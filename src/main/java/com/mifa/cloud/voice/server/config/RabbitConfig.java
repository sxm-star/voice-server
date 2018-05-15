package com.mifa.cloud.voice.server.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: songxm
 * @date: 2018/4/19 14:42
 * @version: v1.0.0
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue fanoutVQueue() {
        return new Queue("q_analysis_mobile_list");
    }

    @Bean
    public Queue jobQueue() {
        return new Queue("q_voice_job_pool");
    }

    @Bean
    public Queue accountNotify(){
        return new Queue("q_voice_account_notify");
    }

}
