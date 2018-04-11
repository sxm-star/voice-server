package com.mifa.cloud.voice.server.controller.common;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.utils.UploadFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "上传文件", description = "上传文件", produces = MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class UploadFileController {



    @Autowired
    private ConstConfig aconst;
    @Autowired
    private UploadFileUtil uploadFileUtil;


    @PostMapping(value = "/upload_file")
    @ApiOperation(value = "单个上传文件", notes = "单个上传文件")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "单个上传文件")
    public String UploadFileCompress(@RequestParam("file") MultipartFile file) throws Exception{
        Map json = new HashMap();
        JSONObject json2 = uploadFileUtil.upload(file, aconst);
        json.put("data", json2);
        json.put("status", 0);
        return JSONObject.toJSONString(json);
    }

    //@PostMapping(value = "/upload_files")
    //@ApiOperation(value = "多个上传文件", notes = "多个上传文件")
    public String UploadFileCompressV2(MultipartHttpServletRequest multiRequest) throws Exception {
        Map json = new HashMap();
        JSONArray array = new JSONArray();
        List<MultipartFile> files = multiRequest.getFiles("file");
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                JSONObject json2 = uploadFileUtil.upload(file, aconst);
                array.add(json2);
            }
        }else {
            log.error("上传文件为空");
        }
        json.put("data", array);
        json.put("status", 0);
        return JSONObject.toJSONString(json);
    }





}
