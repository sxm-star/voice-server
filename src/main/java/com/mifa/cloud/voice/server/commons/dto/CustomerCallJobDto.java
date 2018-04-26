package com.mifa.cloud.voice.server.commons.dto;

import com.mifa.cloud.voice.server.commons.enums.VoiceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

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
    @ApiModelProperty(value = "客户号",required = true)
    private String contractNo;

    /**
     * 语音模板id
     */
    @NotEmpty
    @ApiModelProperty(value = "语音模板ID",required = true)
    private String templateId;
    /**
     * 语音模板类型 冗余
     */
    @NotNull
    @ApiModelProperty(value = "语音模板类型",required = true,example = "VOICE")
    private VoiceTypeEnum templateType;

    @ApiModelProperty(value = "号码组批次taskId",required = true)
    @NotEmpty(message = "taskId不能为空")
    private String taskId;
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
    @ApiModelProperty(value = "计划名称",required = true,example = "米之发催收部拨打计划1")
    private String jobName;

    /**
     * 拨打开始时间段 格式 HH:mm
     */
    @ApiModelProperty(value = "拨打开始时间段 格式 HH:mm",required = true,example = "08:00")
    private String callStartTime;

    /**
     * 拨打结束时间段 格式 HH:mm
     */
    @ApiModelProperty(value = "拨打结束时间段 格式 HH:mm",required = true,example = "16:30")
    private String callEndTime;

    /**
     * 重试次数
     */
    @ApiModelProperty(value = "重试次数",required = true,example = "2")
    private Integer retryCount;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",required = false,example = "米之发催收部定制拨打计划1")
    private String note;
}
