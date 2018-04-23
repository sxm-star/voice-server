package com.mifa.cloud.voice.server.api.jx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    /**
     * 应用ID
     */
    private String appID;
}
