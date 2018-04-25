package com.mifa.cloud.voice.server.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author songxm
 */
@Configuration
@Slf4j
public class RestTemplateConfig {
    @Value("${mifa.cloud.https.poolSize:3}")
    private int poolSize;

    @Bean
    @Primary
    RestTemplate restTemplate() {
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
        connMgr.setMaxTotal(poolSize + 1);
        connMgr.setDefaultMaxPerRoute(poolSize);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)).build();
        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
        fastjson.setFeatures(SerializerFeature.WriteClassName, SerializerFeature.BrowserCompatible, SerializerFeature.DisableCircularReferenceDetect);
        converters.add(fastjson);
        template.setMessageConverters(converters);
        return template;
    }

    @Bean
    @Primary
    public CloseableHttpClient httpSSLClient() {
        SSLContext sslContext;
        try {
            sslContext = SSLContexts.custom()
                    //对服务端证书的检验
                    .loadTrustMaterial(null,
                            new TrustSelfSignedStrategy() {
                                @Override
                                public boolean isTrusted(X509Certificate[] chain,
                                                         String authType) throws CertificateException {
                                    return true;
                                }
                            })

                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf)
                    .build();

            PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

            connMgr.setMaxTotal(poolSize);
            connMgr.setDefaultMaxPerRoute(poolSize);

            return HttpClients
                    .custom().setSSLSocketFactory(sslsf)
                    .setConnectionManager(connMgr)
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
                    .setKeepAliveStrategy(
                            new DefaultConnectionKeepAliveStrategy())
                    .setMaxConnTotal(poolSize)
                    .setMaxConnPerRoute(poolSize).build();


        } catch (KeyManagementException e) {
           log.error("httpClient pool 初始化 异常信息:{}",e);
        } catch (NoSuchAlgorithmException e) {
            log.error("httpClient pool 初始化 异常信息:{}",e);
        } catch (KeyStoreException e) {
            log.error("httpClient pool 初始化 异常信息:{}",e);
        }
        return null;
    }


}