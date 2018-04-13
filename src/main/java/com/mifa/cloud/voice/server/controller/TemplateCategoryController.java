package com.mifa.cloud.voice.server.controller;

import com.mifa.cloud.voice.server.commons.constants.AppConst;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: songxm
 * @date: 2018/4/13 10:11
 * @version: v1.0.0
 */
@RestController
@CrossOrigin
@Api(value = "模板类目相关API.", tags = "模板类目API", description = "模板类目的添加,审核,测试,维护功能")
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class TemplateCategoryController {

    public
}
