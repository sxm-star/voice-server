package com.mifa.cloud.voice.server.pojo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerRecharge implements Serializable {
    /**
     * 充值流水表ID
     */
    private String rechargeId;

    /**
     * 充值金额 单位：分
     */
    private BigDecimal fee;

    /**
     * 客户的支付充值账号
     */
    private String payNo;

    /**
     * 客户号对应的资金账户号
     */
    private String accountNo;

    /**
     * 客户号(用户ID)
     */
    private String contractNo;

    /**
     * 充值时间
     */
    private Date rechargeTime;

    /**
     * 充值状态 1：成功; 0：失败
     */
    private String status;

    /**
     * 充值账号名称（用户名，login_info中的登陆名）
     */
    private String rechargeName;

    /**
     * 充值方式
     */
    private String rechargeWay;

    /**
     * 充值备注 充值套餐类型说明
     */
    private String rechargeRemark;

    /**
     * 平台收款人的账号
     */
    private String sysPayeeNo;

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
     * t_customer_recharge
     */
    private static final long serialVersionUID = 1L;

}