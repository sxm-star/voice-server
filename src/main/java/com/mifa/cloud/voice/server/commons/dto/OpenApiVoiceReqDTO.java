package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author: songxm
 * @date: 2018/5/10 13:31
 * @version: v1.0.0
 */
@Data
public class OpenApiVoiceReqDTO {

    @ApiModelProperty("模板ID")
    private String templateId;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("参数值")
    private Map<String,Object> paramsValue;
}
