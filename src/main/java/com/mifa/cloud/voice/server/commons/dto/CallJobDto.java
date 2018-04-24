package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author: songxm
 * @date: 2018/4/24 08:40
 * @version: v1.0.0
 */
@Data
@ApiModel("执行计划列表信息")
public class CallJobDto {

    /**
     * 主键
     */
    @ApiModelProperty("ID号")
    private Integer id;

    /**
     * 客户号
     */
    @ApiModelProperty("客户号")
    private String contractNo;

    /**
     * 语音模板id
     */
    @ApiModelProperty("语音模板id")
    private String templateId;



    @ApiModelProperty("批次号")
    private String contactTaskId;

    /**
     * 语音模板名称 冗余
     */
    @ApiModelProperty("语音模板名称")
    private String templateName;

    /**
     * 计划内容
     */
    @ApiModelProperty("计划名称")
    @Column(name = "job_name")
    private String jobName;

    /**
     * 任务状态 ：0-待启动 1-运行中，02-暂停，03-停止
     */
    @ApiModelProperty("任务状态 ：0-待启动 1-运行中，02-暂停，03-停止")
    private String jobStatus;


    /**
     * 任务执行表达式
     */
    @ApiModelProperty("任务执行表达式")
    private String cronExpression;

    /**
     * 拨打开始时间段 格式 HH:mm
     */
    @ApiModelProperty("拨打开始时间段 格式 HH:mm")
    private String callStartTime;

    /**
     * 拨打结束时间段 格式 HH:mm
     */
    @ApiModelProperty("拨打结束时间段 格式 HH:mm")
    private String callEndTime;

    /**
     * 重试次数 冗余
     */
    @ApiModelProperty("重试次数")
    private Integer retryCount;

    /**
     * 通讯簿组表 冗余
     */
    @ApiModelProperty("通讯簿组表")
    private String groupName;

    /**
     * 通讯录组内成员数量 冗余
     */
    @ApiModelProperty("通讯录组内成员数量")
    private Integer groupCnt;

    /**
     * 来源
     */
    @ApiModelProperty("来源")
    private String source;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String note;

}
