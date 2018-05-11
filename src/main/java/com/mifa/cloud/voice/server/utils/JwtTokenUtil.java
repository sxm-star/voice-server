package com.mifa.cloud.voice.server.utils;

import com.alibaba.fastjson.JSON;
import com.mifa.cloud.voice.server.commons.enums.TokenState;
import com.mifa.cloud.voice.server.exception.BaseBizException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 宋烜明
 * @version: v1.0.0
 */
@Slf4j
public class JwtTokenUtil {
    /**
     * 秘钥
     */
    private static final byte[] SECRET = "mzf918mzf918mzf808mzf918mzf918mzf808".getBytes();
    /**
     * 客户阈值
     */
    private static final String PAYLOAD_UID = "uid";
    /**
     * 生成时间
     */
    private static final String PAYLOAD_IAT = "iat";
    /**
     * 过期时间时间戳
     */
    private static final String PAYLOAD_EXT = "ext";
    /**
     * 初始化head部分的数据为
     * {
     * "alg":"HS256",
     * "type":"JWT"
     * }
     */

    private static final JWSHeader header = new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT, null, null, null, null, null, null, null, null, null, null, null);

    /**
     * 生成token，该方法只在用户登录成功后调用
     *
     * @param payload
     * @return
     */
    public static String createToken(Map<String, Object> payload) {
        String tokenString = null;
        // 创建一个 JWS object
        JWSObject jwsObject = new JWSObject(header, new Payload(new JSONObject(payload)));
        try {
            // 将jwsObject 进行HMAC签名
            jwsObject.sign(new MACSigner(SECRET));
            tokenString = jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("签名失败! 异常信息:{}", e);
            throw new BaseBizException("500","token创建失败");
        }
        return tokenString;
    }

    /**
     * 基本的JWT token 生成
     *
     * @param uid        用户ID
     * @param extMinute 过期时间的分钟数 单位分钟
     * @return
     */
    public static String createToken(String uid, Integer extMinute) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, extMinute == null ? 30 : extMinute);
        Map<String, Object> payload = new HashMap();
        payload.put(PAYLOAD_UID, uid);
        payload.put(PAYLOAD_IAT, date.getTime());
        payload.put(PAYLOAD_EXT, calendar.getTime().getTime());
        String tokenString = null;
        // 创建一个 JWS object
        JWSObject jwsObject = new JWSObject(header, new Payload(new JSONObject(payload)));
        try {
            jwsObject.sign(new MACSigner(SECRET));
            tokenString = jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("生成签名失败! 异常信息:{}", e);
            throw new BaseBizException("生成签名失败! 异常信息");
        }
        return tokenString;
    }

    /**
     *
     * @param uid
     * @param timeUnit   时间单位  参考 Calendar.MINUTE    Calendar.MONTH
     * @param extValue
     * @return
     */
    public static String createToken(String uid,Date currentDate,Integer timeUnit, Integer extValue) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(timeUnit, extValue == null ? 0 : extValue);
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put(PAYLOAD_UID, uid);
        payload.put(PAYLOAD_IAT, currentDate.getTime());
        payload.put(PAYLOAD_EXT, calendar.getTime().getTime());
        String tokenString = null;
        JWSObject jwsObject = new JWSObject(header, new Payload(new JSONObject(payload)));
        try {
            jwsObject.sign(new MACSigner(SECRET));
            tokenString = jwsObject.serialize();
        } catch (Exception e) {
            log.error("生成签名失败! 异常信息:{}", e);
            throw new BaseBizException("生成签名失败! 异常信息");
        }
        return tokenString;
    }
    /**
     * 校验token是否合法，返回Map集合,集合中主要包含    state状态码   data鉴权成功后从token中提取的数据
     * 该方法在过滤器中调用，每次请求API时都校验
     *
     * @param token
     * @return Map<String, Object>
     */
       public static Map<String, Object> validToken(String token, String curUid) {
        Map<String, Object> resultMap = new HashMap();
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(SECRET);
            if (jwsObject.verify(verifier)) {
                JSONObject jsonOBj = payload.toJSONObject();
                System.out.println("************" + JSON.toJSONString(jsonOBj));
                resultMap.put("state", TokenState.VALID.toString());
                // 若payload包含ext字段，则校验是否过期
                if (jsonOBj.containsKey(PAYLOAD_EXT)) {
                    long extTime = Long.valueOf(jsonOBj.get(PAYLOAD_EXT).toString());
                    long curTime = System.currentTimeMillis();
                    // 过期了
                    if (curTime > extTime) {
                        resultMap.clear();
                        resultMap.put("state", TokenState.EXPIRED.toString());
                    }
                }
                if (!jsonOBj.containsKey(PAYLOAD_UID)) {
                    log.warn("缺少了关键要素 uid");
                    resultMap.put("state", TokenState.INVALID.toString());
                }
                if (jsonOBj.containsKey(PAYLOAD_UID) && !jsonOBj.get(PAYLOAD_UID).toString().equals(curUid)) {
                    log.warn("关键要素 uid 被纂改了");
                    resultMap.put("state", TokenState.INVALID.toString());
                }
                resultMap.put("data", jsonOBj);

            } else {
                // 校验失败
                resultMap.put("state", TokenState.INVALID.toString());
            }

        } catch (Exception e) {
            log.error("无效的token,异常信息:{}", e);
            resultMap.clear();
            resultMap.put("state", TokenState.INVALID.toString());
        }
        return resultMap;
    }

    /**
     * 示例使用
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 示例生成token
         */
        String token = JwtTokenUtil.createToken("0000001",new Date(), Calendar.MONTH,2);

        /**
         * 示例校验token
         */
        String tokenTest = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1MzExODk5MjMyNDgsInVpZCI6IjAwMDAwMDEiLCJpYXQiOjE1MjU5MTk1MjMyNDh9.ohrizVjZ08ofPUoVvlXU_p3UOT0u_5EUZqFNBo_8Ee4";
        System.out.println(JwtTokenUtil.validToken(tokenTest,"0000001"));
    }
}
