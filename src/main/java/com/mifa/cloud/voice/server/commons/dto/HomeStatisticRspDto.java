package com.mifa.cloud.voice.server.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: songxm
 * @date: 2018/5/14 19:23
 * @version: v1.0.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeStatisticRspDto {


    private String contractNo;

    /**
     * 可用金额
     */
    private Long availableAmount;

    private String authStatus;

    private String authDesc;

    private String authType;

    private String companyName;

}
