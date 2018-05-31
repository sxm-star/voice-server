package com.mifa.cloud.voice.server.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.api.jx.VoiceApi;
import com.mifa.cloud.voice.server.api.jx.dto.Info;
import com.mifa.cloud.voice.server.api.jx.dto.JxVoiceVcodeReqDto;
import com.mifa.cloud.voice.server.api.jx.dto.Subject;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.commons.enums.*;
import com.mifa.cloud.voice.server.component.properties.AppProperties;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.dao.CustomerTaskCallDetailDAO;
import com.mifa.cloud.voice.server.dao.TemplateVoiceDAO;
import com.mifa.cloud.voice.server.exception.BaseBizException;
import com.mifa.cloud.voice.server.pojo.*;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private AccountCapitalService accountCapitalService;
    @Autowired
    private CustomerExperienceService customerExperienceService;
    @Autowired
    private BlackListService blackListService;
    @Autowired
    private VoiceServiceBillRateService rateService;


    public Boolean insertTemplateVoice(TemplateVoiceDTO templateVoiceDTO) {
        VoiceTemplateDO voiceTemplateDO = BaseBeanUtils.convert(templateVoiceDTO, VoiceTemplateDO.class);
        voiceTemplateDO.setCreatedBy(templateVoiceDTO.getContractNo());
        voiceTemplateDO.setCreatedAt(new Date());
        voiceTemplateDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        voiceTemplateDO.setAuditStatus(AuditEnum.WAIT_AUDIT.getCode());
        voiceTemplateDO.setTemplateBiz(TemplateBizEnum.CHARGE.getCode());
        voiceTemplateDO.setTemplateType(templateVoiceDTO.getTemplateType().toString());
        int cnt = templateVoiceDAO.insert(voiceTemplateDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }


    public VoiceTemplateRspDTO queryTemplateVoice(@RequestParam(required = true) String contractNo, @RequestParam(required = true) String templateId) {
        VoiceTemplateDO voiceTemplateDO = new VoiceTemplateDO();
        voiceTemplateDO.setContractNo(contractNo);
        voiceTemplateDO.setTemplateId(templateId);
        voiceTemplateDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        VoiceTemplateDO resDo = this.queryOne(voiceTemplateDO);
        VoiceTemplateRspDTO rspDto = null;
        if (resDo != null) {
            rspDto = BaseBeanUtils.convert(resDo, VoiceTemplateRspDTO.class);
            rspDto.setAuditStatus(AuditEnum.getDesc(resDo.getAuditStatus()));
            rspDto.setTemplateTypeDesc(VoiceTypeEnum.getDesc(rspDto.getTemplateType()));
            rspDto.setTemplateType(VoiceTypeEnum.getDesc(resDo.getTemplateType()));
            rspDto.setVoiceUrl(aconst.H5_URL_PATH + rspDto.getVoiceUrl());

        }
        return rspDto;
    }

    public PageDTO<VoiceTemplateRspDTO> querySystemTemplateVoiceList(VoiceTemplateSysQuery query, Integer pageNum, Integer pageSize) {
        VoiceTemplateDO voiceTemplateDO = BaseBeanUtils.convert(query, VoiceTemplateDO.class);
        voiceTemplateDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        if (StringUtils.isNotEmpty(query.getMobile())){
            CustomerLoginInfo customerLoginInfo = customerLoginInfoService.findByLoginMobile(query.getMobile());
            if (null!=customerLoginInfo){
                voiceTemplateDO.setCreatedBy(customerLoginInfo.getContractNo());
            }
        }
        PageDTO<VoiceTemplateRspDTO> pageDTO = null;
        try {
            PageInfo<VoiceTemplateDO> pageInfo = this.queryListByPageAndOrder(voiceTemplateDO, pageNum, pageSize, " created_at desc");
            if (pageInfo != null && pageInfo.getList() != null) {
                pageDTO = BaseBeanUtils.convert(pageInfo, PageDTO.class);
                List<VoiceTemplateDO> doList = pageInfo.getList();
                List<VoiceTemplateRspDTO> rspList = new ArrayList<>();
                doList.forEach(voiceTemplateItem -> {
                    VoiceTemplateRspDTO voiceTemplateRspDTO = BaseBeanUtils.convert(voiceTemplateItem, VoiceTemplateRspDTO.class);
                    voiceTemplateRspDTO.setTemplateType(voiceTemplateRspDTO.getTemplateType().equals(VoiceTypeEnum.TEXT.name()) ? VoiceTypeEnum.TEXT.getDesc() : VoiceTypeEnum.VOICE.getDesc());
                    CustomerLoginInfo customerLoginInfo = customerLoginInfoService.selectByPrimaryKey(voiceTemplateItem.getContractNo());
                    if(null!=customerLoginInfo){
                        voiceTemplateRspDTO.setMobile(customerLoginInfo.getMobile());
                    }
                    if (AuditEnum.WAIT_AUDIT.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDTO.setAuditStatus(AuditEnum.WAIT_AUDIT.getDesc());
                    }
                    if (AuditEnum.AUDIT_SUCCESS.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDTO.setAuditStatus(AuditEnum.AUDIT_SUCCESS.getDesc());
                    }
                    if (AuditEnum.AUDIT_FAIL.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDTO.setAuditStatus(AuditEnum.AUDIT_FAIL.getDesc());
                    }
                    if (AuditEnum.AUDIT_ING.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDTO.setAuditStatus(AuditEnum.AUDIT_ING.getDesc());
                    }
                    voiceTemplateRspDTO.setVoiceUrl(aconst.H5_URL_PATH + voiceTemplateRspDTO.getVoiceUrl());
                    rspList.add(voiceTemplateRspDTO);
                });
                pageDTO.setList(rspList);
            }
            return pageDTO;
        } catch (Exception e) {
            log.error("语音模板分页查询错误:{}", e);
            return pageDTO;
        }
    }

    public PageDTO<VoiceTemplateRspDTO> queryTemplateVoiceList(VoiceTemplateQuery query, Integer pageNum, Integer pageSize) {
        VoiceTemplateDO voiceTemplateDO = BaseBeanUtils.convert(query, VoiceTemplateDO.class);
        voiceTemplateDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        PageDTO<VoiceTemplateRspDTO> pageDTO = null;
        try {
            PageInfo<VoiceTemplateDO> pageInfo = this.queryListByPageAndOrder(voiceTemplateDO, pageNum, pageSize, " created_at desc");
            if (pageInfo != null && pageInfo.getList() != null) {
                pageDTO = BaseBeanUtils.convert(pageInfo, PageDTO.class);
                List<VoiceTemplateDO> doList = pageInfo.getList();
                List<VoiceTemplateRspDTO> rspList = new ArrayList<>();
                doList.forEach(voiceTemplateItem -> {
                    VoiceTemplateRspDTO voiceTemplateRspDTO = BaseBeanUtils.convert(voiceTemplateItem, VoiceTemplateRspDTO.class);
                    voiceTemplateRspDTO.setTemplateType(voiceTemplateRspDTO.getTemplateType().equals(VoiceTypeEnum.TEXT.name()) ? VoiceTypeEnum.TEXT.getDesc() : VoiceTypeEnum.VOICE.getDesc());
                    ;
                    if (AuditEnum.WAIT_AUDIT.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDTO.setAuditStatus(AuditEnum.WAIT_AUDIT.getDesc());
                    }
                    if (AuditEnum.AUDIT_SUCCESS.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDTO.setAuditStatus(AuditEnum.AUDIT_SUCCESS.getDesc());
                    }
                    if (AuditEnum.AUDIT_FAIL.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDTO.setAuditStatus(AuditEnum.AUDIT_FAIL.getDesc());
                    }
                    if (AuditEnum.AUDIT_ING.getCode().equals(voiceTemplateItem.getAuditStatus())) {
                        voiceTemplateRspDTO.setAuditStatus(AuditEnum.AUDIT_ING.getDesc());
                    }
                    rspList.add(voiceTemplateRspDTO);
                });
                pageDTO.setList(rspList);
            }
            return pageDTO;
        } catch (Exception e) {
            log.error("语音模板分页查询错误:{}", e);
            return pageDTO;
        }
    }


    public PageDTO<VoiceTemplateAuditVO> queryAuditList(VoiceTemplateAuditQuery query, Integer pageNum, Integer pageSize) {
        PageDTO<VoiceTemplateAuditVO> pageDTO = null;
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
            pageDTO = BaseBeanUtils.convert(pageInfo, PageDTO.class);
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
            pageDTO.setList(voList);
        } catch (Exception e) {
            log.error("语音模板审核列表分页查询错误:{}", e);
            e.printStackTrace();
        }
        return pageDTO;
    }


    /**
     * 测试页展示用 ,返回固定顶多5条
     *
     * @return
     */
    public List<VoiceTemplateSelectDTO> queryTemplateVoiceSelectList(VoiceTemplateSelectQueryDTO queryDto) {
        try {
            VoiceTemplateDO voiceTemplateDO = BaseBeanUtils.convert(queryDto, VoiceTemplateDO.class);
            voiceTemplateDO.setAuditStatus(AuditEnum.AUDIT_SUCCESS.getCode());
            voiceTemplateDO.setStatus(StatusEnum.NORMAL.getCode().toString());
            PageInfo<VoiceTemplateDO> pageInfo = this.queryListByPageAndOrder(voiceTemplateDO, 1, 5, null);
            if (pageInfo != null && pageInfo.getList() != null) {
                List<VoiceTemplateSelectDTO> resList = new ArrayList<>();
                pageInfo.getList().forEach(item -> {
                    resList.add(BaseBeanUtils.convert(item, VoiceTemplateSelectDTO.class));
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

    public boolean alterTemplateVoice(VoiceTemplateAlterReqDTO alterReqDto) {
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

    public boolean alterTemplateVoiceAdmin(VoiceTemplateAdminAlterReqDTO alterReqDto) {
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

    public boolean testTemplateVoice(VoiceTemplateOpenDTO openDto) {
        AccountCapitalDO accountCapitalDO = accountCapitalService.queryOne(AccountCapitalDO.builder().contractNo(openDto.getContractNo()).build());
        if (accountCapitalDO == null || accountCapitalDO.getAvailableAmount()<1*100) {
            throw new BaseBizException("400", "资金账户余额不足,请先完成充值");
        }
        VoiceTemplateDO voiceTemplateDO = this.queryById(openDto.getTemplateId());
        if (voiceTemplateDO == null || voiceTemplateDO.getOutTemplateId() == null || !AuditEnum.AUDIT_SUCCESS.getCode().equals(voiceTemplateDO.getAuditStatus())) {
            throw new BaseBizException("400", "不存在的模板或未审核通过的模板");
        }

        BlackListDO blackListDO = blackListService.queryOne(BlackListDO.builder().tag(BlackListTagEnum.PHONE.name()).contractNo(openDto.getContractNo()).type(BlackListTypeEnum.BLACK.name()).value(openDto.getPhone()).status(String.valueOf(StatusEnum.NORMAL.getCode())).build());
        if (null!=blackListDO){
            throw new BaseBizException("400", "拨打的手机号号在黑名单里");
        }

        VoiceServiceBillRateDO voiceServiceBillRateDO =  rateService.queryOne(VoiceServiceBillRateDO.builder().contractNo(openDto.getContractNo()).build());
        if (null==voiceServiceBillRateDO){
            throw new BaseBizException("400","租户的费率信息没有配置");
        }

        String templateId = voiceTemplateDO.getOutTemplateId();
        String called = openDto.getPhone();
        String calledDisplay = "";

        String taskId = BaseStringUtils.uuid();
        String batchId = BaseStringUtils.uuid();
        String data = openDto.getContractNo() + "|" + taskId + "|" + batchId + "|" + called + "|" + TemplateBizEnum.CHARGE.getCode();
        int playTimes = 1;
        List<String> params = new ArrayList<>();
        Info info = Info.builder().appID(appProperties.getJixinVoice().getAppId()).callID("call" + BaseStringUtils.uuid()).sessionID("session" + BaseStringUtils.uuid()).build();
        Subject subject = Subject.builder().templateID(templateId).called(called).calledDisplay(calledDisplay).params(params).playTimes(playTimes).build();
        JxVoiceVcodeReqDto jxVoiceVcodeReqDto = JxVoiceVcodeReqDto.builder()
                .data(data).timestamp(String.valueOf(System.currentTimeMillis())).build();
        jxVoiceVcodeReqDto.setInfo(info);
        jxVoiceVcodeReqDto.setSubject(subject);
        log.info(JSON.toJSONString(jxVoiceVcodeReqDto));
        try {
            VoiceApi.sendVoiceNotification(jxVoiceVcodeReqDto, appProperties.getJixinVoice().getAccountId(), appProperties.getJixinVoice().getAuthToken());
            CustomerTaskCallDetailDO customerTaskCallDetailDO = new CustomerTaskCallDetailDO();
            customerTaskCallDetailDO.setPhone(called);
            customerTaskCallDetailDO.setTaskId(taskId);
            customerTaskCallDetailDO.setBatchId(batchId);
            customerTaskCallDetailDO.setNote("在线发送");
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
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public VoiceExperienceDTO templateVoiceFree(VoiceTemplateOpenDTO openDTO) {
        CustomerExperienceDO customerExperienceDO = customerExperienceService.queryOne(CustomerExperienceDO.builder().templateId(openDTO.getTemplateId()).contractNo(openDTO.getContractNo()).build());

        if (customerExperienceDO.getExperienceTotalCnt().equals(customerExperienceDO.getCurrentCnt())){
            throw new BaseBizException("400","您的语音体验次数已经用完");
        }
        boolean flag =  this.testTemplateVoice(openDTO);
        if (flag){
            customerExperienceDO.setCurrentCnt(customerExperienceDO.getCurrentCnt() + 1);
            customerExperienceService.updateByIdSelective(customerExperienceDO);
        }
        return VoiceExperienceDTO.builder().experienceTotalCnt(customerExperienceDO.getExperienceTotalCnt()).currentCnt(customerExperienceDO.getCurrentCnt()).leftCnt(customerExperienceDO.getExperienceTotalCnt()-customerExperienceDO.getCurrentCnt()).build();
    }
}
