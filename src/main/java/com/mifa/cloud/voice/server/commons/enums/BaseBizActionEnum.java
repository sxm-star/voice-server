package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/24 11:52
 * @version: v1.0.0
 */
@Getter
public enum  BaseBizActionEnum {
    GIVE_TEN_VOICE_EXPERIENCE("赠送10条语音"),

    PARSE_LOG("解析文件");

    private String desc;
    BaseBizActionEnum(String desc){
        this.desc = desc;
    }
}
