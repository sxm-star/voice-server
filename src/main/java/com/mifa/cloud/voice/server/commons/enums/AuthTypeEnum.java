package com.mifa.cloud.voice.server.commons.enums;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/14 20:17
 * @version: v1.0.0
 */
@Getter
@ApiModel("认证类型")
public enum  AuthTypeEnum {

    PERSON("1","个人认证"),
    COMPANY("2","企业认证");

    private String code;

    private String type;

    AuthTypeEnum(String code,String type){
        this.code = code;
        this.type = type;
    }

    public static String getDesc(String code){
        for (AuthTypeEnum itemEnum:AuthTypeEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum.getType();
            }
        }
        return code;
    }

    public static String getCode(String desc){
        for (AuthTypeEnum itemEnum:AuthTypeEnum.values()) {
            if (itemEnum.getType().equals(desc)){
                return  itemEnum.getCode();
            }
        }
        return desc;
    }
}
