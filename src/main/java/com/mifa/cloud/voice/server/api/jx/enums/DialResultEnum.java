package com.mifa.cloud.voice.server.api.jx.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/3 16:50
 * @version: v1.0.0
 */
@Getter
public enum DialResultEnum {

    TIMEOUT(0,"识别超时"),
    BLANK_NUMBER(1,"空号"),
    POWER_OFF(2,"关机"),
    NO_CONNECT(3,"无法接通"),
    NO_CALLED(4,"无人接听"),
    REJECT(5,"拒绝"),
    BUSY_LINE(6,"忙"),
    CLOSING_DOWN(7,"停机"),
    CALLING(8,"正在通话"),
    UNIDENTIFIED(-1,"未识别");

    private Integer code;
    private String desc;

    DialResultEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(String code){
        for (DialResultEnum itemEnum:DialResultEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum.getDesc();
            }
        }
        return code;
    }
}
