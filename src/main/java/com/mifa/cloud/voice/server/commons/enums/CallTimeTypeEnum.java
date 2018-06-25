package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/28 17:06
 * @version: v1.0.0
 */
@Getter
public enum CallTimeTypeEnum {

    WEEKDAY("0","工作日"),
    FESTIVAL_AND_HOLIDAY("1","节假日");

    private String code;
    private String desc;

    CallTimeTypeEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(String code){
        for (CallTimeTypeEnum itemEnum:CallTimeTypeEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum.getDesc();
            }
        }
        return code;
    }

    public static String getCode(String desc){
        for (CallTimeTypeEnum itemEnum:CallTimeTypeEnum.values()) {
            if (itemEnum.getDesc().equals(desc)){
                return  itemEnum.getCode();
            }
        }
        return "0";
    }
}
