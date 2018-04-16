package com.mifa.cloud.voice.server.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 */
@Table(name="t_voice_template")
@Data
public class VoiceTemplateDO extends BaseDataDo {
    /**
     * 语音模板id
     */
    @Id
    @GeneratedValue
    private String templateId;

    @Column(name = "template_name")
    private String templateName;

    /**
     * 客户号
     */
    @NotEmpty
    private String contractNo;

    /**
     * 模板类型 1:语音模板；2：文本模板
     */
    private String templateType;

    /**
     * 语音话术类目id
     */
    private Integer categoryId;

    /**
     * 语音话术类目名称
     */
    private String categoryName;

    /**
     * 设置的话术关键词
     */
    private String keyWord;

    /**
     * 状态。可选值:1(正常),2(删除)
     */
    private String status;

    /**
     * 审核状态 0:待审核 1：审核通过；2:审核失败
     */
    private String auditStatus;

    /**
     * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
     */
    private Integer sortOrder;

    /**
     * 语音播放地址
     */
    private String voiceUrl;

    /**
     * 话术语音转文本内容
     */
    private String voiceContent;



}