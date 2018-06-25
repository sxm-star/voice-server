package com.mifa.cloud.voice.server.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拨打统计信息
 * @author: songxm
 * @date: 2018/5/22 09:48
 * @version: v1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallCollectDTO {

    /**
     * 日拨通总数
     */
    private Long calledCnt;

    /**
     * 日未拨通总数
     */
    private Long noCalledCnt;
    /**
     * 日拨打分钟数
     */
    private Long callTime;
}
