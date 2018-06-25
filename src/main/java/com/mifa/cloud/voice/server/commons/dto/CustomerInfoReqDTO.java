package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/5/28 14:40
 * @version: v1.0.0
 */
@Data
@ApiModel("客户信息检索参数")
public class CustomerInfoReqDTO {
    /**
     * 客户号 租户ID或者用户ID
     */
    @ApiModelProperty("客户ID号")
    @NotEmpty(message = "客户ID号不能为空")
    private String contractNo;

    /**
     * 登录号 邮箱或者手机号
     */
    @ApiModelProperty("登录号")
    private String loginName;

    /**
     * 手机号
     */
    @ApiModelProperty("原手机号")
    private String mobile;

    @ApiModelProperty("修改的手机号")
    private String newMobile;

    private String loginStatus;

    /**
     * 登录密码
     */
    private String loginPasswd;

    /**
     * 操作人
     */
    private String createdBy;


}
