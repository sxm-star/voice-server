package com.mifa.cloud.voice.server.controller;

import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.TemplateVoiceDto;
import com.mifa.cloud.voice.server.service.TemplateVoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @Autowired
    TemplateVoiceService templateVoiceService;

    @ApiOperation("新增语音模板")
    @RequestMapping(value = "/template-voice", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "新增模板类目")
    public CommonResponse<Boolean> addTemplateVoice(@RequestBody @Valid TemplateVoiceDto voiceDto) {
        return CommonResponse.successCommonResponse(templateVoiceService.insertTemplateVoice(voiceDto));
    }
}
