package com.mifa.cloud.voice.server.controller;

import com.mifa.cloud.voice.server.commons.constants.AppConst;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: songxm
 * @date: 2018/4/11 19:38
 * @version: v1.0.0
 */
@RestController
@CrossOrigin
@Api(value = "语音模板相关API.", tags = "语音模板API", description = "涉及模板上传,审核,测试,维护功能")
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class TemplateVoiceController {
}
