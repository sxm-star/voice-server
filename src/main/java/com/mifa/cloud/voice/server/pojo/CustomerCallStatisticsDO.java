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
import java.util.Date;

/**
 * @author 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_customer_call_statistics")
public class CustomerCallStatisticsDO extends BaseDataDO {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户号
     */
    @NotEmpty
    private String contractNo;

    /**
     * 当天未拨通总数
     */
    private Integer noCalledCnt;

    /**
     * 当天已拨通总数
     */
    private Integer calledCnt;

    /**
     * 当天拨打分钟数
     */
    private Integer callTime;

    /**
     * 备注
     */
    private String note;



    /**
     * 创建时间
     */
    private Date createdAt;

}