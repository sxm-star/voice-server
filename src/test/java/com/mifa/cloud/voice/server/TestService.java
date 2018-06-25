package com.mifa.cloud.voice.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.OpenApiConfigDTO;
import com.mifa.cloud.voice.server.commons.dto.OpenApiConfigRspDTO;
import com.mifa.cloud.voice.server.commons.dto.OpenApiVoiceReqDTO;
import com.mifa.cloud.voice.server.commons.enums.*;
import com.mifa.cloud.voice.server.component.RandomSort;
import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.pojo.*;
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
import org.springframework.context.ApplicationEventPublisher;
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
    @Autowired
    AccountDetailService accountDetailService;
    @Autowired
    CallJobService callJobService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Test
    public void testRedis() {
        keyValueDao.set("123456","123456");
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
        OpenApiConfigDTO openApiConfigDTO = new OpenApiConfigDTO();
        openApiConfigDTO.setContractNo("123456");
        openApiConfigDTO.setCreatedBy("admin");
        openApiConfigDTO.setTimeUnit(TimeUnitEnum.MONTH);
        openApiConfigDTO.setExpireValue(1);
        openapiConfigService.addOpenapiConfig(openApiConfigDTO);
    }


    @Test
    public void testGetOpenApiService(){
        OpenApiConfigRspDTO openApiConfigRspDTO = openapiConfigService.getOpenApiConfig("123456");
        System.out.println(openApiConfigRspDTO);
    }

    @Test
    public void testParam(){
        OpenApiVoiceReqDTO openApiVoiceReqDTO = new OpenApiVoiceReqDTO();
        openApiVoiceReqDTO.setMobile("13251022729");
        openApiVoiceReqDTO.setTemplateId("6d9c4f7ae25f4782823b7aefc5968962");
        Map<String,Object> map = new HashMap<>();
        map.put("${name}","风清扬");
        openApiVoiceReqDTO.setParamsValue(map);
        System.out.println(JSON.toJSONString(openApiVoiceReqDTO));

    }

    @Test
    public void testNotify() {
       //voiceNotifyLogService.save(VoiceNotifyLogDO.builder().callTime(new Date()).callResponse("test").called("18720987088").data("123456").build());
    }
    @Test
    public void testServiceBillRate(){
        rateService.save(VoiceServiceBillRateDO.builder().channel(ChannelEnum.JIXIN.getName()).calfeeType(CalFeeTypeEnum.CHARGE_BY_TIME.getCode()).contractNo("123456").productName(ProductEnum.VOICE_NOTIFY.name()).rateAmt(10).build());
    }
    @Test
    public void testAccount(){
        accountCapitalService.save(AccountCapitalDO.builder().contractNo("123456").accountId(SeqProducerUtil.getAccountNo()).availableAmount(0l).freezeAccount(0l).totalAmount(0l).currPeriodBal(0l).lastPeriodBal(0l).build());
    }

    @Test
    public void testAccountCaptial(){

        System.out.println( accountDetailService.queryTotalByDataType(1));

        System.out.println(accountDetailService.queryTotalRecharge(AccountTransTypeEnum.RECHARGE.getDesc()));
    }
    @Test
    public void testJob() throws Exception{
      List<CallJobDO>  callJobDOs = callJobService.queryListAndCreateAtGreaterThan(CallJobDO.builder().contractNo("8001489512062976").createdAt(new Date()).status(String.valueOf(JobStatusEnum.WAIT_START.getCode())).build());
        System.out.println(callJobDOs);
    }
}
