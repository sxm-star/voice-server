package com.mifa.cloud.voice.server.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.api.jx.VoiceApi;
import com.mifa.cloud.voice.server.api.jx.dto.Info;
import com.mifa.cloud.voice.server.api.jx.dto.JxVoiceVcodeReqDto;
import com.mifa.cloud.voice.server.api.jx.dto.Subject;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.commons.enums.AuditEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.commons.enums.VoiceTypeEnum;
import com.mifa.cloud.voice.server.component.properties.AppProperties;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.dao.CustomerTaskCallDetailDAO;
import com.mifa.cloud.voice.server.dao.TemplateVoiceDAO;
import com.mifa.cloud.voice.server.exception.BaseBizException;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.pojo.CustomerTaskCallDetailDO;
import com.mifa.cloud.voice.server.commons.dto.VoiceTemplateAuditVO;
import com.mifa.cloud.voice.server.pojo.VoiceTemplateDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    @Autowired
    CustomerTaskCallDetailDAO taskCallDetailDAO;

    @Autowired
    private CustomerLoginInfoService customerLoginInfoService;

    @Autowired
    private ConstConfig aconst;
    @Autowired
    private AppProperties appProperties;



    public Boolean insertTemplateVoice(TemplateVoiceDto templateVoiceDto) {
        VoiceTemplateDO voiceTemplateDO = BaseBeanUtils.convert(templateVoiceDto, VoiceTemplateDO.class);
        voiceTemplateDO.setCreatedBy(templateVoiceDto.getContractNo());
        voiceTemplateDO.setCreatedAt(new Date());
        voiceTemplateDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        voiceTemplateDO.setAuditStatus(AuditEnum.WAIT_AUDIT.getCode());
        voiceTemplateDO.setTemplateType(templateVoiceDto.getTemplateType().toString());
        int cnt = templateVoiceDAO.insert(voiceTemplateDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }


    public VoiceTemplateRspDto queryTemplateVoice(@RequestParam(required = true) String contractNo, @RequestParam(required = true) String templateId) {
        VoiceTemplateDO voiceTemplateDO = new VoiceTemplateDO();
        voiceTemplateDO.setContractNo(contractNo);
        voiceTemplateDO.setTemplateId(templateId);
        voiceTemplateDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        VoiceTemplateDO resDo = this.queryOne(voiceTemplateDO);
        VoiceTemplateRspDto rspDto = null;
        if (resDo != null) {
            rspDto = BaseBeanUtils.convert(resDo, VoiceTemplateRspDto.class);
            rspDto.setAuditStatus(AuditEnum.getDesc(resDo.getAuditStatus()));
            rspDto.setTemplateTypeDesc(VoiceTypeEnum.getDesc(rspDto.getTemplateType()));
            rspDto.setTemplateType(VoiceTypeEnum.getDesc(resDo.getTemplateType()));

        }
        return rspDto;
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
                    if (AuditEnum.AUDIT_ING.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDto.setAuditStatus(AuditEnum.AUDIT_ING.getDesc());
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

    public PageDto<VoiceTemplateAuditVO> queryAuditList(VoiceTemplateAuditQuery query, Integer pageNum, Integer pageSize) {
        PageDto<VoiceTemplateAuditVO> pageDto = null;
        VoiceTemplateDO templateDO = BaseBeanUtils.convert(query, VoiceTemplateDO.class);
        if (query.getVoiceType() != null) {
            templateDO.setTemplateType(query.getVoiceType().name());
        }
        if (StringUtils.isNotEmpty(query.getMerMobile())) {
            CustomerLoginInfo loginInfo = customerLoginInfoService.findByLoginMobile(query.getMerMobile());
            if (loginInfo != null) {
                templateDO.setContractNo(loginInfo.getContractNo());
            }
        }
        try {
            List<VoiceTemplateAuditVO> voList = new ArrayList<>();
            PageInfo<VoiceTemplateDO> pageInfo = this.queryListByPageAndOrder(templateDO, pageNum, pageSize, " created_at desc");
            pageDto = BaseBeanUtils.convert(pageInfo, PageDto.class);
            if (pageInfo != null && !pageInfo.getList().isEmpty()) {
                pageInfo.getList().stream().forEach(item -> {
                    VoiceTemplateAuditVO auditVO = BaseBeanUtils.convert(item, VoiceTemplateAuditVO.class);
                    CustomerLoginInfo customerInfo = customerLoginInfoService.selectByPrimaryKey(item.getContractNo());
                    auditVO.setMerMobile(customerInfo != null ? customerInfo.getMobile() : "");
                    if (AuditEnum.WAIT_AUDIT.getCode().equals(auditVO.getAuditStatus())) {
                        auditVO.setAuditStatus(AuditEnum.WAIT_AUDIT.getDesc());
                    }
                    if (AuditEnum.AUDIT_SUCCESS.getCode().equals(auditVO.getAuditStatus())) {
                        auditVO.setAuditStatus(AuditEnum.AUDIT_SUCCESS.getDesc());
                    }
                    if (AuditEnum.AUDIT_FAIL.getCode().equals(auditVO.getAuditStatus())) {
                        auditVO.setAuditStatus(AuditEnum.AUDIT_FAIL.getDesc());
                    }
                    if (AuditEnum.AUDIT_ING.getCode().equals(auditVO.getAuditStatus())) {
                        auditVO.setAuditStatus(AuditEnum.AUDIT_ING.getDesc());
                    }
                    // 创建人
                    if (StringUtils.isNotEmpty(item.getUpdatedBy())) {
                        CustomerLoginInfo creater = customerLoginInfoService.selectByPrimaryKey(item.getUpdatedBy());
                        auditVO.setAuditer("0".equals(creater.getIsManager()) ? "商户" : "管理员");
                    }
                    auditVO.setVoiceUrl(StringUtils.isNoneEmpty(auditVO.getVoiceUrl()) ? aconst.H5_URL_PATH + auditVO.getVoiceUrl() : auditVO.getVoiceUrl());
                    voList.add(auditVO);
                });
            }
            pageDto.setList(voList);
        } catch (Exception e) {
            log.error("语音模板审核列表分页查询错误:{}", e);
            e.printStackTrace();
        }
        return pageDto;
    }


    /**
     * 测试页展示用 ,返回固定顶多5条
     *
     * @return
     */
    public List<VoiceTemplateSelectDto> queryTemplateVoiceSelectList(VoiceTemplateSelectQueryDto queryDto) {
        try {
            VoiceTemplateDO voiceTemplateDO = BaseBeanUtils.convert(queryDto, VoiceTemplateDO.class);
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
        voiceTemplateDO.setUpdatedAt(new Date());
        voiceTemplateDO.setUpdatedBy(contractNo);
        int cnt = this.updateByIdSelective(voiceTemplateDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean alterTemplateVoice(VoiceTemplateAlterReqDto alterReqDto) {
        String templateID = alterReqDto.getTemplateId();
        VoiceTemplateDO voiceTemplateDO = this.queryById(templateID);
        if (voiceTemplateDO.getAuditStatus().equals(AuditEnum.AUDIT_FAIL.getCode())) {
            VoiceTemplateDO alterVoiceTemplateDO = BaseBeanUtils.convert(alterReqDto, VoiceTemplateDO.class);
            alterVoiceTemplateDO.setTemplateName(StringUtils.isEmpty(alterVoiceTemplateDO.getTemplateName()) ? null : alterVoiceTemplateDO.getTemplateName());
            alterVoiceTemplateDO.setTemplateType(StringUtils.isEmpty(alterVoiceTemplateDO.getTemplateType()) ? null : alterVoiceTemplateDO.getTemplateType());
            alterVoiceTemplateDO.setCategoryName(StringUtils.isEmpty(alterVoiceTemplateDO.getCategoryName()) ? null : alterVoiceTemplateDO.getCategoryName());
            alterVoiceTemplateDO.setVoiceContent(StringUtils.isEmpty(alterVoiceTemplateDO.getVoiceContent()) ? null : alterVoiceTemplateDO.getVoiceContent());
            alterVoiceTemplateDO.setVoiceUrl(StringUtils.isEmpty(alterVoiceTemplateDO.getVoiceUrl()) ? null : alterVoiceTemplateDO.getVoiceUrl());
            alterVoiceTemplateDO.setKeyWord(StringUtils.isEmpty(alterVoiceTemplateDO.getKeyWord()) ? null : alterVoiceTemplateDO.getKeyWord());
            alterVoiceTemplateDO.setTemplateId(templateID);
            alterVoiceTemplateDO.setAuditStatus(AuditEnum.WAIT_AUDIT.getCode());
            alterVoiceTemplateDO.setUpdatedAt(new Date());
            alterVoiceTemplateDO.setUpdatedBy(alterReqDto.getContractNo());
            log.info("修改前数据 alterReqDto:{}", alterReqDto);
            log.info("将入库修改的数据 alterVoiceTemplateDO:{}", alterVoiceTemplateDO);
            int cnt = this.updateByIdSelective(alterVoiceTemplateDO);
            return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
        } else {
            log.warn("审核成功的和待审的不能修改");
            throw new BaseBizException("400", "审核成功的和待审的不能修改");
        }
    }

    public boolean alterTemplateVoiceAdmin(VoiceTemplateAdminAlterReqDto alterReqDto) {
        String templateID = alterReqDto.getTemplateId();
        VoiceTemplateDO voiceTemplateDO = this.queryById(templateID);
        voiceTemplateDO.setOutChannelName(StringUtils.isNotEmpty(alterReqDto.getOutChannelName()) ? alterReqDto.getOutChannelName() : voiceTemplateDO.getOutChannelName());
        voiceTemplateDO.setOutTemplateId(StringUtils.isNotEmpty(alterReqDto.getOutTemplateId()) ? alterReqDto.getOutTemplateId() : voiceTemplateDO.getTemplateId());
        voiceTemplateDO.setAuditStatus(StringUtils.isNotEmpty(alterReqDto.getAuditStatus()) ? AuditEnum.getCode(alterReqDto.getAuditStatus()) : voiceTemplateDO.getAuditStatus());
        voiceTemplateDO.setRemark(alterReqDto.getRemark());
        voiceTemplateDO.setUpdatedBy(alterReqDto.getUpdatedBy());
        voiceTemplateDO.setUpdatedAt(new Date());
        log.info("将入库修改的数据 alterVoiceTemplateDO:{}", voiceTemplateDO);
        int cnt = this.updateByIdSelective(voiceTemplateDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }


    public boolean testTemplateVoice(VoiceTemplateOpenDto openDto) {
        VoiceTemplateDO voiceTemplateDO = this.queryById(openDto.getTemplateId());
        if (voiceTemplateDO == null || voiceTemplateDO.getOutTemplateId() == null || !AuditEnum.AUDIT_SUCCESS.getCode().equals(voiceTemplateDO.getAuditStatus())) {
            throw new BaseBizException("400", "不存在的模板或未审核通过的模板");
        }
        String templateId = voiceTemplateDO.getOutTemplateId();
        String called = openDto.getPhone();
        String calledDisplay = "";
        String data = openDto.getContractNo() +"|" + BaseStringUtils.uuid();
        int playTimes = 1;
        List<String> params = new ArrayList<>();
        params.add(openDto.getName());
        Info info = Info.builder().appID(appProperties.getJixinVoice().getAppId()).callID("call" + BaseStringUtils.uuid()).sessionID("session" + BaseStringUtils.uuid()).build();
        Subject subject = Subject.builder().templateID(templateId).called(called).calledDisplay(calledDisplay).params(params).playTimes(playTimes).build();
        JxVoiceVcodeReqDto jxVoiceVcodeReqDto = JxVoiceVcodeReqDto.builder()
                .data(data).timestamp(String.valueOf(System.currentTimeMillis())).build();
        jxVoiceVcodeReqDto.setInfo(info);
        jxVoiceVcodeReqDto.setSubject(subject);
        log.info(JSON.toJSONString(jxVoiceVcodeReqDto));
        try {
            VoiceApi.sendVoiceNotification(jxVoiceVcodeReqDto);
            CustomerTaskCallDetailDO customerTaskCallDetailDO = new CustomerTaskCallDetailDO();
            customerTaskCallDetailDO.setPhone(called);
            customerTaskCallDetailDO.setTaskId(data);
            customerTaskCallDetailDO.setNote("测试接口记录");
            customerTaskCallDetailDO.setCreatedAt(new Date());
            customerTaskCallDetailDO.setCallCnt(1);
            customerTaskCallDetailDO.setContractNo(openDto.getContractNo());
            customerTaskCallDetailDO.setTemplateId(templateId);
            customerTaskCallDetailDO.setCreatedBy(openDto.getContractNo());
            customerTaskCallDetailDO.setUserName(openDto.getName());
            customerTaskCallDetailDO.setOrgName(openDto.getOrgName());
            taskCallDetailDAO.insert(customerTaskCallDetailDO);
        } catch (Exception e) {
            log.error("发送异常:{}", e);
        }
        return Boolean.TRUE;
    }
}
