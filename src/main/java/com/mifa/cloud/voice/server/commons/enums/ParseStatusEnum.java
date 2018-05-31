package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/17 10:24
 * @version: v1.0.0
 */
@Getter
public enum ParseStatusEnum {

    SUCESS("1"),
    FAIL("2");

    private String code;

    ParseStatusEnum(String code){
        this.code = code;
    }
}
