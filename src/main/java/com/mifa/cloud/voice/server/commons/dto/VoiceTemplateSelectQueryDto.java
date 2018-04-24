package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/4/24 08:01
 * @version: v1.0.0
 */
@Data
@ApiModel("模板下拉框三级联动查询")
public class VoiceTemplateSelectQueryDto {

    @ApiModelProperty(value = "客户号",required = true,example = "123456")
    @NotEmpty(message = "客户号不能为空")
    private String contractNo;

    /**
     * 模板类型 1:语音模板；2：文本模板
     */
    @ApiModelProperty(value = "模板类型 VOICE:语音模板；TEXT：文本模板",example = "VOICE or TEXT")
    private String templateType;

    @ApiModelProperty(value = "业务类型名称",required = false)
    private String categoryName;
    @ApiModelProperty(value = "是否测试 true:是;false:不是",example = "true",required = true)
    @NotEmpty(message = "标识位不能为空")
    private boolean isTest;
}
