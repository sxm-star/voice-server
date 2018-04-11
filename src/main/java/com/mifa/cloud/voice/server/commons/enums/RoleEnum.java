package com.mifa.cloud.voice.server.commons.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author: sxm
 * @date: 2018/4/11 14:25
 * @version: v1.0.0
 */
@ApiModel("角色类型")
@Getter
public enum RoleEnum {
    @ApiModelProperty("正常启用")
    ADMIN(1,"系统管理员"),
    @ApiModelProperty("停止使用")
    TENANT(0,"普通租户");

    Integer code;
    String  msg;
    RoleEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
