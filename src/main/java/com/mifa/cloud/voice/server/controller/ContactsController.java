package com.mifa.cloud.voice.server.controller;

import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.service.ContactsService;
import com.mifa.cloud.voice.server.service.CustomerTaskContactGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: songxm
 * @date: 2018/4/13 07:41
 * @version: v1.0.0
 */
@RestController
@CrossOrigin
@Api(value = "号码管理相关API.", tags = "号码管理相关API", description = "号码管理")
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class ContactsController {

    @Autowired
    ContactsService contactsService;

    @Autowired
    CustomerTaskContactGroupService customerTaskContactGroupService;

    @ApiOperation(value = "增加号码组")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
            , @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string",value = "客户号必填"),
            @ApiImplicitParam(paramType = "query", name = "groupName", required = true, dataType = "string",value = "组名必填"),
            @ApiImplicitParam(paramType = "query", name = "source", required = false, dataType = "string", value = "来源选填")
    })
    @RequestMapping(value = "/contact-group",method = RequestMethod.POST)
    public CommonResponse<Boolean> addContactGroup(String contractNo,String groupName,String source){
        if(StringUtils.isEmpty(groupName) || StringUtils.isEmpty(contractNo)){
            return CommonResponse.failCommonResponse("400","组名和客户号必填,不能为空");
        }
     return CommonResponse.successCommonResponse(customerTaskContactGroupService.addContactGroup(groupName,source,contractNo));
    }

    @ApiOperation(value = "编辑号码组")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
         , @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string",value = "客户号必填"),
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "int", value = "来源选填"),
            @ApiImplicitParam(paramType = "query", name = "groupName", required = false, dataType = "string",value = "组名"),
            @ApiImplicitParam(paramType = "query", name = "source", required = false, dataType = "string", value = "来源选填")
    })
    @RequestMapping(value = "/contact-group",method = RequestMethod.PUT)
    public CommonResponse<Boolean> addContactGroup(String contractNo,String groupName,String source ,Integer id){
        if(StringUtils.isEmpty(contractNo) || id==null){
            return CommonResponse.failCommonResponse("400","组ID和客户号必填,不能为空");
        }
        return CommonResponse.successCommonResponse(customerTaskContactGroupService.updateContactGroup(groupName,source,contractNo,Long.valueOf(id)));
    }

    @ApiOperation(value = "号码组的列表查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    ,  @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string",value = "客户号"),
            @ApiImplicitParam(paramType = "query", name = "groupName", required = false, dataType = "string",value = "组名"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", required = true, dataType = "int", value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页条数"),
    })
    @RequestMapping(value = "/contact-group-list",method = RequestMethod.GET)
    public CommonResponse<PageDto<ContactGroupRspDto>> queryContactList(@RequestParam(required = true)String contractNo,@RequestParam(required = false) String groupName,@RequestParam(required = true, defaultValue = "1") Integer pageNum, @RequestParam(required = true,defaultValue = "10") Integer pageSize){
        return CommonResponse.successCommonResponse(customerTaskContactGroupService.queryContactGroupList(contractNo,groupName,pageNum,pageSize));
    }

    @ApiOperation(value = "号码列表查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @RequestMapping(value = "/contact-list",method = RequestMethod.GET)
    public CommonResponse<PageDto<ContactDto>> queryContactList(@ModelAttribute @Valid ContactQueryDto contactQueryDto,@RequestParam(required = true, defaultValue = "1") Integer pageNum, @RequestParam(required = true,defaultValue = "3") Integer pageSize){
        return CommonResponse.successCommonResponse(contactsService.queryContactList(contactQueryDto,pageNum,pageSize));
    }


    @ApiOperation("号码新增")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @RequestMapping(value = "/contact",method = RequestMethod.POST)
    public CommonResponse<PageDto<ContactDto>> addContact(@RequestBody @Valid ContactDto contactDto){
        return CommonResponse.successCommonResponse(contactsService.insertContact(contactDto));
    }

    @ApiOperation("号码修改")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @RequestMapping(value = "/contact",method = RequestMethod.PUT)
    public CommonResponse<PageDto<ContactDto>> addContact(@ModelAttribute @Valid ContactAlterReqDto contactDto){
        return CommonResponse.successCommonResponse(contactsService.alterContact(contactDto));
    }

}
