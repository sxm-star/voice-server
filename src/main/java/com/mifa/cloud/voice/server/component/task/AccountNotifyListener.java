package com.mifa.cloud.voice.server.component.task;

import com.mifa.cloud.voice.server.api.jx.dto.CallBackV2Dto;
import com.mifa.cloud.voice.server.service.AccountCapitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: songxm
 * @date: 2018/5/15 09:43
 * @version: v1.0.0
 */
@Component
@Slf4j
public class AccountNotifyListener {

    @Autowired
    AccountCapitalService accountCapitalService;



    @RabbitListener(queues = "q_voice_account_notify")
    @RabbitHandler
    public void accountNotifyTask(CallBackV2Dto callBackDto) {
        log.info("账户计费接收到信息 :{}",callBackDto);
        if (null==callBackDto) {
            log.warn("账户扣费接收到通知内容为空!");
            return;
        }
        accountCapitalService.calcTenantBill(callBackDto);
    }
}
