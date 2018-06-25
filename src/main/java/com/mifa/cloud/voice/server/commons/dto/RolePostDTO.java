package com.mifa.cloud.voice.server.commons.dto;

import com.mifa.cloud.voice.server.commons.enums.RoleEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: sxm
 * @date: 2018/4/11 15:21
 * @version: v1.0.0
 */
@Data
@ApiModel("新增角色实体对象")
public class RolePostDTO {
    @ApiModelProperty("修改操作 主键ID必填; 新增操作选填")
    private Long id;
    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称不能为空")
    @ApiModelProperty("角色名称")
    private String roleName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String roleRemark;

    /**
     * 状态0-正常；1-已停用
     */
    @NotNull(message = "请填写角色启用状态 ")
    @ApiModelProperty("启用状态")
    private StatusEnum roleStatus;

    /**
     * 类型0-普通租户；1-系统管理员
     */
    @NotNull
    @ApiModelProperty("角色类型")
    private RoleEnum roleType;
}
