package com.mifa.cloud.voice.server.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.CallJobDTO;
import com.mifa.cloud.voice.server.commons.dto.CustomerCallJobDto;
import com.mifa.cloud.voice.server.commons.dto.PageDTO;
import com.mifa.cloud.voice.server.commons.enums.AuditEnum;
import com.mifa.cloud.voice.server.commons.enums.JobStatusEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.dao.CallJobDAO;
import com.mifa.cloud.voice.server.dao.CustomerTaskContactGroupDAO;
import com.mifa.cloud.voice.server.dao.TemplateVoiceDAO;
import com.mifa.cloud.voice.server.exception.BaseBizException;
import com.mifa.cloud.voice.server.pojo.*;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/4/16 19:20
 * @version: v1.0.0
 */
@Service
@Slf4j
public class CallJobService extends BaseService<CallJobDO> {
    @Autowired
    CallJobDAO customerCallJobDAO;

    @Autowired
    CustomerTaskContactGroupDAO groupDAO;

    @Autowired
    TemplateVoiceDAO templateVoiceDAO;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AccountCapitalService accountCapitalService;
    @Autowired
    private CustomerLoginInfoService customerLoginInfoService;
    @Autowired
    VoiceServiceBillRateService rateService;

    @Transactional(rollbackFor = Exception.class)
    public Boolean addCallJob(CustomerCallJobDto customerCallJobDto) {


        AccountCapitalDO accountCapitalDO =  accountCapitalService.queryOne(AccountCapitalDO.builder().contractNo(customerCallJobDto.getContractNo()).build());
        if (accountCapitalDO==null || accountCapitalDO.getAvailableAmount()<=1*100){
            throw new BaseBizException("400","资金账户余额不足,请先完成充值");
        }
        VoiceTemplateDO voiceTemplateDO = templateVoiceDAO.selectByPrimaryKey(customerCallJobDto.getTemplateId());
        if(voiceTemplateDO==null || !AuditEnum.AUDIT_SUCCESS.getCode().equals(voiceTemplateDO.getAuditStatus())){
            throw new BaseBizException("401","当前模板没有审核通过,不允许被添加至任务列表");
        }

        VoiceServiceBillRateDO voiceServiceBillRateDO =  rateService.queryOne(VoiceServiceBillRateDO.builder().contractNo(customerCallJobDto.getContractNo()).build());
        if (null==voiceServiceBillRateDO){
            throw new BaseBizException("400","租户的费率信息没有配置");
        }

        CallJobDO customerCallJobDO = BaseBeanUtils.convert(customerCallJobDto, CallJobDO.class);
        customerCallJobDO.setBatchId(BaseStringUtils.uuid());
        customerCallJobDO.setStatus(StatusEnum.NORMAL.getCode().toString());
       // customerCallJobDO.setJobStatus(JobStatusEnum.WAIT_START.getCode());
        customerCallJobDO.setJobStatus(JobStatusEnum.RUNNING.getCode());  //默认设置好启动状态
        customerCallJobDO.setCreatedBy(customerCallJobDto.getContractNo());
        CustomerTaskContactGroupDO taskContactGroupDO  = new CustomerTaskContactGroupDO();
        taskContactGroupDO.setTaskId(customerCallJobDto.getTaskId());
        taskContactGroupDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        CustomerTaskContactGroupDO temCustomerTaskContactGroupDO =  groupDAO.selectOne(taskContactGroupDO);
        if (temCustomerTaskContactGroupDO!=null){
            customerCallJobDO.setGroupCnt(temCustomerTaskContactGroupDO.getGroupCnt());
            customerCallJobDO.setSource(temCustomerTaskContactGroupDO.getSource());
            customerCallJobDO.setGroupName(temCustomerTaskContactGroupDO.getGroupName());

        }
        try {
            int cnt = customerCallJobDAO.insert(customerCallJobDO);
            log.info("发送mq----->  拨打任务的ID:{}", customerCallJobDO.getId());
            rabbitTemplate.convertAndSend("q_voice_job_pool", customerCallJobDO.getId());
            return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e) {
            log.error("异常信息:{}",e);
            return Boolean.FALSE;
        }

    }

    public CallJobDTO queryCallJob(String contractNo, Integer id) {
        CallJobDO callJobDO = new CallJobDO();
        callJobDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        callJobDO.setContractNo(contractNo);
        callJobDO.setId(id);
        CallJobDO newCallJobDO = this.queryOne(callJobDO);
        CallJobDTO callJobDTO = null;
        if (newCallJobDO != null) {
            callJobDTO = BaseBeanUtils.convert(newCallJobDO, CallJobDTO.class);
            callJobDTO.setJobStatus(JobStatusEnum.getDesc(newCallJobDO.getJobStatus()));

        }
        return callJobDTO;
    }

    public CallJobDTO queryCallJobByID(Integer id) {
        CallJobDO callJobDO = new CallJobDO();
        callJobDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        callJobDO.setId(id);
        CallJobDO newCallJobDO = this.queryOne(callJobDO);
        CallJobDTO callJobDTO = null;
        if (newCallJobDO != null) {
            callJobDTO = BaseBeanUtils.convert(newCallJobDO, CallJobDTO.class);
            callJobDTO.setJobStatus(JobStatusEnum.getDesc(newCallJobDO.getJobStatus()));

        }
        return callJobDTO;
    }

    public Boolean delCallJob(String contracNo, Integer id) {
        CallJobDO callJobDO = new CallJobDO();
        callJobDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        callJobDO.setContractNo(contracNo);
        callJobDO.setId(id);
        CallJobDO delcCallJobDO = this.queryOne(callJobDO);
        if (null == delcCallJobDO) {
            throw new BaseBizException("400", "不存在的拨打计划");
        } else {
            if (delcCallJobDO.getJobStatus().equals(JobStatusEnum.WAIT_START.getCode()) || delcCallJobDO.getJobStatus().equals(JobStatusEnum.STOP.getCode())) {
                delcCallJobDO.setStatus(StatusEnum.BLOCK.getCode().toString());
                delcCallJobDO.setUpdatedAt(new Date());
                delcCallJobDO.setUpdatedBy(contracNo);
                int cnt = this.updateByIdSelective(delcCallJobDO);
                return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
            } else {
                throw new BaseBizException("501", JobStatusEnum.getDesc(delcCallJobDO.getJobStatus()) + "的计划不允许被删");
            }
        }
    }

    public PageDTO<CallJobDTO> queryCallJobList(String contractNo, String jobName, Integer pageNum, Integer pageize) {
        PageDTO<CallJobDTO> pageDTO = null;
        try {

            Example example = new Example(CallJobDO.class);
            // 声明条件
            Example.Criteria createCriteria = example.createCriteria();
            if (StringUtils.isNotEmpty(jobName)) {
                createCriteria.andLike("jobName", "%" + jobName + "%");
            }
            createCriteria.andEqualTo("contractNo", contractNo);
            createCriteria.andEqualTo("status",StatusEnum.NORMAL.getCode().toString());
            example.setOrderByClause("created_at DESC");
            PageHelper.startPage(pageNum, pageize);
            List<CallJobDO> listDOs = customerCallJobDAO.selectByExample(example);
            if (listDOs != null && listDOs.size() > 0) {
                PageInfo<CallJobDO> pageInfo = new PageInfo<CallJobDO>(listDOs);
                List<CallJobDTO> rspList = new ArrayList<>();

                listDOs.forEach(item -> {
                    CallJobDTO callJobDTO = BaseBeanUtils.convert(item, CallJobDTO.class);
                    callJobDTO.setJobStatus(JobStatusEnum.getDesc(item.getJobStatus()));
                    rspList.add(callJobDTO);
                });
                pageDTO = BaseBeanUtils.convert(pageInfo, PageDTO.class);
                pageDTO.setList(rspList);
            }
            return pageDTO;
        } catch (Exception e) {
            log.error("拨打计划分页查询错误:{}", e);
            return pageDTO;
        }
    }

    public PageDTO<CallJobDTO> querySystemCallJobList(String mobile, String jobName, Integer pageNum, Integer pageize) {
        PageDTO<CallJobDTO> pageDTO = null;
        try {

            Example example = new Example(CallJobDO.class);
            // 声明条件
            Example.Criteria createCriteria = example.createCriteria();
            if (StringUtils.isNotEmpty(jobName)) {
                createCriteria.andLike("jobName", "%" + jobName + "%");
            }
            if (StringUtils.isNotEmpty(mobile)) {
                CustomerLoginInfo customerLoginInfo = customerLoginInfoService.findByLoginMobile(mobile);
                if (null != customerLoginInfo) {
                    createCriteria.andEqualTo("contractNo", customerLoginInfo.getContractNo());
                }
            }
            createCriteria.andEqualTo("status",StatusEnum.NORMAL.getCode().toString());
            PageHelper.startPage(pageNum, pageize);
            List<CallJobDO> listDOs = customerCallJobDAO.selectByExample(example);
            if (listDOs != null && listDOs.size() > 0) {
                PageInfo<CallJobDO> pageInfo = new PageInfo<CallJobDO>(listDOs);
                List<CallJobDTO> rspList = new ArrayList<>();

                listDOs.forEach(item -> {
                    CallJobDTO callJobDTO = BaseBeanUtils.convert(item, CallJobDTO.class);
                    callJobDTO.setJobStatus(JobStatusEnum.getDesc(item.getJobStatus()));
                    CustomerLoginInfo customerLoginInfo =  customerLoginInfoService.selectByPrimaryKey(callJobDTO.getContractNo());
                    if(null!=customerLoginInfo){
                        callJobDTO.setMobile(customerLoginInfo.getMobile());
                    }
                    rspList.add(callJobDTO);
                });
                pageDTO = BaseBeanUtils.convert(pageInfo, PageDTO.class);
                pageDTO.setList(rspList);
            }
            return pageDTO;
        } catch (Exception e) {
            log.error("拨打计划分页查询错误:{}", e);
            return pageDTO;
        }
    }
}
