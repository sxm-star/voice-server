package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: sxm
 * @date: 2018/4/12 10:33
 * @version: v1.0.0
 */
@ApiModel("菜单响应实体信息")
@Data
public class MenuDto {
    @ApiModelProperty("菜单的ID号")
    private Long id;

    /**
     * 父主键
     */
    @ApiModelProperty("菜单的父ID号")
    private Long pid;

    /**
     * 模块名称
     */
    @ApiModelProperty("菜单名称")
    private String resourceName;

    /**
     * 链接地址
     */
    @ApiModelProperty("链接地址")
    private String resourceUrl;

    /**
     * 类型0-模块;1-菜单
     */
    @ApiModelProperty("类型0-模块;1-菜单")
    private Integer resourceType;

    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String resourceIcon;

}
