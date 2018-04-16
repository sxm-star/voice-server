package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/4/16 19:11
 * @version: v1.0.0
 */
@Data
@ApiModel("客户拨打任务设置实体信息")
public class CustomerCallJobDto {
    /**
     * 客户号
     */
    @NotEmpty
    @ApiModelProperty("客户号")
    private String contractNo;

    /**
     * 语音模板id
     */
    @NotEmpty
    @ApiModelProperty("语音模板ID")
    private String templateId;
    /**
     * 语音模板类型 冗余
     */
    @NotEmpty
    @ApiModelProperty("语音模板类型")
    private String templateType;

    @ApiModelProperty("号码组批次taskId")
    private String contactTaskId;
    /**
     * 语音模板类目 冗余
     */
    @NotEmpty
    @ApiModelProperty("语音模板类目")
    private String templateCategory;

    /**
     * 语音模板名称 冗余
     */
    @NotEmpty
    @ApiModelProperty("语音模板名称")
    private String templateName;

    /**
     * 计划内容
     */
    @NotEmpty
    @ApiModelProperty("计划名称")
    private String jobName;

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
     * 重试次数
     */
    @ApiModelProperty("重试次数")
    private Integer retryCount;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String note;
}
