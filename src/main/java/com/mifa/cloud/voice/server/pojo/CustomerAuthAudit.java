package com.mifa.cloud.voice.server.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerAuthAudit implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 客户ID
     */
    private String contractNo;

    /**
     * 认证类型 1-个人认证、2-企业认证
     */
    private String authType;

    /**
     * 企业名称或个人姓名
     */
    private String customerName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 认证时间
     */
    private Date authTime;

    /**
     * 审核状态 1:认证中;2:认证通过,3:认证不通过
     */
    private String auditStatus;

    /**
     * 审核说明
     */
    private String remark;

    /**
     * 审核时间
     */
    private Date autitTime;

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
     * t_customer_auth_audit
     */
    private static final long serialVersionUID = 1L;

}