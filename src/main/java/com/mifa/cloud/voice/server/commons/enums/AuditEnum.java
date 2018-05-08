package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/4/16 16:28
 * @version: v1.0.0
 */
@Getter
public enum AuditEnum {
    WAIT_AUDIT("0","待审核"),
    AUDIT_SUCCESS("1","审核通过"),
    AUDIT_FAIL("2","审核失败"),
    AUDIT_ING("3","审核中");
    String code;
    String desc;

    AuditEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(String code){
        for (AuditEnum itemEnum:AuditEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum.getDesc();
            }
        }
        return code;
    }
}
