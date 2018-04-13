package com.mifa.cloud.voice.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Created by Administrator on 2018/4/12.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemkeyValueTypeVO {

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

}
