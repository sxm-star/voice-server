package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.config.StaticConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/4/10.
 * 验证码service
 */
@Service
public class VerficationService {

    @Autowired
    private KeyValueDao keyValueDao;

    @Value("${spring.profiles.active}")
    private String profileActive;

    /**
     * 根据手机号从缓存中获取短信验证码
     * */
    public String getmobileAuthCodeFromCache(String mobile) {
        if("test".equals(profileActive) || "dev".equals(profileActive)) {
            return "123456";
        }
        // 校验验证码
        String mobileAuthCode = (String) keyValueDao.get(StaticConst.MOBILE_SMS_KEY + mobile);
        return mobileAuthCode;
    }

}
