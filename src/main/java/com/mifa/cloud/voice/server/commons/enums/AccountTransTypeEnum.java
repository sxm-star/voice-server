package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/5/20 19:12
 * @version: v1.0.0
 */
@Getter
public enum AccountTransTypeEnum {
    RECHARGE("充值"),
    COST("支出");
    private String desc;
    AccountTransTypeEnum(String desc){
        this.desc = desc;
    }

}
