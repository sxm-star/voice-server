package com.mifa.cloud.voice.server.commons.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: songxm
 * @date: 2018/4/20 09:17
 * @version: v1.0.0
 */
@Data
@ApiModel("号码组下拉框响应信息")
public class ContactGroupSelectDto {

    @ApiModelProperty("号码组ID")
    private Long id;
    /**
     * 主键
     */
    @ApiModelProperty("批次任务ID")
    private String taskId;

    /**
     * 通讯簿组表
     */
    @ApiModelProperty("通讯簿组表")
    private String groupName;
}
