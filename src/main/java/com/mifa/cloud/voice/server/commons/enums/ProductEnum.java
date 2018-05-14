package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/14 13:55
 * @version: v1.0.0
 */
@Getter
public enum ProductEnum {

    VOICE_NOTIFY("语音通知"),
    VOICE_CODE("语音验证码"),
    SMS_CODE("普通短信验证码");
    private String desc;

    ProductEnum(String desc){
        this.desc = desc;
    }
}
