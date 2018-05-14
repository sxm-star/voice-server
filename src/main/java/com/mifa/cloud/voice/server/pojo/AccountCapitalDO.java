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
@Table(name="t_account_capital")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCapitalDO extends BaseDataDo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 账户号
     */
    @NotEmpty
    private String accountId;

    /**
     * 客户号
     */
    @NotEmpty
    private String contractNo;

    /**
     * 可用金额
     */
    private Long availableAmount;

    /**
     * 冻结资金
     */
    private Long freezeAccount;

    /**
     * 总金额
     */
    private Long totalAmount;

    /**
     * 信用额度,可透支额度
     */
    private Long  creditAmount;

    /**
     * 上期起初余额
     */
    private Long lastPeriodBal;

    /**
     * 本期期初余额
     */
    private Long currPeriodBal;

}