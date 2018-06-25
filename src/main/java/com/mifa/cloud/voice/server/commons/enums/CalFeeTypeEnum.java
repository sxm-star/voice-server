package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/14 11:50
 * @version: v1.0.0
 */
@Getter
public enum CalFeeTypeEnum {

    CHARGE_BY_TIME("1","分/秒");

    private String code;
    private String desc;

    CalFeeTypeEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }


}
