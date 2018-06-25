package com.mifa.cloud.voice.server.api.aliyun.dto;

import lombok.Data;

/**
 * @author: sxm
 * @date: 2018/4/4 11:40
 * @version: v1.0.0
 */
@Data
public class TtsReqDto {
    private String Action;
    private String CalledNumber;
    private String Timestamp; //格式: yyyy-MM-dd'T'HH:mm:ss'Z'
    private String SignatureVersion;
    private String TtsCode;
    private String TtsParam; // json串 {"product":"123456","code":"12345"}
    private String Format; // XML
    private String SignatureNonce; //642053f8-19a4-4953-8780-cc360deeb149
    private String Version; //2017-05-25
    private String CalledShowNumber; //057128857587
    private Integer Volume;
    private String AccessKeyId; //LTAIBsqMANiyaR9u
    private String Signature; //dBdJ6pXMKOB12VdvKnqVBDdk2ZM
    private Integer PlayTimes;
    private String SignatureMethod;//HMAC-SHA1
    private String RegionId; //cn-hangzhou
    private String OutId;

}
