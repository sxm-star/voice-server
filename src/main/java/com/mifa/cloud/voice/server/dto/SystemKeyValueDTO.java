package com.mifa.cloud.voice.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Administrator on 2018/4/12.
 */
@Data
@ApiModel(value = "新增系统字典入参实体类")
public class SystemKeyValueDTO {

    @ApiModelProperty(value = "系统功能类型")
    private String bizType;

    @ApiModelProperty(value = "参数key")
    @NotEmpty(message = "字典key不能为空")
    private String paramKey;

    @ApiModelProperty(value = "参数值")
    private String paramValue;

    @ApiModelProperty(value = "字典说明")
    private String remark;

}
