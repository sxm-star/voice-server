package com.mifa.cloud.voice.server.commons.dto;

import com.mifa.cloud.voice.server.commons.enums.VoiceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/5/8.
 */
@ApiModel("语音模版审核列表查询参数")
@Data
public class VoiceTemplateAuditQuery {

    @ApiModelProperty("语音模板id")
    private String templateId;

    @ApiModelProperty("模板类型")
    private VoiceTypeEnum voiceType;

    @ApiModelProperty("商户手机号")
    private String merMobile;

    @ApiModelProperty("模板名称")
    private String templateName;

    @ApiModelProperty("供应商三方平台模板ID审核回填渠道商名称")
    private String outChannelName;

    @ApiModelProperty("审核状态 0:待审核 1：审核通过；2:审核失败；3:审核中")
    private String auditStatus;

}
