package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * @author: songxm
 * @date: 2018/4/19 12:40
 * @version: v1.0.0
 */
@Data
@ApiModel("号码组响应结构")
public class ContactGroupRspDto {
    @ApiModelProperty("号码组ID")
    private Long id;

    /**
     * 主键
     */
    @NotEmpty
    @ApiModelProperty("批次任务ID")
    private String taskId;

    /**
     * 通讯簿组表
     */
    @ApiModelProperty("通讯簿组表")
    private String groupName;

    /**
     * 通讯录组内成员数量
     */
    @ApiModelProperty("通讯录组内成员数量")
    private Integer groupCnt;

    /**
     * 来源
     */
    @ApiModelProperty("来源")
    private String source;
    @ApiModelProperty("上传时间")
    private Date createdAt;

}
