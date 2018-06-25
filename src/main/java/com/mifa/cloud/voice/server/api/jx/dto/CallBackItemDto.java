package com.mifa.cloud.voice.server.api.jx.dto;

import lombok.Data;

/**
 * @author: songxm
 * @date: 2018/4/27 16:34
 * @version: v1.0.0
 */
@Data
public class CallBackItemDto {

    private String uuid;
    private String phone;
    private String extend;
    private String reportTime;
    private String dest;
    private Integer durationTime;
    private String result;
}
