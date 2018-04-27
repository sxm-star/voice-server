package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/4/27 09:56
 * @version: v1.0.0
 */
@Data
@ApiModel("号码组新增实体")
public class ContactGroupPostDto {
    @ApiModelProperty(value = "客户号",required = true)
    @NotEmpty(message = "客户号不能为空")
    String contractNo;

    @ApiModelProperty(value = "组名",required = true)
    @NotEmpty(message = "组名不能为空")
    String groupName;

    @ApiModelProperty("来源")
    @NotEmpty(message = "来源不能为空")
    String source;
}
