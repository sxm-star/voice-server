package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/28 15:47
 * @version: v1.0.0
 */
@Getter
public enum UserStatusEnum {

    NORMAL("1","正常"),
    LOCK("2","冻结");
    private String code;

    private String desc;

    UserStatusEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(String code){
        for (UserStatusEnum itemEnum:UserStatusEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum.getDesc();
            }
        }
        return code;
    }

    public static String getCode(String desc){
        for (UserStatusEnum itemEnum:UserStatusEnum.values()) {
            if (itemEnum.getDesc().equals(desc)){
                return  itemEnum.getCode();
            }
        }
        return "2";
    }
}
