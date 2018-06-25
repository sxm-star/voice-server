package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 奇葩的需求,可以。。。
 * @author: songxm
 * @date: 2018/4/23 14:19
 * @version: v1.0.0
 */
@ApiModel("语音列表查询")
@Data
public class VoiceTemplateSysQuery {

    @ApiModelProperty(value = "语音模板名称",required = false,example = "模板测试1")
    private String templateName;
    @ApiModelProperty(value = "模板类型",required = false,example = "VOICE")
    private String templateType;
    @ApiModelProperty(value = "手机号",example = "13251022222")
    private String mobile;
    /**
     * 语音话术类目名称
     */
    @ApiModelProperty(value = "业务类型名称",required = false,example = "催收")
    private String categoryName;
}
