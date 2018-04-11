package com.mifa.cloud.voice.server.service.impl;

import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.config.StaticConst;
import com.mifa.cloud.voice.server.service.VerficationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/4/10.
 */
@Component
@Profile({"prod"})
public class VerficationServiceImplProd implements VerficationService{

    @Autowired
    private KeyValueDao keyValueDao;

    @Override
    public String getmobileAuthCodeFromCache(String mobile) {
        // 校验验证码
        String mobileAuthCode = (String) keyValueDao.get(StaticConst.MOBILE_SMS_KEY + mobile);
        return mobileAuthCode;
    }
}
