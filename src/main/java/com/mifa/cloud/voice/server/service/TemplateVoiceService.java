package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.TemplateVoiceDto;
import com.mifa.cloud.voice.server.commons.enums.AuditEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.dao.TemplateVoiceDAO;
import com.mifa.cloud.voice.server.pojo.VoiceTemplateDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: songxm
 * @date: 2018/4/16 16:04
 * @version: v1.0.0
 */
@Service
@Slf4j
public class TemplateVoiceService extends BaseService<VoiceTemplateDO>{

    @Autowired
    TemplateVoiceDAO templateVoiceDAO;

    public Boolean insertTemplateVoice(TemplateVoiceDto templateVoiceDto) {
        VoiceTemplateDO voiceTemplateDO = BaseBeanUtils.convert(templateVoiceDto, VoiceTemplateDO.class);
        voiceTemplateDO.setCreatedBy(templateVoiceDto.getContractNo());
        voiceTemplateDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        voiceTemplateDO.setAuditStatus(AuditEnum.WAIT_AUDIT.name());
        voiceTemplateDO.setTemplateType(templateVoiceDto.getTemplateType().toString());
        int cnt = templateVoiceDAO.insert(voiceTemplateDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
