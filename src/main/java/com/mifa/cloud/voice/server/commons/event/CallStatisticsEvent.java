package com.mifa.cloud.voice.server.commons.event;

import com.mifa.cloud.voice.server.commons.dto.CallStatisticsDTO;
import lombok.Data;

/**
 * @author: songxm
 * @date: 2018/6/1 08:35
 * @version: v1.0.0
 */
@Data
public class CallStatisticsEvent extends BaseBizEvent<CallStatisticsDTO> {

    public CallStatisticsEvent(Object source,String contractNo,CallStatisticsDTO desc){
        super(source,contractNo,desc);
    }
}
