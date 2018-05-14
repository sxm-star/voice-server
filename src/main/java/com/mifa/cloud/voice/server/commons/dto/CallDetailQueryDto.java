package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/4/25 17:42
 * @version: v1.0.0
 */
@Data
@ApiModel("拨打明细查询")
public class CallDetailQueryDto {
    /**
     * 客户号
     */
    @NotEmpty(message = "客户号不能为空")
    @ApiModelProperty(value = "客户号",required = true)
    private String contractNo;

    @NotEmpty(message = "任务号ID不能为空")
    @ApiModelProperty(value = "任务号ID",required = true)
    private String taskId;

    @NotEmpty(message = "计划的批次ID不能为空")
    @ApiModelProperty(value = "计划批次ID",required = true)
    private String batchId;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;


}
