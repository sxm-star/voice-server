package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/11 17:31
 * @version: v1.0.0
 */
@Getter
public enum ChannelEnum {

    JIXIN("即信");

    private String name;

    ChannelEnum(String name){
        this.name = name;
    }
}
