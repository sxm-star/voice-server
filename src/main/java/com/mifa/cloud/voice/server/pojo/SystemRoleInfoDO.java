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
public class SystemRoleInfoDO implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue
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