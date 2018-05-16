package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author 
 */
@Table(name="t_customer_call_job")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallJobDO extends BaseDataDO {
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

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "batch_id")
    private String batchId;

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
     * 任务状态 ：0-待启动 1-运行中，02-暂停，03-停止
     */
    private Integer jobStatus;


    /**
     * 任务执行表达式
     */
    private String cronExpression;

    /**
     * 拨打开始时间段 格式 HH:mm
     */
    private String callStartTime;

    /**
     * 拨打结束时间段 格式 HH:mm
     */
    private String callEndTime;

    /**
     * 重试次数 冗余
     */
    private Integer retryCount;

    /**
     * 通讯簿组表 冗余
     */
    private String groupName;

    /**
     * 通讯录组内成员数量 冗余
     */
    private Integer groupCnt;

    /**
     * 来源
     */
    private String source;

    /**
     * 备注
     */
    private String note;

    /**
     * 0:正常 ; 1:删除
     */
    private String status;


}