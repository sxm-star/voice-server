package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author songxm
 */
@Table(name="t_system_job_time")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemJobTimeDO extends BaseDataDO {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 0:工作日; 1:节假日
     */
    private String callTimeType;

    /**
     * 允许时间段开始时间段 hh:ss
     */
    private String callAllowTimeStart;

    /**
     * 允许时间段结束时间段 hh:ss
     */
    private String callAllowTimeEnd;

    /**
     * 0:正常；1:停用
     */
    private String status;

    private String createdBy;

}