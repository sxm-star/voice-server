package com.mifa.cloud.voice.server.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerAauthPerson implements Serializable {
    /**
     * 客户ID
     */
    private String contractNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 行业
     */
    private String profession;

    /**
     * 身份证正面照url
     */
    private String idCardImgUpUrl;

    /**
     * 身份证反面照url
     */
    private String idCardImgBackUrl;

    /**
     * 手持身份证人像面照片
     */
    private String idCardImgHandheldUrl;

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
     * t_customer_auth_person
     */
    private static final long serialVersionUID = 1L;

}