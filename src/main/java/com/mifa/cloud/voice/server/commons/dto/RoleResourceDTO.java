package com.mifa.cloud.voice.server.commons.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: sxm
 * @date: 2018/4/10 17:08
 * @version: v1.0.0
 */
@Data
public class RoleResourceDTO {

    private Long id;

    /**
     * 角色id
     */
    @NotNull
    private Long roleId;

    /**
     * 资源ID
     */
    @NotEmpty
    private Long resourceId;
}
