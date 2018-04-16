package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.BaseFile;
import com.mifa.cloud.voice.server.utils.FileDownloadUtils;
import com.mifa.cloud.voice.server.utils.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * @author: songxm
 * @date: 2018/4/12 13:04
 * @version: v1.0.0
 */
@Service
@Slf4j
public class BaseFileUploadService {

    public BaseFile upload(MultipartFile file) {
        log.info("文件上传参数 ：{}", file.getName());
        BaseFile baseFile = new BaseFile();
        Map<String, Object> map = FileUploadUtils.upload(file);
        log.info("业务层 文件上传返回结果：{}", map);
        baseFile.setSavePath((String) map.get("realSavePath"));
        baseFile.setFileName((String) map.get("fileName"));
        baseFile.setSaveFilename((String) map.get("saveFilename"));
        baseFile.setSize((Long) map.get("fileSize"));
        return baseFile;
    }

    public void multiUpload(BaseFile baseFile) {
        log.info("业务层 多文件同时上传参数 ：{}", baseFile);
        for (MultipartFile file : baseFile.getFileList()) {
            Map<String, Object> map = FileUploadUtils.upload(file);
            log.info("业务层 文件上传返回结果：{}", map);
        }
    }

    @RequestMapping(value = "/doDownLoadFile")
    public void doDownLoadFile(HttpServletResponse response,
                               HttpServletRequest request) {
        File descDir = new File(AppConst.DOWNLOAD_PATH);
        if (!descDir.exists() && descDir.mkdirs()) {
            log.debug("文件夹创建成功:{}", AppConst.DOWNLOAD_PATH);
        } else {
            log.debug("文件夹已经存在，不需要再次创建：{}", AppConst.DOWNLOAD_PATH);
        }
        String localPath = AppConst.DOWNLOAD_PATH + AppConst.DOWNLOAD_FILE_NAME;
        descDir = new File(localPath);
        if (!descDir.exists()) {
            log.error("not fund file:{}", localPath);
            return;
        }

        FileDownloadUtils.downloadFileToWeb(descDir,
                false, response, request);

    }
}
