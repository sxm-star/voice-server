package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 */
@Table(name="t_voice_notify_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceNotifyLogDO extends BaseDataDo {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户ID号
     */
    private String contractNo;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 批次ID
     */
    private String data;

    /**
     * 通知类型
     */
    private String notify;

    /**
     * 手机号
     */
    private String called;

    /**
     * 通知结果
     */
    private String callResponse;


}