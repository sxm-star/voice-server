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
public class SystemRoleResourceDO implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 角色id
     */
    @NotEmpty
    private Long roleId;

    /**
     * 资源ID
     */
    @NotEmpty
    private Long resourceId;

}