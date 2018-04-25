package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.commons.enums.AuditEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.commons.enums.VoiceTypeEnum;
import com.mifa.cloud.voice.server.dao.TemplateVoiceDAO;
import com.mifa.cloud.voice.server.exception.BaseBizException;
import com.mifa.cloud.voice.server.pojo.VoiceTemplateDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
        voiceTemplateDO.setAuditStatus(AuditEnum.WAIT_AUDIT.getCode());
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

    /**
     * 测试页展示用 ,返回固定顶多5条
     *
     * @return
     */
    public List<VoiceTemplateSelectDto> queryTemplateVoiceSelectList(VoiceTemplateSelectQueryDto queryDto) {
        try {
            VoiceTemplateDO voiceTemplateDO = BaseBeanUtils.convert(queryDto, VoiceTemplateDO.class);
            if (queryDto.getIsTest()) {
                voiceTemplateDO.setContractNo(null);
            }
            voiceTemplateDO.setAuditStatus(AuditEnum.AUDIT_SUCCESS.getCode());
            voiceTemplateDO.setStatus(StatusEnum.NORMAL.getCode().toString());
            PageInfo<VoiceTemplateDO> pageInfo = this.queryListByPageAndOrder(voiceTemplateDO, 1, 5, null);
            if (pageInfo != null && pageInfo.getList() != null) {
                List<VoiceTemplateSelectDto> resList = new ArrayList<>();
                pageInfo.getList().forEach(item -> {
                    resList.add(BaseBeanUtils.convert(item, VoiceTemplateSelectDto.class));
                });
                return resList;
            }
            log.warn("客户:{},未查到任何测试的模板信息,", queryDto.getContractNo());
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("查询异常:");
            throw new BaseBizException("400", "系统查询异常,请联系管理员");
        }

    }

    public boolean delTemplateVoice(String contractNo, String templateId) {
        VoiceTemplateDO voiceTemplateDO = this.queryById(templateId);
        if (voiceTemplateDO == null) {
            throw new BaseBizException("400", "不存在的模板信息");
        }
        if (voiceTemplateDO.getAuditStatus().equals(AuditEnum.WAIT_AUDIT.getCode())) {
            log.warn("等待审核的不能删除");
            throw new BaseBizException("400", "等待审核的不能删除");
        }
        voiceTemplateDO.setStatus(StatusEnum.BLOCK.getCode().toString());
        int cnt = this.updateByIdSelective(voiceTemplateDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean alterTemplateVoice(VoiceTemplateAlterReqDto alterReqDto) {
        String templateID = alterReqDto.getTemplateId();
        VoiceTemplateDO voiceTemplateDO = this.queryById(templateID);
        if (voiceTemplateDO.getAuditStatus().equals(AuditEnum.AUDIT_FAIL.getCode())) {
            VoiceTemplateDO alterVoiceTemplateDO = BaseBeanUtils.convert(alterReqDto, VoiceTemplateDO.class);
            alterVoiceTemplateDO.setTemplateName(StringUtils.isEmpty(alterVoiceTemplateDO.getTemplateName())?null:alterVoiceTemplateDO.getTemplateName());
            alterVoiceTemplateDO.setTemplateType(StringUtils.isEmpty(alterVoiceTemplateDO.getTemplateType())?null:alterVoiceTemplateDO.getTemplateType());
            alterVoiceTemplateDO.setCategoryName(StringUtils.isEmpty(alterVoiceTemplateDO.getCategoryName())?null:alterVoiceTemplateDO.getCategoryName());
            alterVoiceTemplateDO.setVoiceContent(StringUtils.isEmpty(alterVoiceTemplateDO.getVoiceContent())?null:alterVoiceTemplateDO.getVoiceContent());
            alterVoiceTemplateDO.setVoiceUrl(StringUtils.isEmpty(alterVoiceTemplateDO.getVoiceUrl())?null:alterVoiceTemplateDO.getVoiceUrl());
            alterVoiceTemplateDO.setKeyWord(StringUtils.isEmpty(alterVoiceTemplateDO.getKeyWord())?null:alterVoiceTemplateDO.getKeyWord());
            alterVoiceTemplateDO.setTemplateId(templateID);
            alterVoiceTemplateDO.setAuditStatus(AuditEnum.WAIT_AUDIT.getCode());
            log.info("修改前数据 alterReqDto:{}",alterReqDto);
            log.info("将入库修改的数据 alterVoiceTemplateDO:{}",alterVoiceTemplateDO);
            int cnt = this.updateByIdSelective(alterVoiceTemplateDO);
            return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
        } else {
            log.warn("审核成功的和待审的不能修改");
            throw new BaseBizException("400", "审核成功的和待审的不能修改");
        }
    }

    public boolean testTemplateVoice(VoiceTemplateOpenDto openDto) {
        VoiceTemplateDO voiceTemplateDO = this.queryById(openDto.getTemplateId());
        if(voiceTemplateDO==null){
            throw new BaseBizException("400","不存在的模板");
        }
        //走消息队列发送  // TODO: 2018/4/24  走消息队列发送,补充队列
        return Boolean.TRUE;
    }
}
