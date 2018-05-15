package com.mifa.cloud.voice.server.controller.openapi;

import com.alibaba.fastjson.JSON;
import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.api.jx.VoiceApi;
import com.mifa.cloud.voice.server.api.jx.dto.Info;
import com.mifa.cloud.voice.server.api.jx.dto.JxVoiceVcodeReqDto;
import com.mifa.cloud.voice.server.api.jx.dto.Subject;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.OpenApiVoiceReqDto;
import com.mifa.cloud.voice.server.component.properties.AppProperties;
import com.mifa.cloud.voice.server.pojo.VoiceTemplateDO;
import com.mifa.cloud.voice.server.service.TemplateVoiceService;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: 2018/5/10  后期单独做外部API 程序,供租户调用
 * @author: songxm
 * @date: 2018/5/10 11:38
 * @version: v1.0.0
 */
@Api(value = "openApi相关API.", tags = "openApi相关API", description = "外部调用")
@RestController
@RequestMapping(AppConst.BASE_OPEN_PATH)
@Slf4j
public class OpenApiController {

    @Autowired
    TemplateVoiceService templateVoiceService;

    @Autowired
    AppProperties appProperties;



    //暂且就是即信
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "tenant token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @RequestMapping(value = "v1/voice-notify",method = RequestMethod.POST)
    public void sendVoiceNotify(@RequestBody @Valid OpenApiVoiceReqDto openApiVoiceReqDto, HttpServletRequest request){
        VoiceTemplateDO voiceTemplateDO = templateVoiceService.queryOne(VoiceTemplateDO.builder().templateId(openApiVoiceReqDto.getTemplateId()).build());
        voiceTemplateDO.getOutChannelName();
        String templateId = voiceTemplateDO.getOutTemplateId();
        String called = openApiVoiceReqDto.getMobile();
        String calledDisplay = "";
        String data = BaseStringUtils.uuid();
        int playTimes = 1;
        List<String> params = new ArrayList<>();
        openApiVoiceReqDto.getParamsValue().entrySet().stream().forEach(entry -> {
            params.add(entry.getValue().toString());
        });
        Info info = Info.builder().appID(appProperties.getJixinVoice().getAppId()).callID("call" + BaseStringUtils.uuid()).sessionID("session" + BaseStringUtils.uuid()).build();
        Subject subject = Subject.builder().templateID(templateId).called(called).calledDisplay(calledDisplay).params(params).playTimes(playTimes).build();
        JxVoiceVcodeReqDto jxVoiceVcodeReqDto = JxVoiceVcodeReqDto.builder()
                .data(data).timestamp(String.valueOf(System.currentTimeMillis())).build();
        jxVoiceVcodeReqDto.setInfo(info);
        jxVoiceVcodeReqDto.setSubject(subject);
        log.info(JSON.toJSONString(jxVoiceVcodeReqDto));
        try {
           String result =  VoiceApi.sendVoiceNotification(jxVoiceVcodeReqDto,appProperties.getJixinVoice().getAccountId(),appProperties.getJixinVoice().getAuthToken());
           log.info("发送结果通知:{}",result);
        }catch (Exception e){
            log.error("openAPI 语音发送失败:{}",e);
        }
    }
}
