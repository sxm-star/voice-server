package com.mifa.cloud.voice.server.component.task;

import com.mifa.cloud.voice.server.api.jx.VoiceApi;
import com.mifa.cloud.voice.server.api.jx.dto.Info;
import com.mifa.cloud.voice.server.api.jx.dto.JxVoiceVcodeReqDto;
import com.mifa.cloud.voice.server.api.jx.dto.Subject;
import com.mifa.cloud.voice.server.commons.enums.AuditEnum;
import com.mifa.cloud.voice.server.commons.enums.JobStatusEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.component.properties.AppProperties;
import com.mifa.cloud.voice.server.dao.CustomerTaskUserContactsDAO;
import com.mifa.cloud.voice.server.exception.BaseBizException;
import com.mifa.cloud.voice.server.pojo.*;
import com.mifa.cloud.voice.server.service.*;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import com.mifa.cloud.voice.server.utils.EncodesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/4/19 14:45
 * @version: v1.0.0
 */
@Component
@Slf4j
public class JobTaskListener {

    @Autowired
    ContactsService contactTaskService;
    @Autowired
    CustomerTaskUserContactsDAO contactsDAO;
    @Autowired
    CustomerTaskContactGroupService taskContactGroupService;
    @Autowired
    UploadFileLogService uploadFileLogService;
    @Autowired
    AsyncTaskExecutor asyncTaskExecutor;
    @Autowired
    CallJobService jobService;
    @Autowired
    CustomerTaskCallDetailService taskCallDetailService;
    @Autowired
    TemplateVoiceService templateVoiceService;
    @Autowired
    AppProperties appProperties;

    @RabbitListener(queues = "q_voice_job_pool")
    @RabbitHandler
    public void contactTask(Integer jobId) {
        log.info("接收到任务ID :{}",jobId);
        if (null==jobId) {
            log.warn("拨打任务ID为空!");
            return;
        }
        CallJobDO callJobDO = jobService.queryById(jobId);
        if (callJobDO!=null && callJobDO.getJobStatus().equals(JobStatusEnum.RUNNING.getCode())){

            String taskId = callJobDO.getTaskId();
            CustomerTaskUserContactsDOExample example = new CustomerTaskUserContactsDOExample();
            example.createCriteria().andTaskIdEqualTo(taskId).andStatusEqualTo(StatusEnum.NORMAL.getCode()+"");
            List<CustomerTaskUserContactsDO> list = contactsDAO.selectByExample(example);
            if(list!=null && list.size()>0){
                for (CustomerTaskUserContactsDO item: list){
                    VoiceTemplateDO voiceTemplateDO = templateVoiceService.queryById(callJobDO.getTemplateId());
                    if (voiceTemplateDO == null || voiceTemplateDO.getOutTemplateId() == null || !AuditEnum.AUDIT_SUCCESS.getCode().equals(voiceTemplateDO.getAuditStatus())) {
                        throw new BaseBizException("400", "不存在的模板或未审核通过的模板");
                    }
                    String templateId = voiceTemplateDO.getOutTemplateId();
                    log.info("拨打计划----- 三方ID号 :{}",templateId);
                    String called = EncodesUtils.selfDecrypt(item.getUserPhone(),item.getSalt());
                    String calledDisplay = "";
                    int playTimes = 1;
                    List<String> params = new ArrayList<>();
                    Info info =  Info.builder().appID(appProperties.getJixinVoice().getAppId()).callID("call"+ BaseStringUtils.uuid()).sessionID("session"+BaseStringUtils.uuid()).build();
                    Subject subject =  Subject.builder().templateID(templateId).called(called).calledDisplay(calledDisplay).params(params).playTimes(playTimes).build();
                    JxVoiceVcodeReqDto jxVoiceVcodeReqDto = JxVoiceVcodeReqDto.builder()
                            .data(item.getContractNo()+"|"+taskId+"|"+callJobDO.getBatchId()+"|"+called).timestamp(String.valueOf(System.currentTimeMillis())).build();
                    jxVoiceVcodeReqDto.setInfo(info);
                    jxVoiceVcodeReqDto.setSubject(subject);
                    try {
                        VoiceApi.sendVoiceNotification(jxVoiceVcodeReqDto,appProperties.getJixinVoice().getAccountId(),appProperties.getJixinVoice().getAuthToken());
                        CustomerTaskCallDetailDO detailDO = CustomerTaskCallDetailDO.builder().orgName(item.getOrgName()).templateId(templateId).contractNo(item.getContractNo()).userName(item.getUserName()).callCnt(1).phone(called).taskId(taskId).batchId(callJobDO.getBatchId()).build();
                        detailDO.setCreatedBy(item.getContractNo());
                        detailDO.setCreatedAt(new Date());
                        taskCallDetailService.save(detailDO);
                        log.info("保存拨打电话入库:{}",detailDO);
                    }catch (Exception e){
                        log.error("拨打异常:{}",e);
                    }

                }

                callJobDO.setJobStatus(JobStatusEnum.STOP.getCode());
                callJobDO.setUpdatedAt(new Date());
                callJobDO.setUpdatedBy(callJobDO.getCreatedBy());
                jobService.updateByIdSelective(callJobDO);

            }


        }

    }
}
