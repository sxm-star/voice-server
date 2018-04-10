package com.mifa.cloud.voice.server.utils;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

/**
 * 密码加密工具
 * */
@Component
@Slf4j
public class PasswordUtil {

	/**
     * 生成含有随机盐的密码 
     */  
    public Map<String, String> generate(String password) {
        Random r = new Random();  
        StringBuilder sb = new StringBuilder(16);  
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));  
        int len = sb.length();  
        if (len < 16) {  
            for (int i = 0; i < 16 - len; i++) {  
                sb.append("0");  
            }  
        }  
        String salt = sb.toString();
        log.info("salt: " + salt);
        password = md5Hex(password + salt);  
        char[] cs = new char[48];  
        for (int i = 0; i < 48; i += 3) {  
            cs[i] = password.charAt(i / 3 * 2);  
            char c = salt.charAt(i / 3);  
            cs[i + 1] = c;  
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);  
        }  
        String encryptPassword = new String(cs);
        log.info("加密后的密码："+ encryptPassword);
        Map<String, String> map = new HashMap<>();
        map.put("salt", salt);
        map.put("encryptPassword", encryptPassword);
        return map;
    }  
  
    /** 
     * 校验密码是否正确 
     */  
    public boolean verify(String password, String md5, String salt) {
        char[] cs1 = new char[32];  
        char[] cs2 = new char[16];  
        for (int i = 0; i < 48; i += 3) {  
            cs1[i / 3 * 2] = md5.charAt(i);  
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);  
            cs2[i / 3] = md5.charAt(i + 1);  
        }  
        return this.md5Hex(password + salt).equals(new String(cs1));
    }
  
    /** 
     * 获取十六进制字符串形式的MD5摘要 
     */  
    public String md5Hex(String src) {
        try {  
            MessageDigest md5 = MessageDigest.getInstance("MD5");  
            byte[] bs = md5.digest(src.getBytes());  
            return new String(new Hex().encode(bs));  
        } catch (Exception e) {  
            return null;  
        }  
    }  
  
    public static void main(String[] args) {
        Map<String, String> generateMap = new PasswordUtil().generate("123456");
        System.out.println(new PasswordUtil().verify("123456", generateMap.get("encryptPassword"), generateMap.get("salt")));
    }  
	
	

}
