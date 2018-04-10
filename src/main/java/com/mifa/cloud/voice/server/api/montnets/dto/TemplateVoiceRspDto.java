package com.mifa.cloud.voice.server.api.montnets.dto;

import lombok.Data;

/**
 * @author: sxm
 * @date: 2018/4/10 11:48
 * @version: v1.0.0
 */
@Data
public class TemplateVoiceRspDto {

    /**
     * 模板语音发送请求处理结果：
     * 成功：返回0；
     */
    private int result;
    /**
     * 平台流水编号:result非0时，msgid为0
     */
    private long msgid;
    /**
     * 用户自定义流水编号：默认与请求报文中的custid保持一致，若请求报文中没有custid参数或值为空，返回一个与msgid相同的值
     * result非0时，custid为空
     */
    private int custid;
}
