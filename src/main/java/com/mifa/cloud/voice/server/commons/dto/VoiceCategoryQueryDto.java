package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: songxm
 * @date: 2018/4/13 15:56
 * @version: v1.0.0
 */
@Data
@ApiModel("模板类目查询实体信息")
public class VoiceCategoryQueryDto {
    @ApiModelProperty("当前客户号")
    String contanctNo;
    @ApiModelProperty("类型标识")
    String bizType;
    @ApiModelProperty("业务名称")
    String name;

}
