package com.mifa.cloud.voice.server.api.jx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: songxm
 * @date: 2018/5/3 15:21
 * @version: v1.0.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectV2Dto {
    /**
     * 主叫号
     */
    private String caller;
    /**
     * 被叫号
     */
    private String called;
    /**
     * 中间虚拟号码
     */
    private String xNumber;
    /**
     * 业务类型
     */
    private Integer business;

    /**
     * 0:主叫; 1:被叫
     */
    private Integer direction;

    /**
     * 挂断原因
     */
    private Integer   cause;
    /**
     * 挂断方向
     */
    private String  disposition;

    /**
     * 使用tts次数
     */
    private Integer ttsCount;

    /**
     * 总tts长度
     */
    private Integer ttsLength;

    /**
     *  Ivr播放次数
     */
    private Integer ivrCount;

    /**
     *  Ivr播放总时长
     */
    private  Integer  ivrTime;

    /**
     * 总通话时长，包含ivr时间
     */
    private Integer   duration;

    /**
     * 总费用
     */
    private Float cost;

    /**
     * 录音文件名
     */
    private String recordFilename;

    /**
     * 录音文件大小
     */
    private Integer recordSize;

    /**
     * 通话开始时间
     */
    private String   answerTime;

    /**
     *  通话结束时间
     */
    private String  releaseTime;

    private Integer  dialResult;
}
