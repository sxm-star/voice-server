package com.mifa.cloud.voice.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2018/4/10.
 */
@ApiModel("短信验证码校验实体类")
@Data
public class UserPwdMobileDTO {

    @ApiModelProperty("手机号码")
    @NotEmpty(message = "手机号码不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$", message = "请输入正确的手机号")
    private String mobile;

    @ApiModelProperty("短信验证码")
    @NotEmpty(message = "短信验证码不能为空")
    private String mobileVerficationCode;
}
