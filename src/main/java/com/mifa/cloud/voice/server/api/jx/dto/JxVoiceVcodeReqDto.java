package com.mifa.cloud.voice.server.api.jx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 即信语音验证码请求消息体
 * @author: songxm
 * @date: 2018/4/23 11:46
 * @version: v1.0.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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


