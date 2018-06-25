package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/25.
 */
@ApiModel
@Data
public class SystemKeyValueVO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("系统功能类型")
    private String bizType;

    @ApiModelProperty("参数key")
    private String paramKey;

    @ApiModelProperty("参数值")
    private String paramValue;

    @ApiModelProperty(value = "字典说明")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;
}
