package com.mifa.cloud.voice.server.api.aliyun;

import com.mifa.cloud.voice.server.api.aliyun.dto.TtsRspDto;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.Map;

/**
 * @author: sxm
 * @date: 2018/4/4 11:24
 * @version: v1.0.0
 */
public interface AliyunVoiceApi {

    /**
     * 文本转语音
     * @return
     */
    @GET("/services/msgsend.asmx/SendMsg")
    TtsRspDto SingleCallByTts(@QueryMap Map<String,Object> ttsReqDto);
}
