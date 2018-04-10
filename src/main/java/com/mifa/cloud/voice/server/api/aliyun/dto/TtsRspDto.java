package com.mifa.cloud.voice.server.api.aliyun.dto;

import lombok.Data;

/**
 * @author: sxm
 * @date: 2018/4/4 11:43
 * @version: v1.0.0
 */
@Data
public class TtsRspDto {

    private String RequestId;
    private String Code;
    private String Message;
    private String CallId;
}
