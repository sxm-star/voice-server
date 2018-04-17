package com.mifa.cloud.voice.server.controller.common;


import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.dto.UploadFileVO;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "上传文件", description = "上传文件", produces = MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class UploadFileController {

    @Autowired
    private ConstConfig aconst;
    @Autowired
    private UploadFileUtil uploadFileUtil;

    @PostMapping(value = "/file")
    @ApiOperation(value = "单个上传文件", notes = "单个上传文件")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "单个上传文件")
    public CommonResponse<UploadFileVO> UploadFileCompress(@RequestParam("file") MultipartFile file) throws Exception {
        return CommonResponse.successCommonResponse(uploadFileUtil.upload(file, aconst));
    }

    //@PostMapping(value = "/upload_files")
    //@ApiOperation(value = "多个上传文件", notes = "多个上传文件")
    public CommonResponse<List<UploadFileVO>> UploadFileCompressV2(MultipartHttpServletRequest multiRequest) throws Exception {
        List<UploadFileVO> voList = new ArrayList<>();
        List<MultipartFile> files = multiRequest.getFiles("file");
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                UploadFileVO vo = uploadFileUtil.upload(file, aconst);
                voList.add(vo);
            }
        } else {
            log.error("上传文件为空");
        }
        return CommonResponse.successCommonResponse(voList);
    }

}
