package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/4/23 15:41
 * @version: v1.0.0
 */
@Data
@ApiModel("语音实体类修改参数")
public class VoiceTemplateAlterReqDTO {
    /**
     * 语音模板id
     */
    @ApiModelProperty(value = "语音模板id",required = true)
    @NotEmpty(message = "语音模板id不能为空")
    private String templateId;
    @ApiModelProperty(value = "语音模板名称",required = false,example = "模板测试1")
    private String templateName;
    @ApiModelProperty(value = "模板类型",required = false,example = "VOICE")
    private String templateType;
    @ApiModelProperty(value = "客户号",required = true,example = "123456")
    @NotEmpty(message = "客户号不能为空")
    private String contractNo;
    /**
     * 语音话术类目名称
     */
    @ApiModelProperty(value = "业务类型名称",required = false,example = "催收")
    private String categoryName;
    /**
     * 设置的话术关键词
     */
    @ApiModelProperty("设置的话术关键词")
    private String keyWord;

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

}
