package com.mifa.cloud.voice.server.controller;

import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.service.TemplateVoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation("语音模板列表")
    @RequestMapping(value = "/template-voice-list", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "语音模板列表")
    public CommonResponse<PageDto<VoiceTemplateRspDto>> queryTemplateVoiceList(@ModelAttribute @Valid VoiceTemplateQuery query, @RequestParam(required = true, defaultValue = "1") Integer pageNum, @RequestParam(required = true,defaultValue = "10")  Integer pageSize){
        return CommonResponse.successCommonResponse(templateVoiceService.queryTemplateVoiceList(query,pageNum,pageSize));
    }

    @ApiOperation("语音模板下拉框查询 三级联动")
    @RequestMapping(value = "/template-voice-select-list", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
     , @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string",value = "客户号",example = "123456"),
            @ApiImplicitParam(paramType = "query", name = "templateType", required = false, dataType = "VoiceTypeEnum",allowableValues = "TEXT,VOICE" ,value = "模板类型 VOICE:语音模板；TEXT：文本模板"),
            @ApiImplicitParam(paramType = "query", name = "categoryName", required = false, dataType = "string",value = "业务类型名称"),
            @ApiImplicitParam(paramType = "query", name = "isTest", required = true, dataType = "boolean",value = "标识位不能为空 true:测试语音发送查询"),
    })
    @Loggable(descp = "语音模板下拉框查询 三级联动")
    public CommonResponse<List<VoiceTemplateSelectDto>> queryTemplateVoiceSelectList(@ModelAttribute @Valid VoiceTemplateSelectQueryDto queryDto ){
        return CommonResponse.successCommonResponse(templateVoiceService.queryTemplateVoiceSelectList(queryDto));
    }

    @ApiOperation("语音模板删除")
    @RequestMapping(value = "/template-voice", method = RequestMethod.DELETE)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN),
            @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string",value = "客户号",example = "123456"),
            @ApiImplicitParam(paramType = "query", name = "templateId", required = true, dataType = "string",value = "语音模板ID号",example = "1111111111")
    })
    @Loggable(descp = "语音模板删除")
    public CommonResponse<Boolean> delTemplateVoiceList(@RequestParam(required = true) String contractNo,@RequestParam(required = true) String templateId){
        return CommonResponse.successCommonResponse(templateVoiceService.delTemplateVoice(contractNo,templateId));
    }

    @ApiOperation("语音模板编辑")
    @RequestMapping(value = "/template-voice", method = RequestMethod.PUT)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "语音模板删除")
    public CommonResponse<Boolean> alterTemplateVoice(@ModelAttribute @Valid VoiceTemplateAlterReqDto alterReqDto){
        return CommonResponse.successCommonResponse(templateVoiceService.alterTemplateVoice(alterReqDto));
    }

    @ApiOperation("语音模板测试")
    @RequestMapping(value = "/template-voice-test", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "语音模板测试")
    public CommonResponse<Boolean> testTemplateVoice(@RequestBody @Valid VoiceTemplateOpenDto templateOpenDto){
        return CommonResponse.successCommonResponse(templateVoiceService.testTemplateVoice(templateOpenDto));
    }
}
