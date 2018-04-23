package com.mifa.cloud.voice.server.config;

import com.mifa.cloud.voice.server.api.aliyun.AliyunVoiceApi;
import com.mifa.cloud.voice.server.api.jx.JxVoiceApi;
import com.mifa.cloud.voice.server.api.montnets.MontnetsVoiceApi;
import com.mifa.cloud.voice.server.component.properties.AppProperties;
import com.mifa.cloud.voice.server.utils.BaseRetrofitUtils2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.util.Date;

/**
 * @author: sxm
 * @date: 2018/4/10 12:34
 * @version: v1.0.0
 */
@Configuration
@PropertySource(value = {"classpath:application.properties"},ignoreResourceNotFound = true,encoding = "UTF-8")
public class VoiceApiConfig {
    @Autowired(required = false)
    AppProperties appProperties;

    @Bean
    public MontnetsVoiceApi montnetsVoiceApi(){
        return BaseRetrofitUtils2.newBuilder(appProperties.getMontnetsVoice().getVoiceUrl())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .retryWhenTimeout(2)
                .build().create(MontnetsVoiceApi.class);
    }

    @Bean
    public AliyunVoiceApi aliyunVoiceApi(){
        return BaseRetrofitUtils2.newBuilder(appProperties.getAliyunVoice().getVoiceUrl())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .retryWhenTimeout(2)
                .build().create(AliyunVoiceApi.class);
    }

    @Bean
    public JxVoiceApi jxVoiceApi(){
        Date date  = new Date();
        //String authorization =
        //String timeStamp = BaseDateUtils.format(new Date(),BaseDateUtils.YMDHMS);

        return BaseRetrofitUtils2.newBuilder(appProperties.getJixinVoice().getVoiceUrl())
                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .addInterceptors(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        chain.request().newBuilder().
//                        return null;
//                    }
//                })
                //.headers(ImmutableMap.of(HttpHeaders.AUTHORIZATION,"").)
                .retryWhenTimeout(2)
                .build().create(JxVoiceApi.class);
    }

   private  String getSig(String timestamp){
        return DigestUtils.sha1Hex(this.appProperties.getJixinVoice().getAccessId()+this.appProperties.getJixinVoice().getAuthToken()+timestamp).toLowerCase();
    }
}
