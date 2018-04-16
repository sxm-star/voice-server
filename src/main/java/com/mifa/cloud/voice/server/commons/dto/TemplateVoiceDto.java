package com.mifa.cloud.voice.server.commons.dto;

import com.mifa.cloud.voice.server.commons.enums.VoiceTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/4/16 16:24
 * @version: v1.0.0
 */
@Data
public class TemplateVoiceDto {
    /**
     * 语音模板id
     */
    private String templateId;

    /**
     * 语音模板名称
     */
    private String templateName;
    /**
     * 客户号
     */
    @NotEmpty
    private String contractNo;

    /**
     * 模板类型 1:语音模板；2：文本模板
     */
    private VoiceTypeEnum templateType;

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
