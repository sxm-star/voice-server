package com.mifa.cloud.voice.server.controller.user;

import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.UserLoginDTO;
import com.mifa.cloud.voice.server.commons.dto.UserLoginVO;
import com.mifa.cloud.voice.server.commons.enums.UserStatusEnum;
import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.service.CustomerLoginInfoService;
import com.mifa.cloud.voice.server.utils.IPUtil;
import com.mifa.cloud.voice.server.utils.JwtTokenUtil;
import com.mifa.cloud.voice.server.utils.PasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2018/4/10.
 */
@RestController
@Api(value = "用户登陆",description = "用户登陆",produces = MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping(AppConst.BASE_PATH + "v1")
@CrossOrigin
public class LoginController {

    @Autowired
    private CustomerLoginInfoService loginInfoService;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private KeyValueDao keyValueDao;

    @PostMapping("/user-login")
    @ApiOperation(value = "登陆")
    @Loggable(descp = "用户登陆")
    public CommonResponse<UserLoginVO> login(@RequestBody @Valid UserLoginDTO param, HttpServletRequest request) {

        CustomerLoginInfo loginInfo = loginInfoService.findByMobileOrLoginName(param.getLoginName(), param.getLoginName());
        // 判断登陆类型，手机号登陆或用户名登陆
        //String mobileReg = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
        /*if(param.getLoginName().matches(mobileReg)) {
            loginInfo = loginInfoService.findByLoginMobile(param.getLoginName());
        }else {
            // 用户名登陆
            loginInfo = loginInfoService.findByLoginName(param.getLoginName());
        }*/
        // 校验用户是否存在
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
        loginInfo.setLastLoginIp(StringUtils.isNotEmpty(IPUtil.getRequestIp(request)) ? IPUtil.getRequestIp(request) : loginInfo.getLastLoginIp());
        loginInfoService.updateByPrimaryKeySelective(loginInfo);

         if (loginInfo.getLoginStatus()!=null && loginInfo.getLoginStatus().equals(UserStatusEnum.LOCK.getCode()))
         {
             return CommonResponse.failCommonResponse("403","你的账号已经冻结,请联系管理员");
         }
        // 生成token返回
        String token = JwtTokenUtil.createToken(loginInfo.getContractNo(), 60);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .contractNo(loginInfo.getContractNo())
                .token(token)
                .isManager(loginInfo.getIsManager())
                .contractNoRoleId(loginInfo.getContractNoRoleId())
                .build();

        return CommonResponse.successCommonResponse(userLoginVO);
    }

    @GetMapping("/user-logout")
    @ApiOperation(value = "注销")
    @Loggable(descp = "用户注销")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string")
    })
    public CommonResponse<Boolean> logout(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("token:[{}]", token);
        keyValueDao.set(token, token, 30 * 60);
        return CommonResponse.successCommonResponse(Boolean.TRUE);
    }

}
