package com.mifa.cloud.voice.server.api.jx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: songxm
 * @date: 2018/5/3 15:18
 * @version: v1.0.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallBackV2Dto {

    /**
     * 通用名称
     */
    private String notify;
    /**
     * 共用信息
     */
    private Info info;
    /**
     * 消息主体
     */
    private SubjectV2Dto subject;
    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 扩展字段  通知回传数据
     */
    private String data;


}
