package com.mifa.cloud.voice.server.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author songxm
 */
@Table(name="t_upload_file")
@Data
public class UploadFileDO extends BaseDataDo{
    /**
     * 文件记录主键ID
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 状态: 0:正常；1:删除
     */
    @NotEmpty
    private String fileStatus;

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
    @NotEmpty
    private String fileUrl;

    /**
     * 文件服务器的真实路径
     */
    private String fileRealPath;


}