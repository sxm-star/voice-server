package com.mifa.cloud.voice.server;

import com.alibaba.fastjson.JSONObject;
import com.mifa.cloud.voice.server.component.RandomSort;
import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.config.ConstConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class TestService {

    @Autowired
    KeyValueDao keyValueDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConstConfig constConfig;

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

}
