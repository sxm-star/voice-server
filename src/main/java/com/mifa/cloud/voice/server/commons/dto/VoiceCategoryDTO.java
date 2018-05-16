package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: songxm
 * @date: 2018/4/13 13:30
 * @version: v1.0.0
 */
@Data
@ApiModel("模板类目响应实体信息")
public class VoiceCategoryDTO {

    @ApiModelProperty("模板ID号")
    private Integer categoryId;

    /**
     * 父类目parent_id=0时，代表的是一级的类目
     */
    @ApiModelProperty("模板父ID号")
    private Integer parentId;

    /**
     * 分类名称
     */
    @ApiModelProperty("模板类目名称")
    private String name;

    @ApiModelProperty("业务类型标识")
    private String bizType;

    @ApiModelProperty("创建时间")
    private Date createdAt;
}
