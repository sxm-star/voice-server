package com.mifa.cloud.voice.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2018/4/11.
 */
@Data
@ApiModel("修改密码参数实体类")
public class UserEditPwdDTO {

    @ApiModelProperty("用户ID")
    @NotEmpty(message = "用户ID不能为空")
    private String contractNo;

    /**
     * 手机号
     * */
    @ApiModelProperty("手机号")
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$", message = "请输入正确的手机号")
    private String mobile;

    /**
     * 短信验证码
     * */
    @ApiModelProperty("短信验证码")
    @NotEmpty(message = "验证码不能为空")
    private String mobieAuthCode;


    @ApiModelProperty("原始密码")
    @NotEmpty(message = "原始密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    @NotEmpty(message = "新密码")
    private String newPassword;

    @ApiModelProperty("新密码确认")
    @NotEmpty(message = "新密码确认")
    private String newPasswordSecond;

}
