package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: songxm
 * @date: 2018/4/24 07:42
 * @version: v1.0.0
 */
@Data
@ApiModel("语音模板下拉框响应信息")
public class VoiceTemplateSelectDto {

    @ApiModelProperty("语音模板ID")
    private String templateId;
    /**
     * 主键
     */
    @ApiModelProperty("语音模板名称")
    private String templateName;
}
