package com.mifa.cloud.voice.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Administrator on 2018/4/10.
 */
@Data
@ApiModel("用户登录实体类")
public class UserLoginDTO {

    /**
     * 登录号 邮箱或者手机号
     * */
    @ApiModelProperty("登录号 邮箱或者手机号")
    @NotEmpty(message = "账号不能为空")
    private String loginName;

    /**
     * 登录密码
     * */
    @ApiModelProperty("登录密码")
    @NotEmpty(message = "密码不能为空")
    private String loginPasswd;

    /**
     * 最后登录IP
     * */
    @ApiModelProperty("最后登录IP")
    private String lastLoginIp;



}
