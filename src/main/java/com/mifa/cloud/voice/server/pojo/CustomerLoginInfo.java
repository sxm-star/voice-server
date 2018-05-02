package com.mifa.cloud.voice.server.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerLoginInfo implements Serializable {
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
     * 登录密码
     */
    private String loginPasswd;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 密码盐值
     */
    private String salt;

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
     * 激活码
     */
    private String activeCode;

    /**
     * 激活状态 0:待激活；1:已激活
     */
    private String activeStatus;

    /**
     * 注册类型 0:系统自动注册；1:自助注册
     */
    private String registType;

    /**
     * 是否是管理员（0：否；1：是）
     */
    private String isManager;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * t_customer_login_info
     */
    private static final long serialVersionUID = 1L;

}