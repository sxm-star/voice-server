package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2018/4/18.
 * 文件类型枚举类
 */
@Getter
public enum FileTypeEnums {

    /**
     * 图片
     */
    IMAGE("image"),

    /**
     * 文件
     */
    EXCEL("excel"),

    /**
     * 语音
     */
    VOICE("voice");

    private String desc;

    FileTypeEnums(String desc){
        this.desc = desc;
    }


}
