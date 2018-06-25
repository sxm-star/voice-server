package com.mifa.cloud.voice.server.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: songxm
 * @date: 2018/5/17 10:01
 * @version: v1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParseLogDto {

    /**
     * 解析的文件ID
     */
    private Long parseFileId;

    /**
     * 解析的文件名 冗余
     */
    private String parseFileName;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 解析状态 1:成功；2:失败
     */
    private String parseStatus;

    /**
     * 解析时间
     */
    private Date parseTime;

    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy;
}
