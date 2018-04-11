package com.mifa.cloud.voice.server.controller.user;

import com.alibaba.fastjson.JSON;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.dto.UserRegisterDTO;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.service.CustomerLoginInfoService;
import com.mifa.cloud.voice.server.service.VerficationService;
import com.mifa.cloud.voice.server.utils.PasswordUtil;
import com.mifa.cloud.voice.server.utils.SeqProducerUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/9.
 */
@RestController
@Api(value = "用户注册",description = "用户注册",produces = MediaType.APPLICATION_JSON)
@Slf4j
public class RegisterController {
    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private CustomerLoginInfoService customerLoginInfoService;

    @Autowired
    private VerficationService verficationService;

    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public CommonResponse register(@RequestBody @Valid UserRegisterDTO param) {

        log.info("注册接口入参：[{}]", JSON.toJSONString(param));

        // 校验验证码
        String mobileAuthCode = verficationService.getmobileAuthCodeFromCache(param.getMobile());

        if(StringUtils.isBlank(mobileAuthCode)) {
            log.info("注册接口：[短信验证码已过期]");
            return CommonResponse.failCommonResponse("短信验证码已过期");
        }
        if(!mobileAuthCode.equals(param.getMobieAuthCode())) {
            log.info("注册接口：[验证码错误]");
            return CommonResponse.failCommonResponse("验证码错误");
        }
        if(!param.getLoginPasswd().equals(param.getLoginPasswdSecond())) {
            log.info("注册接口：[两次密码输入不一致]");
            return CommonResponse.failCommonResponse("两次密码输入不一致");
        }
        // 验证账号是否已经注册
        CustomerLoginInfo loginInfo = customerLoginInfoService.findByLoginName(param.getLoginName());
        if(loginInfo != null) {
            log.info("注册接口：[该账号已被注册]");
            return CommonResponse.failCommonResponse("该账号已被注册");
        }

        // 密码加密处理
        Map<String, String> generateMap = passwordUtil.generate(param.getLoginPasswd());

        // 保存用户信息
        CustomerLoginInfo customerLoginInfo = CustomerLoginInfo.builder()
                .contractNo(SeqProducerUtil.getContractNo())
                .contractType(param.getContractType())
                .loginName(param.getLoginName())
                .mobile(param.getMobile())
                .loginPasswd(generateMap.get("encryptPassword"))
                .salt(generateMap.get("salt"))
                .loginStatus("1")
                .registType(param.getRegistType())
                .build();
        int result = customerLoginInfoService.insertSelective(customerLoginInfo);
        if (result > 0) {
            log.info("注册接口：[注册成功]");
            return CommonResponse.successCommonResponse("注册成功",null);
        }
        log.info("注册接口：[注册失败]");
        return CommonResponse.failCommonResponse("注册失败");

    }































}
