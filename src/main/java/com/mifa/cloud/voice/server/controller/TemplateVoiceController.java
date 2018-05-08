package com.mifa.cloud.voice.server.controller;

import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.commons.enums.VoiceTypeEnum;
import com.mifa.cloud.voice.server.pojo.SystemKeyValue;
import com.mifa.cloud.voice.server.commons.dto.VoiceTemplateAuditVO;
import com.mifa.cloud.voice.server.service.SystemKeyValueService;
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

    @Autowired
    private SystemKeyValueService systemKeyValueService;

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

    @ApiOperation("获取单个语音模板")
    @RequestMapping(value = "/template-voice", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "获取单个模板列表")
    public CommonResponse<PageDto<VoiceTemplateRspDto>> queryTemplateVoice(@RequestParam(required = true) String contractNo,@RequestParam(required = true) String templateId){
        return CommonResponse.successCommonResponse(templateVoiceService.queryTemplateVoice(contractNo,templateId));
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
    @Loggable(descp = "语音模板编辑")
    public CommonResponse<Boolean> alterTemplateVoice(@ModelAttribute @Valid VoiceTemplateAlterReqDto alterReqDto){
        return CommonResponse.successCommonResponse(templateVoiceService.alterTemplateVoice(alterReqDto));
    }

    @ApiOperation("管理员语音模板编辑")
    @RequestMapping(value = "/template-voice-admin", method = RequestMethod.PUT)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "管理员语音模板编辑")
    public CommonResponse<Boolean> alterTemplateVoiceByAdmin(@ModelAttribute @Valid VoiceTemplateAdminAlterReqDto alterReqDto){
        return CommonResponse.successCommonResponse(templateVoiceService.alterTemplateVoiceAdmin(alterReqDto));
    }

    @ApiOperation("语音模板测试")
    @RequestMapping(value = "/template-voice-test", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "语音模板测试")
    public CommonResponse<Boolean> testTemplateVoice(@RequestBody @Valid VoiceTemplateOpenDto templateOpenDto){
        return CommonResponse.successCommonResponse(templateVoiceService.testTemplateVoice(templateOpenDto));
    }

    @ApiOperation("业务类型列表")
    @RequestMapping(value = "/business-type-list", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "业务类型列表")
    public CommonResponse<PageDto<SystemKeyValueVO>> getBusinessTypeList(
            @ModelAttribute @Valid SystemKeyValueQueryDTO query,
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true,defaultValue = "10")  Integer pageSize) {

        return CommonResponse.successCommonResponse(systemKeyValueService.getSystemKeyValuePageList(query, pageNum, pageSize));
    }


    @ApiOperation("业务类型添加")
    @RequestMapping(value = "/business-type", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "业务类型添加")
    public CommonResponse<Boolean> addBusinessType(@RequestBody @Valid BusinessTyeAddDTO dto) {
        // 判断相同类型的key是否已经存在
        List<SystemKeyValue> keyValueList = systemKeyValueService.getKeyValueListByType("BUSINESS_TYPE", dto.getParamValue(), dto.getContractNo());
        if(!keyValueList.isEmpty()) {
            return CommonResponse.failCommonResponse("字典已存在");
        }
        SystemKeyValue systemKeyValue = SystemKeyValue.builder()
                .bizType("BUSINESS_TYPE")
                .paramKey(dto.getParamValue())
                .paramValue(dto.getParamValue())
                .remark(dto.getRemark())
                .createdBy(dto.getContractNo())
                .build();
        int count = systemKeyValueService.insert(systemKeyValue);
        return CommonResponse.successCommonResponse(count>0 ? Boolean.TRUE : Boolean.FALSE);
    }

    @ApiOperation("业务类型编辑")
    @RequestMapping(value = "/business-type", method = RequestMethod.PUT)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "业务类型编辑")
    public CommonResponse<Boolean> editBusinessType(@ModelAttribute @Valid SystemKeyValueEditDTO edit) {
        return CommonResponse.successCommonResponse(systemKeyValueService.updateByPrimaryKeySelective(edit));
    }

    @ApiOperation("业务类型删除")
    @RequestMapping(value = "/business-type", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN),
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "long",value = "业务类型ID")
    })
    @Loggable(descp = "业务类型删除")
    public CommonResponse<Boolean> deleteBusinessType(@RequestParam(required = true) Long id) {
        return CommonResponse.successCommonResponse(systemKeyValueService.deleteByPrimaryKey(id));
    }

    @ApiOperation("获得单个业务类型")
    @RequestMapping(value = "business-type-detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN),
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "long",value = "业务类型ID")
    })
    @Loggable(descp = "获得单个业务类型")
    public CommonResponse<SystemKeyValueVO> getBusinessType(Long id) {
        return CommonResponse.successCommonResponse(systemKeyValueService.selectByPrimaryKey(id));
    }

    @ApiOperation("语音模版审核列表")
    @RequestMapping(value = "voiceTemplate-autid-list", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "语音模版审核列表")
    public CommonResponse<PageDto<VoiceTemplateAuditVO>> getVoiceTemplateAutidList(
            @ModelAttribute @Valid VoiceTemplateAuditQuery query,
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize) {


        return CommonResponse.successCommonResponse(templateVoiceService.queryAuditList(query, pageNum, pageSize));
    }


}
