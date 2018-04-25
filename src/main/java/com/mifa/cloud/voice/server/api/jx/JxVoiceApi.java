package com.mifa.cloud.voice.server.api.jx;

import com.mifa.cloud.voice.server.api.jx.dto.JxVoiceVcodeReqDto;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: songxm
 * @date: 2018/4/23 11:30
 * @version: v1.0.0
 */
public interface JxVoiceApi {

    /**
     * 语音验证码接入
     * @param voiceVcodeReqDto  语音验证码
     * @return
     */
    @POST("/api/v1.0.0/voice/verify")
    String templateVoiceVcodeSend(@Header("Authorization") String authorization,@Header("Content-Length") String length,@Query("sig") String sig , @Body JxVoiceVcodeReqDto voiceVcodeReqDto);
}
