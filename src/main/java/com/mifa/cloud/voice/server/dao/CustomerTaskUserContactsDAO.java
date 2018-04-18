package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.CustomerTaskUserContactsDO;
import com.mifa.cloud.voice.server.pojo.CustomerTaskUserContactsDOExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerTaskUserContactsDAO {
    long countByExample(CustomerTaskUserContactsDOExample example);

    int deleteByExample(CustomerTaskUserContactsDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustomerTaskUserContactsDO record);

    int insertBatch(List<CustomerTaskUserContactsDO> list);

    int insertSelective(CustomerTaskUserContactsDO record);

    List<CustomerTaskUserContactsDO> selectByExample(CustomerTaskUserContactsDOExample example);

    CustomerTaskUserContactsDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustomerTaskUserContactsDO record, @Param("example") CustomerTaskUserContactsDOExample example);

    int updateByExample(@Param("record") CustomerTaskUserContactsDO record, @Param("example") CustomerTaskUserContactsDOExample example);

    int updateByPrimaryKeySelective(CustomerTaskUserContactsDO record);

    int updateByPrimaryKey(CustomerTaskUserContactsDO record);
}