package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCapitalDetailDO implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 凭证号
     */
    private String receiptNo;

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 客户号
     */
    private String contractNo;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 交易前余额
     */
    private Long beforeBal;

    /**
     * 交易金额
     */
    private Long amount;

    /**
     * 交易后余额
     */
    private Long afterBal;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间 
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 最后更新时间 
     */
    private Date updatedAt;

    /**
     * 最后更新人
     */
    private String updatedBy;


}