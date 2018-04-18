package com.mifa.cloud.voice.server.component.task;

import com.mifa.cloud.voice.server.service.ContactTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: songxm
 * @date: 2018/4/18 15:41
 * @version: v1.0.0
 */
@Slf4j
@Component
public class ContactTask {

    @Autowired
    ContactTaskService contactTaskService;

    @Scheduled(initialDelay = 30*1000, fixedDelay = 1*60*1000)
    public void parseContact(){
        log.info("后台解析通讯录模板开始");

    }
}
