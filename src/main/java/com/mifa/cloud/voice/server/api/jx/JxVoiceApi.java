package com.mifa.cloud.voice.server.api.jx;

import com.mifa.cloud.voice.server.api.jx.dto.JxConmonErrorRspDto;
import com.mifa.cloud.voice.server.api.montnets.dto.TemplateVoiceReqDto;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: songxm
 * @date: 2018/4/23 11:30
 * @version: v1.0.0
 */
public interface JxVoiceApi {

    /**
     * 语音验证码接入
     * @param templateVoiceReqDto
     * @return
     */
    @POST("/v1.0.0/voice/verify?sig={sigParameter}")
    JxConmonErrorRspDto templateVoiceVcodeSend(@Body TemplateVoiceReqDto templateVoiceReqDto);
}
