package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Administrator on 2018/4/10.
 * 用户登陆返回值
 */
@Data
@Builder
@ApiModel("登陆返回实体类")
public class UserLoginVO {


    @ApiModelProperty("用户ID")
    private String contractNo;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("是否是管理员（0：否；1：是）")
    private String isManager;

    @ApiModelProperty("客户角色ID")
    private Long contractNoRoleId;

}
