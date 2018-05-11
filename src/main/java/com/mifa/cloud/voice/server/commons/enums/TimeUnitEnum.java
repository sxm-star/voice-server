package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

import java.util.Calendar;

/**
 * @author: songxm
 * @date: 2018/5/10 10:41
 * @version: v1.0.0
 */
@Getter
public enum  TimeUnitEnum {

    MONTH(Calendar.MONTH,"单位 月"),
    MINUTE(Calendar.MINUTE,"单位 分"),
    YEAR(Calendar.YEAR,"单位 年");

    private Integer code;
    private String desc;

    TimeUnitEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
