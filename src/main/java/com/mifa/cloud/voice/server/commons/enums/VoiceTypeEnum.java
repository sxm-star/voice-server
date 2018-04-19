package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * 语音类型枚举
 * @author: songxm
 * @date: 2018/4/16 16:32
 * @version: v1.0.0
 */
@Getter
public enum VoiceTypeEnum {
    TEXT("文本类型"),
    VOICE("语音类型");

    private String desc;
    VoiceTypeEnum(String desc){
        this.desc = desc;
    }

}
