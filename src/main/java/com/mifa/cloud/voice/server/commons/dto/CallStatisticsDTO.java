package com.mifa.cloud.voice.server.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallStatisticsDTO {
    /**
     * 分钟数
     */
    int cost;

    /**
     * 次数
     */
    int cnt;

    String contractNo;
}
