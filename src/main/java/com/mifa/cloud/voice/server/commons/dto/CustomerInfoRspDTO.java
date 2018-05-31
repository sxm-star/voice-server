package com.mifa.cloud.voice.server.commons.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author: songxm
 * @date: 2018/5/28 14:25
 * @version: v1.0.0
 */
@Data
public class CustomerInfoRspDTO {
    /**
     * 客户号 租户ID或者用户ID
     */
    private String contractNo;

    /**
     * 客户类型 1:企业；2:个人
     */
    private String contractType;

    /**
     * 登录号 邮箱或者手机号
     */
    private String loginName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 客户角色ID
     */
    private Long contractNoRoleId;


    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 昵称名字
     */
    private String nickName;

    /**
     * 登录状态 1:正常SUCCESS；2:被冻结LOCK
     */
    private String loginStatus;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private Long lastLoginTime;

    /**
     * 注册时间
     */
    private Date createdAt;

    /**
     * 账户可用余额
     */
    private Long availableAmount;
}
