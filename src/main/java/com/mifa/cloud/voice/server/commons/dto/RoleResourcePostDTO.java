package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: sxm
 * @date: 2018/4/12 17:53
 * @version: v1.0.0
 */
@Data
@ApiModel("用户权限资源修改请求实体")
public class RoleResourcePostDTO {

    @ApiModelProperty("角色ID")
    @NotNull(message = "角色ID不能为空")
    Long roleId;

    @ApiModelProperty("批量菜单ID号")
    @NotNull(message = "要更新的菜单ID不能为空")
    Long[] resourceIds;
}
