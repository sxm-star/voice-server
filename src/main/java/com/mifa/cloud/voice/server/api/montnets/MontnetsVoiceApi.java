package com.mifa.cloud.voice.server.api.montnets;

import com.mifa.cloud.voice.server.api.montnets.dto.TemplateVoiceReqDto;
import com.mifa.cloud.voice.server.api.montnets.dto.TemplateVoiceRspDto;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 梦网云通讯语音API
 * @author: sxm
 * @date: 2018/4/10 11:29
 * @version: v1.0.0
 */
public interface MontnetsVoiceApi {

    @POST("/voice/v2/std/template_send")
    TemplateVoiceRspDto templateVoiceSend(@Body TemplateVoiceReqDto templateVoiceReqDto);
}
