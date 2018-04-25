package com.mifa.cloud.voice.server.commons.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 语音类型枚举
 * @author: songxm
 * @date: 2018/4/16 16:32
 * @version: v1.0.0
 */
@ApiModel("模板类型")
@Getter
public enum VoiceTypeEnum {
    @ApiModelProperty("文本类型")
    TEXT("文本类型"),
    @ApiModelProperty("语音类型")
    VOICE("语音类型");

    private String desc;
    VoiceTypeEnum(String desc){
        this.desc = desc;
    }

    public static String getDesc(String name){
        for (VoiceTypeEnum itemEnum:VoiceTypeEnum.values()) {
            if (itemEnum.name().equals(name)){
                return  itemEnum.name();
            }
        }
        return name;
    }
}
