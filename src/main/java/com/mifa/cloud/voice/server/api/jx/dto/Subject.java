package com.mifa.cloud.voice.server.api.jx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 消息主题
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
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
