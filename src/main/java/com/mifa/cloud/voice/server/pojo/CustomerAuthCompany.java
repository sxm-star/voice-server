package com.mifa.cloud.voice.server.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerAuthCompany implements Serializable {
    /**
     * 客户ID
     */
    private String contractNo;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 企业所在地址
     */
    private String companyAddress;

    /**
     * 企业规模
     */
    private String scale;

    /**
     * 经营年限
     */
    private String businessLife;

    /**
     * 行业
     */
    private String profession;

    /**
     * 官网地址
     */
    private String officialWebsiteUrl;

    /**
     * 营业执照号
     */
    private String businessLicenseNo;

    /**
     * 营业执照地址
     */
    private String businessLicenseUrl;

    /**
     * 认证状态 1:认证中;2:认证通过,3:认证不通过
     */
    private String authStatus;

    /**
     * 审核通过、不通过原因
     */
    private String remark;

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
     * t_customer_auth_company
     */
    private static final long serialVersionUID = 1L;

}