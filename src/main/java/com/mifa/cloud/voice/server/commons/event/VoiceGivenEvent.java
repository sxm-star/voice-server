package com.mifa.cloud.voice.server.commons.event;

import lombok.Data;

/**
 * @author: songxm
 * @date: 2018/5/31 07:48
 * @version: v1.0.0
 */
@Data
public class VoiceGivenEvent extends BaseBizEvent<String> {

    public VoiceGivenEvent(Object source,String contractNo,String desc){
        super(source,contractNo,desc);
    }
}
