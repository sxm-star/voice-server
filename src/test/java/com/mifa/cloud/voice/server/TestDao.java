package com.mifa.cloud.voice.server;

import com.mifa.cloud.voice.server.dao.CustomerLoginInfoMapper;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
