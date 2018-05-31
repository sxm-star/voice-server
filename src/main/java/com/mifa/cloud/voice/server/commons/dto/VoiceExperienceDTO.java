package com.mifa.cloud.voice.server.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: songxm
 * @date: 2018/5/23 20:17
 * @version: v1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceExperienceDTO {

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 体验模板内容
     */
    private String content;
    /**
     * 语音可体验的总条数
     */
    private Integer experienceTotalCnt;

    /**
     * 语音当前使用的条数
     */
    private Integer currentCnt;

    /**
     * 剩余条数
     */
    private Integer leftCnt;
}
