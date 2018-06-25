package com.mifa.cloud.voice.server.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author songxm
 */
@Data
public class SystemResourceDO implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue
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
    @NotEmpty
    private Integer resourceOrder;

    /**
     * 状态0-正常；1-已停用 
     */
    @NotEmpty
    private Integer resourceStatus;

    /**
     * 分组信息0-普通用户；1-管理员
     */
    @NotEmpty
    private Integer resourceGroup;


}