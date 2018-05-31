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
 * @author songxm
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_customer_voice_experience")
public class CustomerExperienceDO extends BaseDataDO {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户号ID
     */
    private String contractNo;

    /**
     * 语音可体验的总条数
     */
    private Integer experienceTotalCnt;

    /**
     * 语音当前使用的条数
     */
    private Integer currentCnt;



    /**
     * 模板ID
     */
    private String templateId;

    private String content;



}