package com.mifa.cloud.voice.server.service.impl;

import com.mifa.cloud.voice.server.service.VerficationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/4/10.
 */
@Component
@Profile({"dev", "test"})
public class VerficationServiceImplDev implements VerficationService{

    @Override
    public String getmobileAuthCodeFromCache(String mobile) {
        return "123456";
    }
}
