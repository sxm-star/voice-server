package com.mifa.cloud.voice.server.component.task;

import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.enums.BizTypeEnums;
import com.mifa.cloud.voice.server.commons.enums.FileTypeEnums;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.pojo.CustomerTaskContactGroupDO;
import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.service.ContactsService;
import com.mifa.cloud.voice.server.service.CustomerTaskContactGroupService;
import com.mifa.cloud.voice.server.service.UploadFileLogService;
import com.mifa.cloud.voice.server.utils.OperExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: songxm
 * @date: 2018/4/19 14:45
 * @version: v1.0.0
 */
@Component
@Slf4j
public class ContactTaskListener {

    @Autowired
    ContactsService contactTaskService;
    @Autowired
    CustomerTaskContactGroupService taskContactGroupService;
    @Autowired
    UploadFileLogService uploadFileLogService;
    @Autowired
    AsyncTaskExecutor asyncTaskExecutor;

    @RabbitListener(queues = "q_analysis_mobile_list")
    @RabbitHandler
    public void contactTask(String groupId) {
        log.info("接收到信息 :{}",groupId);
        if (StringUtils.isEmpty(groupId)) {
            log.warn("通讯录上传任务通知内容为空!");
            return;
        }
        CustomerTaskContactGroupDO taskContactGroupDO = taskContactGroupService.queryById(Long.parseLong(groupId));
        if(taskContactGroupDO==null){
            log.warn("不存在的通讯组 groupId:{}",groupId);
            return;
        }
        List<UploadFileLog> uploadFileLogs = uploadFileLogService.selectByFileTypeAndBizType(FileTypeEnums.EXCEL.getDesc(), BizTypeEnums.MOBILE_LIST.name(), StatusEnum.NORMAL.getCode().toString(),groupId);
        if (CollectionUtils.isNotEmpty(uploadFileLogs)){
            uploadFileLogs.forEach(uploadFileLog -> {
                asyncTaskExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        String filePath =   uploadFileLog.getFileRealPath();
                        List<Map<String,Object>> list = OperExcel.readExcel(filePath, AppConst.VOICE_TEMPLATE_METAS);
                        if (CollectionUtils.isEmpty(list)) {
                            return;
                        }
                        int batchCnt =  contactTaskService.addContancts(list,uploadFileLog.getCreateBy(), taskContactGroupDO.getTaskId(),uploadFileLog.getId());
                        Integer  cnt  = taskContactGroupDO.getGroupCnt()==null?0:taskContactGroupDO.getGroupCnt();
                        taskContactGroupDO.setGroupCnt(cnt+batchCnt);
                        taskContactGroupDO.setUpdatedAt(new Date());
                        taskContactGroupDO.setUpdatedBy(uploadFileLog.getCreateBy());
                        taskContactGroupService.updateByIdSelective(taskContactGroupDO);
                    }
                });
            });

        }
    }
}
