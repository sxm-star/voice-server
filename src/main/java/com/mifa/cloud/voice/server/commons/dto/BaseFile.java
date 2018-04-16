package com.mifa.cloud.voice.server.commons.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: sxm
 * @date: 2018/4/12 10:33
 * @version: v1.0.0
 */
@Setter
@Getter
@ToString
public class BaseFile implements Serializable{

    private static final long serialVersionUID = -8555668715921117274L;

    /**
     * 上传文件
     */
    MultipartFile file;

    /**
     * 上传文件队列
     */
    List<MultipartFile> fileList;

    /**
     * 原始文件名
     */
    String originalFileName;

    /**
     * 文件名（不含后缀名）
     */
    String fileName;

    /**
     * 文件存储姓名
     */
    String saveFilename;

    /**
     * 文件后缀名
     */
    String extName;

    /**
     * 文件大小
     */
    Long size;

    /**
     * 文件存储路径
     */
    String savePath;

    /**
     * 文件下载路径
     */
    String downloadPath;

    /**
     * 上传时间
     */
    Date uploadTime;

    /**
     * 备注
     */
    String remark;




}
