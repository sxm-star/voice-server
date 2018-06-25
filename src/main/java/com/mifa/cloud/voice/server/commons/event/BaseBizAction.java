package com.mifa.cloud.voice.server.commons.event;

import com.mifa.cloud.voice.server.commons.enums.BaseBizActionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: songxm
 * @date: 2018/5/24 13:50
 * @version: v1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseBizAction <T>{
    private String targetId;
    private T data;
    BaseBizActionEnum baseBizActionEnum;

    public BaseBizAction (String targetId,BaseBizActionEnum baseBizActionEnum){
        this.targetId = targetId;
        this.baseBizActionEnum = baseBizActionEnum;
    }
}
