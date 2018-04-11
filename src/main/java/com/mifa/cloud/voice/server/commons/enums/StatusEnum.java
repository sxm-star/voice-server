package com.mifa.cloud.voice.server.commons.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author: sxm
 * @date: 2018/4/10 15:40
 * @version: v1.0.0
 */
@Getter
@ApiModel("系统启动 停用状态")
public enum StatusEnum {
    @ApiModelProperty("正常启用")
    NORMAL(0,"正常"),
    @ApiModelProperty("停止使用")
    BLOCK(1,"停用");

    Integer code;
    String  msg;
     StatusEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
