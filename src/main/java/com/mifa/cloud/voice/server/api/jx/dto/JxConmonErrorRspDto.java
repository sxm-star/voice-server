package com.mifa.cloud.voice.server.api.jx.dto;

import lombok.Data;

/**
 * @author: songxm
 * @date: 2018/4/23 11:34
 * @version: v1.0.0
 */
@Data
public class JxConmonErrorRspDto {
    /**
     * 消息码
     */
    private String code;
    /**
     * 消息说明
     */
    private String msg;

    /**
     * 响应时间戳  格式为yyyyMMddHHmmss,如20160825153024
     */
    private String created;

    /**
     * 对应错误的建议信息，正确的响应则该字段为空
     */
    private String suggest;
}
