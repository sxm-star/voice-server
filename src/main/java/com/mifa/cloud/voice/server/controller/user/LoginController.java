package com.mifa.cloud.voice.server.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.dto.UserLoginDTO;
import com.mifa.cloud.voice.server.dto.UserLoginVO;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.service.CustomerLoginInfoService;
import com.mifa.cloud.voice.server.utils.JwtTokenUtil;
import com.mifa.cloud.voice.server.utils.PasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2018/4/10.
 */
@RestController
@Api(value = "用户登陆",description = "用户登陆",produces = MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping(AppConst.BASE_PATH + "v1")
public class LoginController {

    @Autowired
    private CustomerLoginInfoService loginInfoService;

    @Autowired
    private PasswordUtil passwordUtil;

    @PostMapping("/login")
    @ApiOperation(value = "登陆",response = UserLoginVO.class)
    @Loggable(descp = "用户登录")
    public CommonResponse login(@RequestBody @Valid UserLoginDTO param) {

        // 校验用户是否存在
        CustomerLoginInfo loginInfo = loginInfoService.findByLoginName(param.getLoginName());
        if(loginInfo == null) {
            return CommonResponse.failCommonResponse("用户名不存在");
        }
        // 校验密码是否正确
        boolean verifyFlag = passwordUtil.verify(param.getLoginPasswd(), loginInfo.getLoginPasswd(), loginInfo.getSalt());
        if(!verifyFlag) {
            return CommonResponse.failCommonResponse("密码错误");
        }

        //修改登陆时间、登陆ip
        loginInfo.setLastLoginTime(System.currentTimeMillis());
        loginInfo.setLastLoginIp(StringUtils.isNotEmpty(param.getLastLoginIp()) ? param.getLastLoginIp() : loginInfo.getLastLoginIp());
        loginInfoService.updateByPrimaryKeySelective(loginInfo);

        // 生成token返回
        String token = JwtTokenUtil.createToken(loginInfo.getContractNo(), 30);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .contractNo(loginInfo.getContractNo())
                .token(token)
                .build();

        return CommonResponse.successCommonResponse(userLoginVO);
    }













}
