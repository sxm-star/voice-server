package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2018/5/11.
 * 用户认证审核枚举 1:认证中;2:认证通过,3:认证不通过
 */
@Getter
public enum AuthEnum {

    AUTH_ING("1", "认证中"),
    AUTH_SUCCESS("2", "认证通过"),
    AUTH_FAIL("3", "认证不通过");

    private String code;
    private String desc;

    AuthEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(String code){
        for (AuthEnum itemEnum:AuthEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum.getDesc();
            }
        }
        return code;
    }

    public static String getCode(String desc){
        for (AuthEnum itemEnum:AuthEnum.values()) {
            if (itemEnum.getDesc().equals(desc)){
                return  itemEnum.getCode();
            }
        }
        return desc;
    }

}
