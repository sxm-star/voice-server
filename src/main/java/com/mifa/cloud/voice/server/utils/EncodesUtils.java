package com.mifa.cloud.voice.server.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

public class EncodesUtils {
    private static SecureRandom random = new SecureRandom();

    public static byte[] hmacSha1(byte[] input, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            return mac.doFinal(input);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return key;
    }

    public static boolean isMacValid(byte[] expected, byte[] input, byte[] key) {
        byte[] actual = hmacSha1(input, key);
        return Arrays.equals(expected, actual);
    }

    public static byte[] generateHmacSha1Key() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
            keyGenerator.init(160);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] aesEncrypt(byte[] input, byte[] key) {
        return aes(input, key, 1);
    }

    public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
        return aes(input, key, iv, 1);
    }

    public static String aesDecrypt(byte[] input, byte[] key) {
        byte[] decryptResult = aes(input, key, 2);
        return new String(decryptResult);
    }

    public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
        byte[] decryptResult = aes(input, key, iv, 2);
        return new String(decryptResult);
    }

    private static byte[] aes(byte[] input, byte[] key, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(mode, secretKey);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return key;
    }

    private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(mode, secretKey, ivSpec);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return iv;
    }

    public static byte[] generateAesKey() {
        return generateAesKey(128);
    }

    public static byte[] generateAesKey(int keysize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keysize);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] generateIV() {
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return bytes;
    }

    private static final String DEFAULT_URL_ENCODING = "UTF-8";
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            .toCharArray();

    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    public static String encodeUrlSafeBase64(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input);
    }

    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
        }
        return new String(chars);
    }

    /**
     * 解密
     * @param pwd 密码
     * @param secretKey 秘钥
     * @return
     */
    public static String decrypt(String pwd, String secretKey) {
        return aesDecrypt(decodeBase64(pwd), decodeBase64(secretKey));
    }

    public static void main(String[] args) throws IOException {
        String phone = "18720987043";

        String salt = encodeBase64(generateAesKey());
        System.out.println("salt" +   salt);

        String securePhone = encodeBase64(aesEncrypt(phone.getBytes("UTF-8"), decodeBase64(salt)));

        System.out.println("加密之后---" + securePhone);
        System.out.println(aesDecrypt(decodeBase64(securePhone), decodeBase64(salt)));
    }
}
