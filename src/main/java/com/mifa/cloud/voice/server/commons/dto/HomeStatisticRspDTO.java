package com.mifa.cloud.voice.server.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: songxm
 * @date: 2018/5/14 19:23
 * @version: v1.0.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeStatisticRspDTO {


    private String contractNo;

    /**
     * 可用金额
     */
    private String availableAmount ;

    private String authStatus;

    private String authDesc;

    private String authType;

    private String companyName;

    /**
     * 拨打统计
     */
    private CallCollectDTO callCollect;

    /**
     * 语音体验次数
     */
    private VoiceExperienceDTO voiceExperience;

}
