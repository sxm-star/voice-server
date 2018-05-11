package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;

/**
 * @author: songxm
 * @date: 2018/4/23 15:41
 * @version: v1.0.0
 */
@Data
@ApiModel("语音实体类修改参数")
public class VoiceTemplateAdminAlterReqDto {
    /**
     * 语音模板id
     */
    @ApiModelProperty(value = "语音模板id",required = true)
    @NotEmpty(message = "语音模板id不能为空")
    private String templateId;

    @ApiModelProperty(value = "供应商三方平台模板ID审核回填渠道商名称")
    private String outChannelName;

    @ApiModelProperty(value = "供应商三方平台模板ID")
    private String outTemplateId;

    @ApiModelProperty(value = "审核状态 0:待审核 1：审核通过；2:审核失败；3:审核中")
    private String auditStatus;

    @ApiModelProperty(value = "拒绝原因")
    private String remark;

    @ApiModelProperty("审核人")
    private String updatedBy;

}
