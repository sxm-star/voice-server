package com.mifa.cloud.voice.server.commons.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @author: songxm
 * @date: 2018/5/24 11:50
 * @version: v1.0.0
 */
@Data
public class BaseBizEvent <T> extends ApplicationEvent {
    protected BaseBizAction<T> baseBizAction;

    protected BaseBizEvent(Object source) {
        super(source);
    }

    protected BaseBizEvent(Object source,BaseBizAction baseBizAction) {
        super(source);
        this.baseBizAction = baseBizAction;
    }

    protected BaseBizEvent(Object source,String targetId,T desc) {
        super(source);
        BaseBizAction<T> baseBizAction = new BaseBizAction<T>();
        baseBizAction.setTargetId(targetId);
        baseBizAction.setData(desc);
        this.baseBizAction = baseBizAction;
    }
}
