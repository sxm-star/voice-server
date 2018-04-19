package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2018/4/19.
 */
@Getter
public enum FileStatusEnum {

    //状态: 0:正常；1:删除

    EFFECTIVE("0", "正常"),

    INVALID("1", "无效");

    private String code;
    private String desc;

    FileStatusEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
