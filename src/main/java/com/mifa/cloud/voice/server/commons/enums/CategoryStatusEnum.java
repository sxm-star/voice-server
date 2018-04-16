package com.mifa.cloud.voice.server.commons.enums;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/4/13 17:50
 * @version: v1.0.0
 */
@Getter
public enum  CategoryStatusEnum {
    @ApiModelProperty("正常启用")
    NORMAL('1',"正常"),
    @ApiModelProperty("删除")
    DELETE('2',"删除");

    char code;
    String  msg;
    CategoryStatusEnum(char code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
