package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: sxm
 * @date: 2018/4/10 15:40
 * @version: v1.0.0
 */
@Getter
public enum ResouceTypeEnum {

    MODULE(0,"模块"),
    MENU(1,"菜单");

    Integer code;
    String  msg;
     ResouceTypeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
