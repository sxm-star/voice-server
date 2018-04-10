package com.mifa.cloud.voice.server.api.aliyun.enums;

import lombok.Getter;

/**
 * @author: sxm
 * @date: 2018/4/9 17:03
 * @version: v1.0.0
 */
@Getter
public enum  AliyunVoiceEnum {
    SINGLECALLBYTTS("SingleCallByTts");

    String action;
    AliyunVoiceEnum(String action){
        this.action = action;
    }

    @Override
    public String toString() {
        return this.action;
    }
}
