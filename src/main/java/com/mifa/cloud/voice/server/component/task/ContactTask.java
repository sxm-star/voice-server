package com.mifa.cloud.voice.server.component.task;

import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.enums.BizTypeEnums;
import com.mifa.cloud.voice.server.commons.enums.FileTypeEnums;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.service.ContactsService;
import com.mifa.cloud.voice.server.service.UploadFileLogService;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import com.mifa.cloud.voice.server.utils.OperExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author: songxm
 * @date: 2018/4/18 15:41
 * @version: v1.0.0
 */
@Slf4j
@Component
public class ContactTask {

    @Autowired
    ContactsService contactTaskService;
    @Autowired
    UploadFileLogService uploadFileLogService;
    @Autowired
    AsyncTaskExecutor asyncTaskExecutor;

    @Scheduled(initialDelay = 1000, fixedDelay = 1*60*1000)
    public void parseContact(){
        log.info("后台解析通讯录模板开始");
        List<UploadFileLog> uploadFileLogs = uploadFileLogService.selectByFileTypeAndBizType(FileTypeEnums.EXCEL.getDesc(), BizTypeEnums.MOBILE_LIST.name(), StatusEnum.NORMAL.getCode().toString());
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
                        contactTaskService.addContancts(list,uploadFileLog.getCreateBy(), BaseStringUtils.uuid(),uploadFileLog.getId());
                    }
                });
            });

        }
    }
}
