package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/4/23 14:19
 * @version: v1.0.0
 */
@ApiModel("语音列表查询")
@Data
public class VoiceTemplateQuery {

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
}
