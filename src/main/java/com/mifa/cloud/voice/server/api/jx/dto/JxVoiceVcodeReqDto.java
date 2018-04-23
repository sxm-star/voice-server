package com.mifa.cloud.voice.server.api.jx.dto;
import lombok.Data;
import java.util.List;

/**
 * 即信语音验证码请求消息体
 * @author: songxm
 * @date: 2018/4/23 11:46
 * @version: v1.0.0
 */
@Data
public class JxVoiceVcodeReqDto {
    /**
     * 用户信息
     */
    private Info info;

    /**
     * 消息主题
     */
    private Subject subject;

    /**
     * 私有数据，回应和通知带回，可被更新 N
     */
    private String data;

    /**
     * 请求时间戳，毫秒
     */
    private String timestamp;
}

/**
 * 用户信息
 */
@Data
class Info {
    /**
     * 应用ID
     */
    private String appID;
}

/**
 * 消息主题
 */
@Data
class Subject {
    /**
     * 被叫号 Y
     */
    private String called;
    /**
     * 被叫显号 N
     */
    private String calledDisplay;
    /**
     * 模板ID Y
     */
    private String templateID;

    /**
     * 模板变量（占位）列表 N
     */
    private List<String> params;

    /**
     * 播放次数 Y
     */
    private Integer playTimes;

    /**
     * 0 关闭，1 开启，语音识别开关 Y
     */
    private Integer dialStatus;
}


