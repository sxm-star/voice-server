package com.mifa.cloud.voice.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SAAS 语音首页统计信息
 * @author: songxm
 * @date: 2018/5/14 19:14
 * @version: v1.0.0
 */
@Service
@Slf4j
public class HomeService {

    @Autowired
    AccountCapitalService accountCapitalService;
    @Autowired
    CustomerLoginInfoService loginInfoService;

    public void homeStatistic(String contractNo){

    }
}
