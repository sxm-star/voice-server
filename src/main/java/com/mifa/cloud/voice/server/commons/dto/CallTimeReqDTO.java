package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: songxm
 * @date: 2018/5/28 19:36
 * @version: v1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("时间段信息")
public class CallTimeReqDTO {
    
    /**
     *
     */
    @ApiModelProperty(value = "允许时间段开始时间段 hh:ss",example = "08:30")
    private String callAllowTimeStart;

    /**
     * 允许时间段结束时间段 hh:ss
     */
    @ApiModelProperty(value = "允许时间段结束时间段 hh:ss",example = "09:30")
    private String callAllowTimeEnd;

}
