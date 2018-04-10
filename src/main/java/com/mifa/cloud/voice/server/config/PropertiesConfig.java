package com.mifa.cloud.voice.server.config;

import com.mifa.cloud.voice.server.component.properties.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({AppProperties.class})
@Configuration
public class PropertiesConfig {

}