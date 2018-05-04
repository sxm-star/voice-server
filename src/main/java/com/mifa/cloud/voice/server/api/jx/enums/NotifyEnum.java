package com.mifa.cloud.voice.server.api.jx.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/3 17:30
 * @version: v1.0.0
 */
@Getter
public enum NotifyEnum {

    CALLSTATE("CallState","呼叫状态"),
    CALLCREATE("CallCreate","呼叫创建"),
    CALLPROCESS("CallProcess","呼叫振铃"),
    CALLANSWER("CallAnswer","呼叫应答"),
    CALLEND("CallEnd","呼叫结束"),
    CDR("Cdr","通话详单"),
    OTHER("other","未知通知");
    String code;
    String desc;

    NotifyEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(String code){
        for (NotifyEnum itemEnum:NotifyEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum.getDesc();
            }
        }
        return code;
    }

    public static NotifyEnum getEnum(String code){
        for (NotifyEnum itemEnum:NotifyEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum;
            }
        }
        return OTHER;
    }
}
