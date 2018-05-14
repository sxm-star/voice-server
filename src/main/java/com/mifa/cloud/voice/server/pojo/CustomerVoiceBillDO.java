package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 */
@Table(name="t_customer_voice_bill")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVoiceBillDO extends BaseDataDo {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue
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
     * 批次ID
     */
    private String data;

    /**
     * 通知类型
     */
    private String notify;

    /**
     * 费用字段 单位分(根据签订的产品服务费率来计费)
     */
    private Integer cost;

    /**
     * 拨打时长 单位秒 
     */
    private Integer duration;

    /**
     * 手机号
     */
    private String called;

}