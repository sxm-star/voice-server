package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: sxm
 * @date: 2018/4/8 19:02
 * @version: v1.0.0
 */
@Service
public class UserService {
     @Autowired
    UserDao userDao;

    public Object findUserById(String id){
       return userDao.selectByPrimaryKey(id);
    }
}
