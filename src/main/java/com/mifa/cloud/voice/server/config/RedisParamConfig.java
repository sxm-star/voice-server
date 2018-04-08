package com.mifa.cloud.voice.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/4/8.
 * redis 参数配置
 */
@Component
@ConfigurationProperties(prefix = "jedis")
@Data
public class RedisParamConfig {

    private String host;
    private Integer port;
    private Integer maxTotal;
    private Integer maxIdle;
    private Long maxWaitMillis;

}
