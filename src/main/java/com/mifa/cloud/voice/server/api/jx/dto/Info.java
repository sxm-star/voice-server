package com.mifa.cloud.voice.server.api.jx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    /**
     * 应用ID
     */
    private String appID;

    private String sessionID;

    private String callID;
}
