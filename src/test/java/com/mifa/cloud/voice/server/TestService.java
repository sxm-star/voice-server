package com.mifa.cloud.voice.server;

import com.alibaba.fastjson.JSONObject;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.enums.BizTypeEnums;
import com.mifa.cloud.voice.server.commons.enums.FileTypeEnums;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.component.RandomSort;
import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.pojo.CustomerTaskContactGroupDO;
import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.service.ContactsService;
import com.mifa.cloud.voice.server.service.CustomerTaskContactGroupService;
import com.mifa.cloud.voice.server.service.UploadFileLogService;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import com.mifa.cloud.voice.server.utils.OperExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
@ActiveProfiles("dev")
public class TestService {

    @Autowired
    KeyValueDao keyValueDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConstConfig constConfig;
    @Autowired
    private ContactsService contactsService;

    @Autowired
    ContactsService contactTaskService;
    @Autowired
    CustomerTaskContactGroupService taskContactGroupService;
    @Autowired
    UploadFileLogService uploadFileLogService;
    @Autowired
    AsyncTaskExecutor asyncTaskExecutor;

    @Test
    public void testRedis() {
        System.out.println(keyValueDao.get("MOBILE_SMS_KEY_18225695244"));
    }

    @Test
    public void testSendMsg() {
        String code = String.valueOf(RandomSort.generateRandomNum(6));
        keyValueDao.set("MOBILE_SMS_KEY_" + "18225695244", code, 60 * 3);
        Map<String, String> map = new HashMap<String, String>();
        map.put("bizType", "1");
        map.put("aliasName", "mifayuyinyun");
        map.put("tel", "18225695244");
        map.put("identifyingCode", code);
        String json = JSONObject.toJSONString(map);
        log.info("send msg：" + json);
        rabbitTemplate.convertAndSend("q_sms", json);
    }

    @Test
    public void testConst() {
        System.out.println(constConfig.H5_URL_PATH);
    }

    @Test
    public void test(){
        String groupId = "11";
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
                        boolean flag =  contactTaskService.addContancts(list,uploadFileLog.getCreateBy(), BaseStringUtils.uuid(),uploadFileLog.getId());
                        Integer  cnt  = taskContactGroupDO.getGroupCnt()==null?0:taskContactGroupDO.getGroupCnt();
                        taskContactGroupDO.setGroupCnt(cnt+list.size());
                        taskContactGroupDO.setUpdatedAt(new Date());
                        taskContactGroupDO.setUpdatedBy("system");
                        taskContactGroupService.updateByIdSelective(taskContactGroupDO);
                    }
                });
            });

        }
    }
























}
