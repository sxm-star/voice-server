package com.mifa.cloud.voice.server.api.jx;

import com.alibaba.fastjson.JSON;
import com.mifa.cloud.voice.server.api.jx.dto.JxVoiceVcodeReqDto;
import com.mifa.cloud.voice.server.component.properties.AppProperties;
import com.mifa.cloud.voice.server.utils.BaseDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: songxm
 * @date: 2018/4/24 14:39
 * @version: v1.0.0
 */
@Component
@Slf4j
public class JxVoiceManager {
    @Autowired
    AppProperties appProperties;

    private static final String SIG_PARAM_STR = "?sig=";
    private static final String CAPTCHA_URL = "/api/v1.0.0/voice/verify";
    private static final String NOTIFICATION_URL = "/api/v1.0.0/voice/notify";
    private static final String CLICK_CALL_URL = "/voice/clickcall";
    private static final String JSON_CONTENT_TYPE = "application/json;charset=utf-8";
    private static final String CHARSET_UTF8 = "UTF-8";
    @Value("${mifa.cloud.https.timeout:10000}")
    private int timeOut;

    @Autowired
    private CloseableHttpClient httpSSLClient;

    /**
     * 获取yyyyMMddHHmmss格式时间戳
     * @return
     */
    public  String getTimeStamp(){
        return new SimpleDateFormat(BaseDateUtils.YMDHMS).format(new Date());
    }
    /**
     * 获取Authorization
     * @param timestamp
     * @return
     */
    public  String getAuthorization(String timestamp){
        return Base64.encodeBase64String((appProperties.getJixinVoice().getAccountId() +":"+timestamp).getBytes());
    }

    /**
     * 获取sig字符串
     * @param timestamp
     * @return
     */
    public  String getSig(String timestamp){
        return DigestUtils.sha1Hex(appProperties.getJixinVoice().getAccountId()+appProperties.getJixinVoice().getAuthToken()+timestamp).toLowerCase();
    }

    /*获取请求完整URL*/
    private  String getUrl(String functionUrl,String sig){
        return appProperties.getJixinVoice().getVoiceUrl() + functionUrl + SIG_PARAM_STR +sig;
    }
    public String templateVoiceVcodeSend(JxVoiceVcodeReqDto reqDto){
        String timestamp = getTimeStamp();
        String jsonParams = JSON.toJSONString(reqDto);
        try {
            Map<String,Object> map = new HashMap<>();
            getResponse(map, getHttpPost(CAPTCHA_URL,getAuthorization(timestamp),jsonParams.length(),jsonParams));
        }catch (Exception e){
            log.error("语音验证码发送异常:{}",e);
        }

        return null;
    }

    public HttpPost getHttpPost(String taskUrl,String authorization,int length,String paramJson){
        HttpPost httpPost = new HttpPost(appProperties.getJixinVoice().getVoiceUrl() + taskUrl);
        httpPost.addHeader("Content-Type",JSON_CONTENT_TYPE );
        httpPost.addHeader("Accept", JSON_CONTENT_TYPE);
        httpPost.addHeader("Authorization", authorization);
        httpPost.addHeader("Content-Length", length+"");
        StringEntity se = new StringEntity(JSON.toJSONString(paramJson), CHARSET_UTF8);
        httpPost.setEntity(se);
        // 设置超时
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut)
                .build();
        httpPost.setConfig(reqConfig);
        return  httpPost;
    }

    public Map getResponse(Map<String,Object> t,HttpPost httpPost){
        try {
            String resultStr;
            HttpResponse response = httpSSLClient.execute(httpPost);
            // 判断返回码
            int ret_code = response.getStatusLine().getStatusCode();
            if (ret_code == 200) {
                HttpEntity entity = (HttpEntity) response.getEntity();
                resultStr = IOUtils.toString(entity.getContent(), "utf-8");
                log.info("三方服务响应结果:{}",resultStr);
                return JSON.parseObject(resultStr,Map.class);
            }else {
                log.warn("调用接口{} 返回失败,{},返回数据:{}", httpPost.getURI(), response.getStatusLine(),
                        IOUtils.toString(response.getEntity().getContent()));
                return (Map)null;
            }
        }catch (Exception e){
            log.error("调用三方服务异常:{}",e);
            return (Map) null;
        }

    }

}
