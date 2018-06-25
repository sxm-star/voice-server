package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * 模板业务标识
 * @author: songxm
 * @date: 2018/5/24 11:38
 * @version: v1.0.0
 */
@Getter
public enum TemplateBizEnum {
    CHARGE("0","收费"),
    FREE_EXPERIENCE("1","免费");

    String code;
    String  desc;
    TemplateBizEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
