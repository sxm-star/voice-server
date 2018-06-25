package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author: songxm
 * @date: 2018/5/28 17:47
 * @version: v1.0.0
 */
@Data
@ApiModel("系统拨打时间设置响应信息")
public class SystemJobTimeRspDTO {
    private String id;

    /**
     * 0:工作日; 1:节假日
     */
    private String callTimeType;

    /**
     * 允许时间段开始时间段 hh:ss
     */
    private String callAllowTimeStart;

    /**
     * 允许时间段结束时间段 hh:ss
     */
    private String callAllowTimeEnd;

    /**
     * 0:正常；1:停用
     */
    private String status;
}
