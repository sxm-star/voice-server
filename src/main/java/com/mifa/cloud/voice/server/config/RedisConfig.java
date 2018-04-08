package com.mifa.cloud.voice.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisParamConfig redisParamConfig;

    @Bean
    public ShardedJedisPool shardedJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisParamConfig.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisParamConfig.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisParamConfig.getMaxWaitMillis());
        List<JedisShardInfo> jedisShardInfos = new ArrayList<>();
        jedisShardInfos.add(new JedisShardInfo(redisParamConfig.getHost(), redisParamConfig.getPort()));
        return new ShardedJedisPool(jedisPoolConfig, jedisShardInfos);
    }

}
