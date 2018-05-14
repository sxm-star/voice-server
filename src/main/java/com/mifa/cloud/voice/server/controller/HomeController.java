package com.mifa.cloud.voice.server.controller;

import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.service.AccountCapitalService;
import com.mifa.cloud.voice.server.service.CustomerLoginInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: songxm
 * @date: 2018/5/14 16:53
 * @version: v1.0.0
 */
@RestController
@CrossOrigin
@Api(value = "后台首页相关API.", tags = "后台首页相关API", description = "首页管理")
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class HomeController {
    @Autowired
    AccountCapitalService accountCapitalService;
    @Autowired
    CustomerLoginInfoService loginInfoService;
}
