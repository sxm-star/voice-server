package com.mifa.cloud.voice.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;


/**
 * Created by Administrator on 2018/4/9.
 * 用户注册参数dto
 */
@Data
@ApiModel("用户注册实体类")
public class UserRegisterDTO {

    /**
     * 客户类型 1:企业；2:个人
     * */
    @ApiModelProperty("客户类型1:企业；2:个人")
    private String contractType;

    /**
     * 登录号 邮箱或者手机号
     * */
    @ApiModelProperty("登录号 邮箱或者手机号")
    @NotEmpty(message = "用户名不能为空")
    private String loginName;

    /**
     * 登录密码
     * */
    @ApiModelProperty("登录密码")
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 30)
    private String loginPasswd;

    /**
     * 登录密码
     * */
    @ApiModelProperty("重复输入的登录密码")
    @NotEmpty(message = "再次输入密码不能为空")
    private String loginPasswdSecond;

    /**
     * 手机号
     * */
    @ApiModelProperty("手机号")
    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    /**
     * 短信验证码
     * */
    @ApiModelProperty("短信验证码")
    @NotEmpty(message = "验证码不能为空")
    private String mobieAuthCode;

    /**
     * 最后登录IP
     * */
    @ApiModelProperty("最后登录IP")
    private String lastLoginIp;


    /**
     * 注册类型 0:系统自动注册；1:自助注册
     * */
    @ApiModelProperty("注册类型 0:系统自动注册；1:自助注册")
    private String registType;




}
