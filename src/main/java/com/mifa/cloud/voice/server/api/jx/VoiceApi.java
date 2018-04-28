package com.mifa.cloud.voice.server.api.jx;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mifa.cloud.voice.server.api.jx.dto.JxVoiceVcodeReqDto;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class VoiceApi {

    private static final String ACCOUNT_SID = "b1a593ca514fd08adf922d205fecb18e";
    private static final String AUTH_TOKEN = "00343a5a9e1db212774dff67b4f0cc14";
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String BASE_URL = "https://api.139130.com:9999";
    private static final String CAPTCHA_URL = "/api/v1.0.0/voice/verify";
    private static final String NOTIFICATION_URL = "/api/v1.0.0/voice/notify";
    private static final String CLICK_CALL_URL = "/voice/clickcall";
    private static final String SIG_PARAM_STR = "?sig=";
private static final String JSON_CONTENT_TYPE = "application/json;charset=utf-8";
private static final String CHARSET_UTF8 = "UTF-8";


    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        try {
            System.out.println("ssss");
           // sendVoiceCaptcha();
 //           sendVoiceNotification();
//            sendClickToCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*语音验证码DEMO*/
    public static String  sendVoiceCaptcha(JxVoiceVcodeReqDto jxVoiceVcodeReqDto) throws Exception{
        String timestamp = getTimeStamp();
        String sig = getSig(timestamp);
        String url = getUrl(CAPTCHA_URL,sig);
        String authorization = getAuthorization(timestamp);
        String requestContent = getJsonRequestBody(jxVoiceVcodeReqDto);

        HttpURLConnection conn = getConnection(url,authorization,requestContent.length());

        DataOutputStream output = new DataOutputStream(conn.getOutputStream());
        output.write(requestContent.getBytes(CHARSET_UTF8));
        output.close();

       return printResponse(conn);
    }

    /*语音通知DEMO*/
    public static String sendVoiceNotification(JxVoiceVcodeReqDto jxVoiceVcodeReqDto) throws Exception{
        String timestamp = getTimeStamp();
        String sig = getSig(timestamp);
        String url = getUrl(NOTIFICATION_URL,sig);
        String authorization = getAuthorization(timestamp);
        String requestContent = getJsonRequestBody(jxVoiceVcodeReqDto);
        HttpURLConnection conn = getConnection(url,authorization,requestContent.length());

        DataOutputStream output = new DataOutputStream(conn.getOutputStream());
        output.write(requestContent.getBytes(CHARSET_UTF8));
        output.close();

       return printResponse(conn);
    }

    /*点击通话DEMO*/
    public static String  sendClickToCall(JxVoiceVcodeReqDto jxVoiceVcodeReqDto) throws Exception{
        String timestamp = getTimeStamp();
        String sig = getSig(timestamp);
        String url = getUrl(CLICK_CALL_URL,sig);
        String authorization = getAuthorization(timestamp);
        String requestContent = getJsonRequestBody(jxVoiceVcodeReqDto);
        HttpURLConnection conn = getConnection(url,authorization,requestContent.length());

        DataOutputStream output = new DataOutputStream(conn.getOutputStream());
        output.write(requestContent.getBytes(CHARSET_UTF8));
        output.close();

        return printResponse(conn);
    }

    /*获取yyyyMMddHHmmss格式时间戳*/
    private static String getTimeStamp(){
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }

    /*获取sig字符串*/
    private static String getSig(String timestamp){
        return DigestUtils.sha1Hex(ACCOUNT_SID+AUTH_TOKEN+timestamp).toLowerCase();
    }

    /*获取请求完整URL*/
    private static String getUrl(String functionUrl,String sig){
        return BASE_URL + functionUrl + SIG_PARAM_STR +sig;
    }

    /*获取Authorization*/
    private static String getAuthorization(String timestamp){
        return Base64.encodeBase64String((ACCOUNT_SID+":"+timestamp).getBytes());
    }

    /*获取完整HttpURLConnection对象*/
    private static HttpURLConnection getConnection(String url,String authorization,int length) throws Exception {
        HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",JSON_CONTENT_TYPE );
        conn.setRequestProperty("Accept", JSON_CONTENT_TYPE);
        conn.setRequestProperty("Authorization", authorization);
        conn.setRequestProperty("Content-Length", length+"");
        return conn;
    }

    /*获取请求body*/
    private static String getJsonRequestBody(JxVoiceVcodeReqDto jxVoiceVcodeReqDto) throws Exception{
//        String appId = "aac0430e5af2394a4035f635a6399702";
//        String templateId = "10003";
//        String called = "13251022729";
//        String calledDisplay = "95776";
//
//        Map request = new HashMap<String,Object>();
//
//        Map info = new HashMap<String,Object>();
//        info.put("appID",appId);
//
//        Map subject = new HashMap<String,Object>();
//        subject.put("called",called);
//        subject.put("calledDisplay",calledDisplay);
//        subject.put("templateID",templateId);
//
//        List<String> params = new ArrayList<>();
//        params.add("宋烜明");
//
//        subject.put("params",params);
//        subject.put("playTimes",3);
//
//        request.put("info",info);
//        request.put("subject",subject);
//        request.put("data","abc123");
//        request.put("timestamp",String.valueOf(System.currentTimeMillis()));

        //return objectMapper.writeValueAsString(jxVoiceVcodeReqDto);
        return JSON.toJSONString(jxVoiceVcodeReqDto);
    }

    /*打印请求响应结果*/
    private static String printResponse(HttpURLConnection conn) throws IOException {
        StringBuilder response = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String tmp;
        System.out.println("********** Response **********");
        while ((tmp = reader.readLine()) != null) {
            System.out.println(tmp);
            response.append(tmp);
        }
        return response.toString();
    }
}
