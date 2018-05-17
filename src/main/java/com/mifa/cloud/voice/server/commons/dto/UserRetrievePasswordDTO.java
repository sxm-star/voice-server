package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2018/4/10.
 */
@Data
@ApiModel("用户找回密码实体类")
public class UserRetrievePasswordDTO {

    @ApiModelProperty("用户手机号")
    @NotEmpty(message = "用户手机号不能为空")
    private String mobile;

    /**
     * 新密码
     * */
    @ApiModelProperty("登录密码")
    @NotEmpty(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度应在6-20位")
    private String loginPasswd;

    /**
     * 再次输入密码
     * */
    @ApiModelProperty("再次输入的登录密码")
    @NotEmpty(message = "再次输入密码不能为空")
    private String loginPasswdSecond;



}
