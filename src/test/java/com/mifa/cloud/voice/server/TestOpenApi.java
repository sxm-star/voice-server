package com.mifa.cloud.voice.server;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.mifa.cloud.voice.server.api.aliyun.AliyunVoiceApi;
import com.mifa.cloud.voice.server.api.aliyun.dto.TtsReqDto;
import com.mifa.cloud.voice.server.api.aliyun.enums.AliyunVoiceEnum;
import com.mifa.cloud.voice.server.api.aliyun.enums.TtsDataEnum;
import com.mifa.cloud.voice.server.api.jx.JxVoiceApi;
import com.mifa.cloud.voice.server.api.jx.JxVoiceManager;
import com.mifa.cloud.voice.server.api.jx.VoiceApi;
import com.mifa.cloud.voice.server.api.jx.dto.Info;
import com.mifa.cloud.voice.server.api.jx.dto.JxVoiceVcodeReqDto;
import com.mifa.cloud.voice.server.api.jx.dto.Subject;
import com.mifa.cloud.voice.server.api.montnets.MontnetsVoiceApi;
import com.mifa.cloud.voice.server.api.montnets.dto.TemplateVoiceReqDto;
import com.mifa.cloud.voice.server.api.montnets.dto.TemplateVoiceRspDto;
import com.mifa.cloud.voice.server.component.properties.AppProperties;
import com.mifa.cloud.voice.server.utils.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: sxm
 * @date: 2018/4/9 16:51
 * @version: v1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("dev")
public class TestOpenApi {

    @Autowired
    AliyunVoiceApi aliyunVoiceApi;
    @Autowired
    AppProperties appProperties;
    @Autowired
    MontnetsVoiceApi montnetsVoiceApi;
    @Autowired
    JxVoiceApi jxVoiceApi;
    @Autowired
    JxVoiceManager jxVoiceManager;

    @Test
    public void testAliyunApi() {
        System.out.println(AliyunVoiceEnum.SINGLECALLBYTTS);
        System.out.println(BaseDateUtils.STANDARD_FORMAT.format(new Date()));

        System.out.println(IdWorker.sequenceMask);
    }

    @Test
    public void testAliyunVoiceApi() {
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

    @Test
    public void testMontnetsVoiceApi() {
        TemplateVoiceReqDto reqDto = new TemplateVoiceReqDto();
        reqDto.setApikey("02973ba013ed3f1917fb80f8b194cbf9");
        reqDto.setMobile("13251022729");
        reqDto.setContent("您预购的宝贝即将过期，请您尽快付款，如已支付请忽略，谢谢！");
        reqDto.setTmplid("47791");
        String cusId = SeqProducerUtil.getContractNo();
        System.out.println("cusid:" + cusId);
        reqDto.setMsgtype("3");
        reqDto.setCustid(cusId);
        TemplateVoiceRspDto rspDto = montnetsVoiceApi.templateVoiceSend(reqDto);
        System.out.println(BaseJsonUtils.writeValue(rspDto));
    }

    @Test
    public void testJxVoiceApi() {
        String sig = "";
        String timestamp = jxVoiceManager.getTimeStamp();
        String templateId = "10003";
        String called = "13251022729";
        String calledDisplay = "95776";
        int playTimes = 1;
        List<String> params = new ArrayList<>();
        params.add("宋烜明");

        JxVoiceVcodeReqDto jxVoiceVcodeReqDto = JxVoiceVcodeReqDto.builder()
                .info(Info.builder().appID("aac0430e5af2394a4035f635a6399702").build())
                .subject(Subject.builder().templateID(templateId).called(called).calledDisplay(calledDisplay).params(params).playTimes(playTimes).build())
                .data("123").timestamp(String.valueOf(System.currentTimeMillis())).build();

        System.out.println( jxVoiceApi.templateVoiceVcodeSend(jxVoiceManager.getAuthorization(timestamp), JSON.toJSONString(jxVoiceVcodeReqDto).length()+"", jxVoiceManager.getSig(timestamp), jxVoiceVcodeReqDto));
    }

    @Test
    public void testJxVoiceApi2() throws Exception{
        String templateId = "20325";
        String called = "13251022729";
        //String calledDisplay = "95776";
        String calledDisplay = "";
        int playTimes = 1;
        List<String> params = new ArrayList<>();
        params.add("宋烜明");
        Info info =  Info.builder().appID("9b45108124879810c3b081a8aabff9f0").callID("call"+BaseStringUtils.uuid()).sessionID("session"+BaseStringUtils.uuid()).build();
        Subject subject =  Subject.builder().templateID(templateId).called(called).calledDisplay(calledDisplay).params(params).playTimes(playTimes).build();
        System.out.println("info " + JSON.toJSONString(info));
        System.out.println("subject " + JSON.toJSONString(subject));
        JxVoiceVcodeReqDto jxVoiceVcodeReqDto = JxVoiceVcodeReqDto.builder()
                .data("123").timestamp(String.valueOf(System.currentTimeMillis())).build();
        jxVoiceVcodeReqDto.setInfo(info);
        jxVoiceVcodeReqDto.setSubject(subject);

        System.out.println(JSON.toJSONString(jxVoiceVcodeReqDto));
       // jxVoiceManager.templateVoiceVcodeSend(jxVoiceVcodeReqDto);
       // VoiceApi.sendVoiceCaptcha(jxVoiceVcodeReqDto);

        System.out.println(VoiceApi.sendVoiceNotification(jxVoiceVcodeReqDto));
        //jxVoiceManager.templateVoiceVcodeSend();
       // System.out.println( jxVoiceApi.templateVoiceVcodeSend(jxVoiceManager.getAuthorization(timestamp), JSON.toJSONString(jxVoiceVcodeReqDto).length()+"", jxVoiceManager.getSig(timestamp), jxVoiceVcodeReqDto));
    }
}
