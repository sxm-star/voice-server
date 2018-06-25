package com.mifa.cloud.voice.server.api.jx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info implements Serializable{
    /**
     * 应用ID
     */
    private String appID;

    private String sessionID;

    private String callID;
}
