package com.mifa.cloud.voice.server;

import com.mifa.cloud.voice.server.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    UserDao userDao;
    @Test
    public void testFindUserById(){
        System.out.println(userDao.selectByPrimaryKey("1"));
    }
}
