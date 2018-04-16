package com.mifa.cloud.voice.server.controller;

import com.google.common.collect.Lists;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.service.BaseFileUploadService;
import com.mifa.cloud.voice.server.service.ContactsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    BaseFileUploadService baseFileUploadService;
    @Autowired
    ContactsService contactsService;

    @ApiOperation("文件上传")
    @RequestMapping(value = "/upload-file",method = RequestMethod.POST)
    @ResponseBody
    @Loggable(descp = "文件上传")
    CommonResponse<BaseFile> upload(HttpServletRequest request){
        BaseFile baseFile = new BaseFile();
        Map<String, Object> map = getParamFromRequest(request);
        Map<String, String[]> paramMap = (Map<String, String[]>) map.get("param");
        baseFile.setRemark(paramMap.get("remark")==null?"":paramMap.get("remark")[0]);
        baseFile.setFileList((List<MultipartFile>) map.get("fileList"));
        CommonResponse<BaseFile> responseObj = new CommonResponse();
        baseFileUploadService.multiUpload(baseFile);
        responseObj.setSuccess(CommonResponse.TRUE);
        return responseObj;
    }

    public Map<String, Object> getParamFromRequest(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        List<MultipartFile> list = Lists.newArrayList();
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        Map<String, String[]> paramMap = multipartRequest.getParameterMap();
        resultMap.put("param", paramMap);
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            list.add(entity.getValue());
        }
        resultMap.put("fileList", list);
        return resultMap;
    }

    @ApiOperation("文件下载")
    @RequestMapping(value = "/download-file",method = RequestMethod.GET)
    public void doDownLoadFile(HttpServletResponse response,
                               HttpServletRequest request){
           baseFileUploadService.doDownLoadFile(response,request);
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
    @RequestMapping(value = "/contact/{task-id}",method = RequestMethod.PUT)
    public CommonResponse<PageDto<ContactDto>> addContact(@PathVariable("task-id") String taskId,@RequestBody @Valid ContactQueryDto contactDto){
        return CommonResponse.successCommonResponse(contactsService.alterContact(taskId,contactDto));
    }

}
