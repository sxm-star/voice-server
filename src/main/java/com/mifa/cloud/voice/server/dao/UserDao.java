package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.UserDo;

public interface UserDao {

    int deleteByPrimaryKey(String id);

    int insert(UserDo record);

    int insertSelective(UserDo record);


    UserDo selectByPrimaryKey(String id);


    int updateByPrimaryKeySelective(UserDo record);

    int updateByPrimaryKey(UserDo record);
}