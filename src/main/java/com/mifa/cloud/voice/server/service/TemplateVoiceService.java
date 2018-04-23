package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.commons.enums.AuditEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.commons.enums.VoiceTypeEnum;
import com.mifa.cloud.voice.server.dao.TemplateVoiceDAO;
import com.mifa.cloud.voice.server.pojo.VoiceTemplateDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/4/16 16:04
 * @version: v1.0.0
 */
@Service
@Slf4j
public class TemplateVoiceService extends BaseService<VoiceTemplateDO> {

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


    public PageDto<VoiceTemplateRspDto> queryTemplateVoiceList(VoiceTemplateQuery query, Integer pageNum, Integer pageSize) {
        VoiceTemplateDO voiceTemplateDO = BaseBeanUtils.convert(query, VoiceTemplateDO.class);
        voiceTemplateDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        PageDto<VoiceTemplateRspDto> pageDto = null;
        try {
            PageInfo<VoiceTemplateDO> pageInfo = this.queryListByPageAndOrder(voiceTemplateDO, pageNum, pageSize, " created_at desc");
            if (pageInfo != null && pageInfo.getList() != null) {
                pageDto = BaseBeanUtils.convert(pageInfo, PageDto.class);
                List<VoiceTemplateDO> doList = pageInfo.getList();
                List<VoiceTemplateRspDto> rspList = new ArrayList<>();
                doList.forEach(voiceTemplateItem -> {
                    VoiceTemplateRspDto voiceTemplateRspDto = BaseBeanUtils.convert(voiceTemplateItem, VoiceTemplateRspDto.class);
                    voiceTemplateRspDto.setTemplateType(voiceTemplateRspDto.getTemplateType().equals(VoiceTypeEnum.TEXT.name()) ? VoiceTypeEnum.TEXT.getDesc() : VoiceTypeEnum.VOICE.getDesc());
                     ;
                    if (AuditEnum.WAIT_AUDIT.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDto.setAuditStatus(AuditEnum.WAIT_AUDIT.getDesc());
                    }
                    if (AuditEnum.AUDIT_SUCCESS.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDto.setAuditStatus(AuditEnum.AUDIT_SUCCESS.getDesc());
                    }
                    if (AuditEnum.AUDIT_FAIL.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDto.setAuditStatus(AuditEnum.AUDIT_FAIL.getDesc());
                    }
                    rspList.add(voiceTemplateRspDto);
                });
                pageDto.setList(rspList);
            }
            return pageDto;
        } catch (Exception e) {
            log.error("语音模板分页查询错误:{}", e);
            return pageDto;
        }
    }

    public boolean delTemplateVoice(String contractNo,String templateId){
        VoiceTemplateDO voiceTemplateDO =  this.queryById(templateId);
        if (voiceTemplateDO.getAuditStatus().equals(AuditEnum.WAIT_AUDIT.getCode())){
            log.warn("等待审核的不能删除");
            return Boolean.FALSE;
        }
        voiceTemplateDO.setStatus(StatusEnum.BLOCK.getCode().toString());
       int cnt =  this.updateByIdSelective(voiceTemplateDO);
        return cnt>0?Boolean.TRUE:Boolean.FALSE;
    }

    public boolean alterTemplateVoice(VoiceTemplateAlterReqDto alterReqDto){
        String templateID= alterReqDto.getTemplateId();
        VoiceTemplateDO voiceTemplateDO =  this.queryById(templateID);
        if (voiceTemplateDO.getAuditStatus().equals(AuditEnum.AUDIT_FAIL.getCode())){
            VoiceTemplateDO alterVoiceTemplateDO = BaseBeanUtils.convert(alterReqDto,VoiceTemplateDO.class);
            alterVoiceTemplateDO.setTemplateId(templateID);
            alterVoiceTemplateDO.setAuditStatus(AuditEnum.WAIT_AUDIT.getCode());
            int cnt = this.updateByIdSelective(alterVoiceTemplateDO);
            return cnt>0?Boolean.TRUE:Boolean.FALSE;
        }else {
            log.warn("审核成功的和待审的不能修改");
            return Boolean.FALSE;
        }
    }
}
