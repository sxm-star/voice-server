package com.mifa.cloud.voice.server.controller.common;


import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.enums.FileTypeEnums;
import com.mifa.cloud.voice.server.commons.enums.BizTypeEnums;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.dto.DownLoadFileVO;
import com.mifa.cloud.voice.server.dto.UploadFileVO;
import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.service.UploadFileLogService;
import com.mifa.cloud.voice.server.utils.UploadFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@Api(value = "文件管理", description = "文件管理", produces = MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class UpDownFileController {

    @Autowired
    private ConstConfig aconst;
    @Autowired
    private UploadFileUtil uploadFileUtil;
    @Autowired
    private UploadFileLogService uploadFileLogService;


    @PostMapping(value = "/upload-file")
    @ApiOperation(value = "单个上传文件", notes = "单个上传文件")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "单个上传文件")
    public CommonResponse<UploadFileVO> uploadFileCompress(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileType") FileTypeEnums fileType,
            @RequestParam("bizType") BizTypeEnums bizType,
            @RequestParam("contractNo") String contractNo
            ) throws Exception {
        return CommonResponse.successCommonResponse(uploadFileUtil.upload(file, fileType, bizType, contractNo, aconst));
    }

    @GetMapping("/download-file")
    @ApiOperation(value = "下载文件", notes = "下载文件")
    @Loggable(descp = "下载文件")
    public CommonResponse<DownLoadFileVO> downLoadFile(
            @RequestParam(name = "fileType") FileTypeEnums fileType,
            @RequestParam(name = "bizType") BizTypeEnums bizType) {

        List<UploadFileLog> uploadFileLogs = uploadFileLogService.selectByFileTypeAndBizType(fileType.name(), bizType.name(), "0");
        DownLoadFileVO vo = DownLoadFileVO.builder()
                .fileUrl(uploadFileLogs.get(0)!=null ? aconst.H5_URL_PATH + uploadFileLogs.get(0).getFileUrl() : "")
                .build();
        return CommonResponse.successCommonResponse(vo);
    }

    //@PostMapping(value = "/upload_files")
    //@ApiOperation(value = "多个上传文件", notes = "多个上传文件")
    /*public CommonResponse<List<UploadFileVO>> UploadFileCompressV2(MultipartHttpServletRequest multiRequest) throws Exception {
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
    }*/

}
