package com.mifa.cloud.voice.server.dao.test;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerTaskUserContactsDAO {
    long countByExample(CustomerTaskUserContactsDOExample example);

    int deleteByExample(CustomerTaskUserContactsDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustomerTaskUserContactsDO record);

    int insertSelective(CustomerTaskUserContactsDO record);

    List<CustomerTaskUserContactsDO> selectByExample(CustomerTaskUserContactsDOExample example);

    CustomerTaskUserContactsDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustomerTaskUserContactsDO record, @Param("example") CustomerTaskUserContactsDOExample example);

    int updateByExample(@Param("record") CustomerTaskUserContactsDO record, @Param("example") CustomerTaskUserContactsDOExample example);

    int updateByPrimaryKeySelective(CustomerTaskUserContactsDO record);

    int updateByPrimaryKey(CustomerTaskUserContactsDO record);
}