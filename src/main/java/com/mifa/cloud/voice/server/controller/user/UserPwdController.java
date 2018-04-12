package com.mifa.cloud.voice.server.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.config.StaticConst;
import com.mifa.cloud.voice.server.dto.*;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.service.CustomerLoginInfoService;
import com.mifa.cloud.voice.server.service.VerficationService;
import com.mifa.cloud.voice.server.utils.PasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/10.
 * 用户密码controller
 */
@RestController
@Api(value = "用户密码管理", description = "用户密码管理", produces = MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class UserPwdController {

    @Autowired
    private KeyValueDao keyValueDao;

    @Autowired
    private CustomerLoginInfoService loginInfoService;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private VerficationService verficationService;

    @Autowired
    private CustomerLoginInfoService infoService;



    @PostMapping("/retrieve_password/verify_img_code")
    @ApiOperation(value = "校验图片验证码")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    /*@RequestHeader(HttpHeaders.AUTHORIZATION) String token*/
    @Loggable(descp = "找回密码校验图片验证码")
    public CommonResponse<Void> retrievePasswordVerifyImgCode(@RequestBody @Valid UserPwdImgCodeDTO param) {

        // 根据手机号获取缓存中的图片验证码
        String imgIdentifyCode = (String) keyValueDao.get(StaticConst.IMG_IDENTIFY_CODE + param.getMobile());
        if(StringUtils.isEmpty(imgIdentifyCode)) {
            return CommonResponse.failCommonResponse("图片验证码已过期，请重新获取");
        }
        if(!param.getImageVerficationCode().equalsIgnoreCase(imgIdentifyCode)) {
            return CommonResponse.failCommonResponse("验证码错误");
        }
        return CommonResponse.successCommonResponse();

    }


    @PostMapping("/retrieve_password/modify_password")
    @ApiOperation(value = "找回密码修改登陆密码")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "找回密码修改登陆密码")
    public CommonResponse<Void> retrievePasswordModify(@RequestBody @Valid UserRetrievePasswordDTO param) {

        if(!param.getLoginPasswd().equals(param.getLoginPasswdSecond())) {
            return CommonResponse.failCommonResponse("两次密码输入不一致");
        }

        CustomerLoginInfo customerLoginInfo = loginInfoService.selectByPrimaryKey(param.getContractNo());

        if(customerLoginInfo == null) {
            return CommonResponse.failCommonResponse("用户不存在");
        }

        // 密码加密处理
        Map<String, String> generateMap = passwordUtil.generate(param.getLoginPasswd());
        customerLoginInfo.setSalt(generateMap.get("salt"));
        customerLoginInfo.setLoginPasswd(generateMap.get("encryptPassword"));

        int count = loginInfoService.updateByPrimaryKeySelective(customerLoginInfo);
        if (count > 0) {
            return CommonResponse.successCommonResponse("找回密码成功",null);
        }

        return CommonResponse.failCommonResponse("找回密码失败，请稍后重试");
    }


    @PostMapping("/password/edit_password")
    @ApiOperation(value = "修改登陆密码")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "修改登陆密码")
    public CommonResponse<Void> editPassword(@RequestBody @Valid UserEditPwdDTO param) {

        // 校验短信验证码
        String mobileAuthCode = verficationService.getmobileAuthCodeFromCache(param.getMobile());
        if(StringUtils.isEmpty(mobileAuthCode)) {
            return CommonResponse.failCommonResponse("手机验证码已过期，请重新获取");
        }
        if(!mobileAuthCode.equals(param.getMobieAuthCode())) {
            return CommonResponse.failCommonResponse("验证码错误");
        }

        // 校验原始密码
        CustomerLoginInfo customerInfo = infoService.selectByPrimaryKey(param.getContractNo());
        if(customerInfo == null) {
            return CommonResponse.failCommonResponse("用户不存在");
        }
        boolean verifyFlag = passwordUtil.verify(param.getOldPassword(), customerInfo.getLoginPasswd(), customerInfo.getSalt());
        if(!verifyFlag) {
            return CommonResponse.failCommonResponse("原始密码错误");
        }

        // 校验新密码
        if(!param.getNewPassword().equals(param.getNewPasswordSecond())) {
            return CommonResponse.failCommonResponse("两次密码输入不一致");
        }

        // 密码加密处理
        Map<String, String> generateMap = passwordUtil.generate(param.getNewPassword());
        customerInfo.setSalt(generateMap.get("salt"));
        customerInfo.setLoginPasswd(generateMap.get("encryptPassword"));

        int count = loginInfoService.updateByPrimaryKeySelective(customerInfo);
        if (count > 0) {
            return CommonResponse.successCommonResponse("修改密码成功",null);
        }

        return CommonResponse.failCommonResponse("修改密码失败，请稍后重试");

    }




}
