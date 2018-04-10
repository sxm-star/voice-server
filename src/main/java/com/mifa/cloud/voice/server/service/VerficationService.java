package com.mifa.cloud.voice.server.service;

/**
 * Created by Administrator on 2018/4/10.
 * 验证码service
 */
public interface VerficationService {

    /**
     * 根据手机号从缓存中获取短信验证码
     * */
    String getmobileAuthCodeFromCache(String mobile);

}
