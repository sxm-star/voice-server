package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.CustomerRecharge;
import com.mifa.cloud.voice.server.pojo.CustomerRechargeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRechargeMapper {
    int countByExample(CustomerRechargeExample example);

    int deleteByExample(CustomerRechargeExample example);

    int deleteByPrimaryKey(String rechargeId);

    int insert(CustomerRecharge record);

    int insertSelective(CustomerRecharge record);

    List<CustomerRecharge> selectByExample(CustomerRechargeExample example);

    CustomerRecharge selectByPrimaryKey(String rechargeId);

    int updateByExampleSelective(@Param("record") CustomerRecharge record, @Param("example") CustomerRechargeExample example);

    int updateByExample(@Param("record") CustomerRecharge record, @Param("example") CustomerRechargeExample example);

    int updateByPrimaryKeySelective(CustomerRecharge record);

    int updateByPrimaryKey(CustomerRecharge record);
}