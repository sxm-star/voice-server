package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/4/16 08:10
 * @version: v1.0.0
 */
@Getter
public enum SexEnum {
    M("男"),
    W("女"),
    N("未知");

    private String desc;
    SexEnum(String desc){
       this.desc = desc;
    }

}
