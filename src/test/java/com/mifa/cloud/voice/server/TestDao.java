package com.mifa.cloud.voice.server;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.dao.CustomerLoginInfoMapper;
import com.mifa.cloud.voice.server.dao.SystemKeyValueMapper;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.pojo.SystemKeyValue;
import com.mifa.cloud.voice.server.pojo.SystemKeyValueExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: sxm
 * @date: 2018/4/9 15:25
 * @version: v1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestDao {

    @Autowired
    private CustomerLoginInfoMapper customerLoginInfoMapper;

    @Autowired
    private SystemKeyValueMapper systemKeyValueMapper;

    @Test
    public void testCustomer() {
        CustomerLoginInfo customerLoginInfo = CustomerLoginInfo.builder()
                .contractNo("1")
                .contractType("2")
                .loginName("zhangsan")
                .mobile("18225695244")
                .loginPasswd("11234566")
                .salt("1234455")
                .loginStatus("1")
                .registType("1")
                .build();
        customerLoginInfoMapper.insertSelective(customerLoginInfo);

    }

    @Test
    public void testSystemKeyValue() {
        PageHelper.startPage(1,10);
        SystemKeyValueExample example = new SystemKeyValueExample();
        List<SystemKeyValue> list = systemKeyValueMapper.selectByExample(example);
        PageInfo<SystemKeyValue> info = new PageInfo<>(list);

        List<SystemKeyValue> infoList = info.getList();
        System.out.println(infoList.get(0));
        System.out.println(JSONObject.toJSONString(infoList));

    }


























}
