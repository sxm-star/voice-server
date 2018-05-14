package com.mifa.cloud.voice.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.OpenApiConfigDto;
import com.mifa.cloud.voice.server.commons.dto.OpenApiConfigRspDto;
import com.mifa.cloud.voice.server.commons.dto.OpenApiVoiceReqDto;
import com.mifa.cloud.voice.server.commons.enums.*;
import com.mifa.cloud.voice.server.component.RandomSort;
import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.pojo.AccountCapitalDO;
import com.mifa.cloud.voice.server.pojo.CustomerTaskContactGroupDO;
import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.pojo.VoiceServiceBillRateDO;
import com.mifa.cloud.voice.server.service.*;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import com.mifa.cloud.voice.server.utils.OperExcel;
import com.mifa.cloud.voice.server.utils.SeqProducerUtil;
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
    @Autowired
    OpenapiConfigService openapiConfigService;

    @Autowired
    VoiceNotifyLogService voiceNotifyLogService;
    @Autowired
    VoiceServiceBillRateService rateService;
    @Autowired
    AccountCapitalService accountCapitalService;

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
                        Integer batchCnt =  contactTaskService.addContancts(list,uploadFileLog.getCreateBy(), BaseStringUtils.uuid(),uploadFileLog.getId());
                        Integer  cnt  = taskContactGroupDO.getGroupCnt()==null?0:taskContactGroupDO.getGroupCnt();
                        taskContactGroupDO.setGroupCnt(cnt+batchCnt);
                        taskContactGroupDO.setUpdatedAt(new Date());
                        taskContactGroupDO.setUpdatedBy("system");
                        taskContactGroupService.updateByIdSelective(taskContactGroupDO);
                    }
                });
            });

        }
    }

    @Test
    public void testOpenApiService(){
        OpenApiConfigDto openApiConfigDto = new OpenApiConfigDto();
        openApiConfigDto.setContractNo("123456");
        openApiConfigDto.setCreatedBy("admin");
        openApiConfigDto.setTimeUnit(TimeUnitEnum.MONTH);
        openApiConfigDto.setExpireValue(1);
        openapiConfigService.addOpenapiConfig(openApiConfigDto);
    }


    @Test
    public void testGetOpenApiService(){
        OpenApiConfigRspDto openApiConfigRspDto = openapiConfigService.getOpenApiConfig("123456");
        System.out.println(openApiConfigRspDto);
    }

    @Test
    public void testParam(){
        OpenApiVoiceReqDto openApiVoiceReqDto = new OpenApiVoiceReqDto();
        openApiVoiceReqDto.setMobile("13251022729");
        openApiVoiceReqDto.setTemplateId("6d9c4f7ae25f4782823b7aefc5968962");
        Map<String,Object> map = new HashMap<>();
        map.put("${name}","风清扬");
        openApiVoiceReqDto.setParamsValue(map);
        System.out.println(JSON.toJSONString(openApiVoiceReqDto));

    }

    @Test
    public void testNotify() {
       // voiceNotifyLogService.save(VoiceNotifyLogDO.builder().callTime(new Date()).callResponse("test").called("18720987088").data("123456").build());
    }
    @Test
    public void testServiceBillRate(){
        rateService.save(VoiceServiceBillRateDO.builder().channel(ChannelEnum.JIXIN.getName()).calfeeType(CalFeeTypeEnum.CHARGE_BY_TIME.getCode()).contractNo("123456").productName(ProductEnum.VOICE_NOTIFY.name()).rateAmt(10).build());
    }
    @Test
    public void testAccount(){
        accountCapitalService.save(AccountCapitalDO.builder().contractNo("123456").accountId(SeqProducerUtil.getAccountNo()).availableAmount(0l).freezeAccount(0l).totalAmount(0l).currPeriodBal(0l).lastPeriodBal(0l).build());
    }
}
