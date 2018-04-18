package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.CustomerAuthCompany;
import com.mifa.cloud.voice.server.pojo.CustomerAuthCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerAuthCompanyMapper {
    int countByExample(CustomerAuthCompanyExample example);

    int deleteByExample(CustomerAuthCompanyExample example);

    int deleteByPrimaryKey(String contractNo);

    int insert(CustomerAuthCompany record);

    int insertSelective(CustomerAuthCompany record);

    List<CustomerAuthCompany> selectByExample(CustomerAuthCompanyExample example);

    CustomerAuthCompany selectByPrimaryKey(String contractNo);

    int updateByExampleSelective(@Param("record") CustomerAuthCompany record, @Param("example") CustomerAuthCompanyExample example);

    int updateByExample(@Param("record") CustomerAuthCompany record, @Param("example") CustomerAuthCompanyExample example);

    int updateByPrimaryKeySelective(CustomerAuthCompany record);

    int updateByPrimaryKey(CustomerAuthCompany record);
}