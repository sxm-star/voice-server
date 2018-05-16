package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author songxm
 */
@Table(name="t_account_capital_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCapitalDetailDO extends BaseDataDO {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 凭证号
     */
    @NotEmpty
    private String receiptNo;

    /**
     * 账户号
     */
    @NotEmpty
    private String accountNo;

    /**
     * 客户号
     */
    @NotEmpty
    private String contractNo;

    /**
     * 交易类型
     */
    @NotEmpty
    private String transType;

    /**
     * 币种
     */
    @NotEmpty
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


}