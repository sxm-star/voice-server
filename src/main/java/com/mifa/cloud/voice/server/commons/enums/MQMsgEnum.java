package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2018/4/19.
 * mq消息队列的名称
 */
@Getter
public enum MQMsgEnum {

    /**
     * 短信验证码
     */
    MOBILE_AUTH_CODE("q_sms"),

    /**
     * 解析号码列表
     */
    ANALYSIS_MOBILE_LIST("analysis_mobile_list");

    private String code;

    MQMsgEnum(String code){
        this.code = code;
    }

}
