package com.mifa.cloud.voice.server.controller;

import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.ContactDto;
import com.mifa.cloud.voice.server.commons.dto.ContactQueryDto;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.service.ContactsService;
import com.mifa.cloud.voice.server.service.CustomerTaskContactGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "号码列表查询")
    @RequestMapping(value = "/contact-group",method = RequestMethod.GET)
    public CommonResponse<Boolean> addContactGroup(String groupName,String source){
     return CommonResponse.successCommonResponse(customerTaskContactGroupService.addContactGroup(groupName,source));
    }

    @ApiOperation(value = "号码列表查询")
    @RequestMapping(value = "/contact-list",method = RequestMethod.GET)
    public CommonResponse<PageDto<ContactDto>> queryContactList(@ModelAttribute @Valid ContactQueryDto contactQueryDto,@RequestParam(required = true, defaultValue = "1") Integer pageNum, @RequestParam(required = true,defaultValue = "3") Integer pageSize){
        return CommonResponse.successCommonResponse(contactsService.queryContactList(contactQueryDto,pageNum,pageSize));
    }


    @ApiOperation("号码新增")
    @RequestMapping(value = "/contact",method = RequestMethod.POST)
    public CommonResponse<PageDto<ContactDto>> addContact(@RequestBody @Valid ContactDto contactDto){
        return CommonResponse.successCommonResponse(contactsService.insertContact(contactDto));
    }

    @ApiOperation("号码修改")
    @RequestMapping(value = "/contact/{id}",method = RequestMethod.PUT)
    public CommonResponse<PageDto<ContactDto>> addContact(@PathVariable("id") Long id,@RequestBody @Valid ContactQueryDto contactDto){
        return CommonResponse.successCommonResponse(contactsService.alterContact(id,contactDto));
    }

}
