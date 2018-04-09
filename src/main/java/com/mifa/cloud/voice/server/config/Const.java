package com.mifa.cloud.voice.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/4/9.
 */
@Component
public class Const {

    @Value("${h5.app.url.path}")
    public String H5_URL_PATH;

    @Value("${upload.path}")
    public String UPLOAD_PATH;

}
