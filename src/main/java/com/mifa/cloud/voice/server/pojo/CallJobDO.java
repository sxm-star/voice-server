package com.mifa.cloud.voice.server.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author 
 */
@Table(name="t_customer_call_job")
@Data
public class CallJobDO extends BaseDataDo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户号
     */
    @NotEmpty
    private String contractNo;

    /**
     * 语音模板id
     */
    @NotEmpty
    private String templateId;


    /**
     * 语音模板类型 冗余
     */
    @NotEmpty
    private String templateType;

    /**
     * 语音模板类目 冗余
     */
    @NotEmpty
    private String templateCategory;

    @Column(name = "contact_task_id")
    private String contactTaskId;

    /**
     * 语音模板名称 冗余
     */
    @NotEmpty
    private String templateName;

    /**
     * 计划内容
     */
    @NotEmpty
    private String jobName;

    /**
     * 拨打开始时间段 格式 HH:mm
     */
    private String callStartTime;

    /**
     * 拨打结束时间段 格式 HH:mm
     */
    private String callEndTime;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 备注
     */
    private String note;


}