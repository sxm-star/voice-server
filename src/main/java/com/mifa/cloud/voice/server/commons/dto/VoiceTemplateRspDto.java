package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: songxm
 * @date: 2018/4/23 14:05
 * @version: v1.0.0
 */
@Data
@ApiModel("语音模板响应实体")
public class VoiceTemplateRspDto {
    /**
     * 语音模板id
     */
    @ApiModelProperty("语音模板id")
    private String templateId;

    @ApiModelProperty("语音模板名称")
    private String templateName;

    /**
     * 客户号
     */
    @ApiModelProperty("客户号")
    private String contractNo;

    /**
     * 模板类型 1:语音模板；2：文本模板
     */
    @ApiModelProperty("模板类型 VOICE:语音模板；TEXT：文本模板")
    private String templateType;


    /**
     * 语音话术类目名称
     */
    @ApiModelProperty("业务类型名称")
    private String categoryName;

    /**
     * 语音话术类目ID
     */
    private String categoryId;

    /**
     * 设置的话术关键词
     */
    @ApiModelProperty("设置的话术关键词")
    private String keyWord;


    /**
     * 审核状态 0:待审核 1：审核通过；2:审核失败
     */
    @ApiModelProperty("审核状态 0:待审核 1：审核通过；2:审核失败")
    private String auditStatus;


    /**
     * 语音播放地址
     */
    @ApiModelProperty("语音播放地址")
    private String voiceUrl;

    /**
     * 话术语音转文本内容
     */
    @ApiModelProperty("话术语音转文本内容")
    private String voiceContent;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdAt;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createdBy;

}
