package com.mifa.cloud.voice.server.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UploadFileLog implements Serializable {
    /**
     * 文件记录主键ID
     */
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 状态: 0:正常；1:删除
     */
    private String fileStatus;

    /**
     * 号码组id
     */
    private String mobileListGroupId;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件业务模块类型
     */
    private String bizType;

    /**
     * 文件URL路径
     */
    private String fileUrl;

    /**
     * 文件服务器的真实路径
     */
    private String fileRealPath;

    /**
     * 文件创建时间
     */
    private Date createAt;

    /**
     * 文件创建者
     */
    private String createBy;

    /**
     * 文件更新时间
     */
    private Date updateAt;

    /**
     * 文件更新者
     */
    private String updateBy;

    /**
     * t_upload_file_log
     */
    private static final long serialVersionUID = 1L;

}