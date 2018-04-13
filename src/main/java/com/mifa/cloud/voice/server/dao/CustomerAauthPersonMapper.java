package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.CustomerAauthPerson;
import com.mifa.cloud.voice.server.pojo.CustomerAauthPersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAauthPersonMapper {

    int countByExample(CustomerAauthPersonExample example);

    int deleteByExample(CustomerAauthPersonExample example);

    int deleteByPrimaryKey(String contractNo);

    int insert(CustomerAauthPerson record);

    int insertSelective(CustomerAauthPerson record);

    List<CustomerAauthPerson> selectByExample(CustomerAauthPersonExample example);

    CustomerAauthPerson selectByPrimaryKey(String contractNo);

    int updateByExampleSelective(@Param("record") CustomerAauthPerson record, @Param("example") CustomerAauthPersonExample example);

    int updateByExample(@Param("record") CustomerAauthPerson record, @Param("example") CustomerAauthPersonExample example);

    int updateByPrimaryKeySelective(CustomerAauthPerson record);

    int updateByPrimaryKey(CustomerAauthPerson record);
}