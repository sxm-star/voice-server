package com.mifa.cloud.voice.server.listener;

import com.mifa.cloud.voice.server.commons.enums.BaseBizActionEnum;
import com.mifa.cloud.voice.server.commons.enums.TemplateBizEnum;
import com.mifa.cloud.voice.server.commons.event.BaseBizAction;
import com.mifa.cloud.voice.server.commons.event.ParseLogEvent;
import com.mifa.cloud.voice.server.commons.event.VoiceGivenEvent;
import com.mifa.cloud.voice.server.component.properties.AppProperties;
import com.mifa.cloud.voice.server.exception.BaseBizException;
import com.mifa.cloud.voice.server.pojo.AccountCapitalDO;
import com.mifa.cloud.voice.server.pojo.CustomerExperienceDO;
import com.mifa.cloud.voice.server.pojo.VoiceTemplateDO;
import com.mifa.cloud.voice.server.service.AccountCapitalService;
import com.mifa.cloud.voice.server.service.CustomerExperienceService;
import com.mifa.cloud.voice.server.service.ParseLogService;
import com.mifa.cloud.voice.server.service.TemplateVoiceService;
import com.mifa.cloud.voice.server.utils.SeqProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: songxm
 * @date: 2018/5/24 11:58
 * @version: v1.0.0
 */
@Component
@Slf4j
public class BaseBizListener {
    @Autowired
    CustomerExperienceService customerExperienceService;
    @Autowired
    TemplateVoiceService templateVoiceService;
    @Autowired
    AccountCapitalService accountCapitalService;
    @Autowired
    AppProperties appProperties;
    @Autowired
    ParseLogService parseLogService;


    @EventListener
    public void handler(VoiceGivenEvent baseBizEvent) {
        log.info("接收到行为事件:{}", baseBizEvent);

        if (baseBizEvent != null && baseBizEvent.getBaseBizAction() != null) {
            BaseBizAction baseBizAction = baseBizEvent.getBaseBizAction();
            BaseBizActionEnum baseBizActionEnum = baseBizAction.getBaseBizActionEnum();
            switch (baseBizActionEnum) {
                case GIVE_TEN_VOICE_EXPERIENCE:
                    List<VoiceTemplateDO> list = templateVoiceService.queryListByWhere(VoiceTemplateDO.builder().templateBiz(TemplateBizEnum.FREE_EXPERIENCE.getCode()).build());
                    if (null != list && list.size() > 0) {
                        int cnt = customerExperienceService.save(CustomerExperienceDO.builder().contractNo(baseBizAction.getTargetId()).content(list.get(0).getVoiceContent()).templateId(list.get(0).getTemplateId()).experienceTotalCnt(appProperties.getTenant().getFreeVoiceCnt()).currentCnt(0).build());
                        log.info("用户:{},获的免费体验情况:{}", baseBizAction.getTargetId(), cnt);
                        //初始化账户
                        int upCnt = accountCapitalService.save(AccountCapitalDO.builder().contractNo(baseBizAction.getTargetId()).accountId(SeqProducerUtil.getAccountNo()).availableAmount(appProperties.getTenant().getFreeAmount()).creditAmount(0L).freezeAccount(0L).totalAmount(0L).build());
                        if (upCnt < 1) {
                            log.warn("用户:{},赠送的体验分失败",baseBizAction.getTargetId());
                            throw new BaseBizException("500", "赠送体验分失败!");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @EventListener
    public void handlerParse(ParseLogEvent parseLogEvent) {
        log.info("接收到行为事件:{}", parseLogEvent);

        if (parseLogEvent != null) {

        }
    }
}
