package com.mifa.cloud.voice.server;

import com.alibaba.fastjson.JSONObject;
import com.mifa.cloud.voice.server.component.RandomSort;
import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.service.ContactsService;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import com.mifa.cloud.voice.server.utils.EncodesUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.mifa.cloud.voice.server.utils.EncodesUtils.encodeBase64;
import static com.mifa.cloud.voice.server.utils.EncodesUtils.generateAesKey;

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
    public void testContactsService()throws Exception{
        CustomerTaskUserContactsDO model = new CustomerTaskUserContactsDO();
        String phone = "18720987043";
        String salt = encodeBase64(generateAesKey());
        model.setUserPhone(encodeBase64(EncodesUtils.aesEncrypt(phone.getBytes("UTF-8"), EncodesUtils.decodeBase64(salt))));
        model.setCreatedBy("123456");
        model.setSalt("salt");
        model.setContractNo("123456");
        model.setOrgName("111");
        model.setUserAddress("test1");
        model.setTaskId(BaseStringUtils.uuid());
        contactsService.save(model);
    }























}
