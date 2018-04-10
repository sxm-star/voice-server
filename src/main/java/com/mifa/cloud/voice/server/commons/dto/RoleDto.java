package com.mifa.cloud.voice.server.commons.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: sxm
 * @date: 2018/4/10 15:10
 * @version: v1.0.0
 */
@Data
public class RoleDto {
    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名称
     */
    @NotEmpty
    private String roleName;

    /**
     * 备注
     */
    @NotEmpty
    private String roleRemark;

    /**
     * 状态0-正常；1-已停用
     */
    @NotEmpty
    private Integer roleStatus;

    /**
     * 类型0-普通租户；1-系统管理员
     */
    @NotEmpty
    private Integer roleType;

}
