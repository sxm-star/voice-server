package com.mifa.cloud.voice.server.controller.user;

import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.dto.UserEditAvatarDTO;
import com.mifa.cloud.voice.server.dto.UserEditMobileDTO;
import com.mifa.cloud.voice.server.dto.UserInfoVO;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.service.CustomerLoginInfoService;
import com.mifa.cloud.voice.server.service.VerficationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2018/4/11.
 */

@RestController
@Api(value = "用户信息管理", description = "用户信息管理", produces = MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class UserInfoController {

    @Autowired
    private CustomerLoginInfoService infoService;

    @Autowired
    private VerficationService verficationService;


    @PostMapping("/user_info/get_user_info/{contractNo}")
    @ApiOperation(value = "获得用户信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "获得用户信息")
    public CommonResponse getUserInfo(@PathVariable("contractNo") String contractNo) {

        CustomerLoginInfo customerInfo = infoService.selectByPrimaryKey(contractNo);
        if(customerInfo == null) {
            return CommonResponse.failCommonResponse("用户不存在");
        }

        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(customerInfo, vo);

        /** TODO 获取用户认证信息*/


        return CommonResponse.successCommonResponse(vo);
    }

    @PostMapping("/user_info/edit_avatar")
    @ApiOperation(value = "修改头像")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "修改头像")
    public CommonResponse editAvatar(@RequestBody @Valid UserEditAvatarDTO param) {

        CustomerLoginInfo customerInfo = infoService.selectByPrimaryKey(param.getContractNo());
        if(customerInfo == null) {
            return CommonResponse.failCommonResponse("用户不存在");
        }
        customerInfo.setAvatarUrl(param.getAvatarUrl());
        int count = infoService.updateByPrimaryKeySelective(customerInfo);

        if (count > 0) {
            return CommonResponse.successCommonResponse("修改头像成功",null);
        }
        return CommonResponse.failCommonResponse("修改头像失败");

    }

    @PostMapping("/user_info/edit_mobile")
    @ApiOperation(value = "修改手机号码")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "修改手机号码")
    public CommonResponse editMobileVerify(@RequestBody @Valid UserEditMobileDTO param){

        // 校验短信验证码
        String mobileAuthCode = verficationService.getmobileAuthCodeFromCache(param.getNewMobile());
        if(StringUtils.isEmpty(mobileAuthCode)) {
            return CommonResponse.failCommonResponse("手机验证码已过期，请重新获取");
        }
        if(!mobileAuthCode.equals(param.getMobieAuthCode())) {
            return CommonResponse.failCommonResponse("验证码错误");
        }
        CustomerLoginInfo customerInfo = infoService.selectByPrimaryKey(param.getContractNo());
        if(customerInfo == null) {
            return CommonResponse.failCommonResponse("用户不存在");
        }
        customerInfo.setMobile(param.getNewMobile());
        int count = infoService.updateByPrimaryKeySelective(customerInfo);

        if (count > 0) {
            return CommonResponse.successCommonResponse("修改手机号成功",null);
        }
        return CommonResponse.failCommonResponse("修改手机号失败");

    }


























}
