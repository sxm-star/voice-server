package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/4/25.
 */
@ApiModel
@Data
public class SystemKeyValueEditDTO {

    @ApiModelProperty("主键ID")
    @NotNull(message = "ID不能为空")
    private Long id;

    @ApiModelProperty(value = "客户号", required = true)
    @NotEmpty(message = "客户号不能为空")
    private String contractNo;

    @NotEmpty(message = "业务类型名称为空")
    private String paramValue;

    private String remark;

}
