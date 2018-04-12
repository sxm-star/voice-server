package com.mifa.cloud.voice.server.commons.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: sxm
 * @date: 2018/4/10 15:20
 * @version: v1.0.0
 */
@Data
public class ResourceDto {
    private Long id;

    /**
     * 父主键
     */
    @NotEmpty
    private Long pid;

    /**
     * 模块名称
     */
    @NotEmpty
    private String resourceName;

    /**
     * 链接地址
     */
    @NotEmpty
    private String resourceUrl;

    /**
     * 类型0-模块;1-菜单
     */
    @NotEmpty
    private Integer resourceType;

    /**
     * 菜单图标
     */
    private String resourceIcon;

    /**
     * 排列顺序
     */
    @NotNull
    private Integer resourceOrder;

    /**
     * 状态0-正常；1-已停用
     */
    @NotNull
    private Integer resourceStatus;

    /**
     * 分组信息0-普通用户；1-管理员
     */
    @NotNull
    private Integer resourceGroup;

    /**
     *   是否拥有当前菜单权限,默认false
     */
    private boolean hasResource=false;
    /**
     * 子权限菜单
     */
    private List<ResourceDto> childResource;
}
