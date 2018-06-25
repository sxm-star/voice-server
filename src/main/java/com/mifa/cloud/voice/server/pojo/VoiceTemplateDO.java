package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 
 */
@Table(name="t_voice_template")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceTemplateDO extends BaseDataDO {
    /**
     * 语音模板id
     */
    @Id
    @GeneratedValue(generator="UUID")
    private String templateId;

    @Column(name = "template_name")
    private String templateName;

    /**
     * 客户号
     */
    @NotEmpty
    @Column(name = "contract_no")
    private String contractNo;

    /**
     * 模板类型 VOICE:语音模板；TEXT：文本模板
     */
    @Column(name = "template_type")
    private String templateType;

    /**
     * 语音话术类目id
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 语音话术类目名称
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 设置的话术关键词
     */
    @Column(name = "key_word")
    private String keyWord;

    /**
     * 状态。可选值:1(正常),2(删除)
     */
    private String status;

    /**
     * 审核状态 0:待审核 1：审核通过；2:审核失败；3:审核中
     */
    @Column(name = "audit_status")
    private String auditStatus;

    /**
     * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /**
     * 语音播放地址
     */
    @Column(name = "voice_url")
    private String voiceUrl;

    /**
     * 话术语音转文本内容
     */
    @Column(name = "voice_content")
    private String voiceContent;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 供应商三方平台模板ID审核回填渠道商名称
     */
    @Column(name = "out_channel_name")
    private String outChannelName;

    /**
     * 供应商三方平台模板ID回填
     */
    @Column(name = "out_template_id")
    private String outTemplateId;

    /**
     * 业务类型 0:收费;1:赠送的
     */
    private String templateBiz;
    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

}