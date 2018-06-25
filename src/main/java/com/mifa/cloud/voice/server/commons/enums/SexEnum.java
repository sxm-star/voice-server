package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/4/16 08:10
 * @version: v1.0.0
 */
@Getter
public enum SexEnum {
    M("M","男"),
    W("W","女"),
    N("N","未知");

    private String desc;
    private String code;

    SexEnum(String code,String desc){
        this.code = code;
       this.desc = desc;
    }

    public static String getDesc(String code){
        for (SexEnum itemEnum:SexEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum.getDesc();
            }
        }
        return code;
    }

}
