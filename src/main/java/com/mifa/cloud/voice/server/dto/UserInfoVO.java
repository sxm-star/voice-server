package com.mifa.cloud.voice.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2018/4/11.
 */
@Data
@Builder
@ApiModel("获得用户信息返回实体类")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {

    /**
     * 客户号 租户ID或者用户ID
     */
    @ApiModelProperty("用户ID")
    private String contractNo;

    /**
     * 登录号 邮箱或者手机号
     */
    @ApiModelProperty("账号")
    private String loginName;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号码")
    private String mobile;

    /**
     * 登录密码
     */
    @ApiModelProperty("密码")
    private String loginPasswd;

    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String avatarUrl;

    /**
     * 认证类型
     */
    @ApiModelProperty("认证类型；0-个人，1-企业，-1-未认证")
    private String authType;

}
