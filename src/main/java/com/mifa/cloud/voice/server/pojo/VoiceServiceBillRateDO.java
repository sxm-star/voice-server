package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 */
@Table(name="t_voice_service_bill_rate")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceServiceBillRateDO extends BaseDataDo {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户ID号
     */
    private String contractNo;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 计费产品名称
     */
    private String productName;

    /**
     * 金额（单位：分）
     */
    private Integer rateAmt;

    /**
     * 计费类型 ，目前统一按时间计费 1: 分/秒
     */
    private String calfeeType;

}