package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/4/24 07:58
 * @version: v1.0.0
 */
@Data
@ApiModel("开放的语音测试地址")
public class VoiceTemplateOpenDto {
    @ApiModelProperty("语音模板id")
    private String templateId;
    /**
     * 客户号
     */
    @ApiModelProperty("客户号")
    @NotEmpty(message = "客户号不能为空")
    private String contractNo;
    @ApiModelProperty("测试手机号")
    @NotEmpty(message = "测试手机号不能为空")
    private String phone;
    @ApiModelProperty("测试姓名")
    @NotEmpty(message = "测试姓名不能为空")
    private String name;
    @NotEmpty(message = "公司名称不能为空")
    private String orgName;
}
