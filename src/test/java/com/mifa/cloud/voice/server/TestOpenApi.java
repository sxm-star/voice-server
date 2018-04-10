package com.mifa.cloud.voice.server;

import com.google.common.collect.ImmutableMap;
import com.mifa.cloud.voice.server.api.aliyun.AliyunVoiceApi;
import com.mifa.cloud.voice.server.api.aliyun.dto.TtsReqDto;
import com.mifa.cloud.voice.server.api.aliyun.enums.AliyunVoiceEnum;
import com.mifa.cloud.voice.server.api.aliyun.enums.TtsDataEnum;
import com.mifa.cloud.voice.server.component.properties.AppProperties;
import com.mifa.cloud.voice.server.utils.BaseDateUtils;
import com.mifa.cloud.voice.server.utils.BaseJsonUtils;
import com.mifa.cloud.voice.server.utils.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author: sxm
 * @date: 2018/4/9 16:51
 * @version: v1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestOpenApi {

    @Autowired
    AliyunVoiceApi aliyunVoiceApi;
    @Autowired
    AppProperties appProperties;

    @Test
    public void testAliyunApi(){
        System.out.println( AliyunVoiceEnum.SINGLECALLBYTTS);
        System.out.println(BaseDateUtils.STANDARD_FORMAT.format(new Date()));

        System.out.println(IdWorker.sequenceMask);
    }

    @Test
    public void testAliyunVoiceApi(){
        TtsReqDto ttsReqDto = new TtsReqDto();
        ttsReqDto.setAction(AliyunVoiceEnum.SINGLECALLBYTTS.toString());
        ttsReqDto.setCalledNumber("13251022729");
        ttsReqDto.setTimestamp(BaseDateUtils.STANDARD_FORMAT.format(new Date()));
        ttsReqDto.setSignatureVersion("1.0");
        ttsReqDto.setTtsCode("TTS_130810023");
        ttsReqDto.setTtsParam(BaseJsonUtils.writeValue(ImmutableMap.of("product", "123456", "code", "12345")));
        ttsReqDto.setFormat(TtsDataEnum.XML.toString());
        ttsReqDto.setCalledNumber("057128857587");
        ttsReqDto.setVolume(100);
        ttsReqDto.setAccessKeyId(appProperties.getAliyunVoice().getAccessId());
        ttsReqDto.setPlayTimes(1);
        ttsReqDto.setOutId("111111");
    }
}
