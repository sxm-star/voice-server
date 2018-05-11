package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
    @GeneratedValue
    private Long id;

    /**
     * 通知类型
     */
    private String notify;

    /**
     * 平台自定义的批次ID
     */
    private String data;

    /**
     * 手机号
     */
    private String called;

    /**
     * 调用时间
     */
    private Date callTime;

    /**
     * 调用响应结果
     */
    private String callResponse;

}