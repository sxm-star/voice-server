package com.mifa.cloud.voice.server.commons.dto;

import com.mifa.cloud.voice.server.commons.enums.ResouceTypeEnum;
import com.mifa.cloud.voice.server.commons.enums.RoleEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: sxm
 * @date: 2018/4/12 10:08
 * @version: v1.0.0
 */
@Data
@ApiModel("顶级菜单对象实体信息")
public class ResourceFunctionModelDto {
    /**
     * 父主键
     */
    @ApiModelProperty("菜单父ID号,顶级菜单为 0")
    @NotNull(message = "菜单父ID号不能为空")
    private Long pid;
    /**
     * 模块名称
     */
    @NotEmpty(message = "菜单模块名不能为空")
    @ApiModelProperty("模块名称")
    private String resourceName;

    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String resourceIcon;

    @ApiModelProperty("菜单地址")
    @NotEmpty(message = "菜单地址不能为空,顶级菜单默认#")
    private String resourceUrl;

    /**
     * 排列顺序
     */
    @NotNull(message = "菜单排列顺序不能为空")
    @ApiModelProperty("排列顺序")
    private Integer resourceOrder;

    /**
     * 状态0-正常；1-已停用
     */
    @ApiModelProperty("状态0-正常；1-已停用")
    @NotNull(message = "请设置正常的启用状态")
    private StatusEnum statusEnum;

    @ApiModelProperty("类型MODULE模块;MENU:模块下的子菜单")
    @NotNull(message = "请设置菜单类型")
    private ResouceTypeEnum resouceTypeEnum;

    /**
     * 分组信息0-普通用户；1-管理员
     */
    @NotNull
    private RoleEnum resourceGroup;
}
