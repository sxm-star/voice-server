package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: sxm
 * @date: 2018/4/10 15:40
 * @version: v1.0.0
 */
@Getter
public enum ResouceStatusEnum {

    NORMAL(0,"正常"),
    BLOCK(1,"停用");

    Integer code;
    String  msg;
     ResouceStatusEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
