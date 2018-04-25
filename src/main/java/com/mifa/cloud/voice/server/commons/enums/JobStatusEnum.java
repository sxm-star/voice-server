package com.mifa.cloud.voice.server.commons.enums;

import lombok.Getter;

/**
 * @author: songxm
 * @date: 2018/4/23 16:41
 * @version: v1.0.0
 */
@Getter
public enum JobStatusEnum {
    WAIT_START(0,"待启动"),
    RUNNING(1,"运行中"),
    PAUSE(2,"暂停"),
    STOP(3,"停止");

    private String desc;
    private Integer code;
    JobStatusEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(Integer code){
        for (JobStatusEnum itemEnum:JobStatusEnum.values()) {
            if (itemEnum.getCode().equals(code)){
                return  itemEnum.getDesc();
            }
        }
        return String.valueOf(code);
    }
}
