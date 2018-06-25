package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/4/25 18:04
 * @version: v1.0.0
 */
@Getter
public enum CallFlagEnum {
    HAS_CALLED("1","已拨通"),
    NO_CALLED("2","未拨通");
    String code;
    String desc;

    CallFlagEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(String code){
        for (CallFlagEnum itemEnum:CallFlagEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum.getDesc();
            }
        }
        return code;
    }

    public static void main(String[] args) {
        System.out.println(CallFlagEnum.getDesc("1"));
    }
}
