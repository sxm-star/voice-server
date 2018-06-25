package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author songxm
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_parse_log")
public class ParseLogDO extends BaseDataDO {
    /**
     * 主键ID号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


}