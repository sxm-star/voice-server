package com.mifa.cloud.voice.server.commons.dto;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/29 11:21
 * @version: v1.0.0
 */
@Getter
public enum DateTypeEnum {
    DAY(1),
    WEEK(7),
    MONTH(30);

    int day;
    DateTypeEnum(int day){
        this.day = day;
    }
}
