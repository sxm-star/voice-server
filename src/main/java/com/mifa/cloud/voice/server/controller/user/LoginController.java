package com.mifa.cloud.voice.server.controller.user;

import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2018/4/10.
 */
@RestController
@Api(value = "用户登陆",description = "用户登陆",produces = MediaType.APPLICATION_JSON)
@Slf4j
public class LoginController {

    @PostMapping("/login")
    @ApiOperation(value = "登陆")
    public CommonResponse login() {


        return CommonResponse.successCommonResponse(null);
    }













}
