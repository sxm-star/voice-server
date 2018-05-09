package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/8.
 */
@ApiModel("语音模版审核列表实体类")
@Data
public class VoiceTemplateAuditVO {

    @ApiModelProperty("语音模板id")
    private String templateId;

    @ApiModelProperty("客户号")
    private String contractNo;

    @ApiModelProperty("模板名称")
    private String templateName;

    @ApiModelProperty("供应商三方平台模板ID审核回填渠道商名称")
    private String outChannelName;

    @ApiModelProperty("供应商三方平台模板ID")
    private String outTemplateId;

    @ApiModelProperty("商户手机号")
    private String merMobile;

    @ApiModelProperty("审核状态 0:待审核 1：审核通过；2:审核失败；3:审核中")
    private String auditStatus;

    @ApiModelProperty("说明")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createdAt;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("审核人No")
    private String updatedBy;

    @ApiModelProperty("审核人")
    private String auditer;

    @ApiModelProperty("审核时间")
    private Date updatedAt;

    @ApiModelProperty("模板类型 VOICE:语音模板；TEXT：文本模板")
    private String templateType;

    @ApiModelProperty("语音播放地址")
    private String voiceUrl;

    @ApiModelProperty("话术语音转文本内容")
    private String voiceContent;
}
