package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLoginInfoMapper {
    /**
     *
     * @mbggenerated 2018-04-10
     */
    int countByExample(CustomerLoginInfoExample example);

    /**
     *
     * @mbggenerated 2018-04-10
     */
    int deleteByExample(CustomerLoginInfoExample example);

    /**
     *
     * @mbggenerated 2018-04-10
     */
    int deleteByPrimaryKey(String contractNo);

    /**
     *
     * @mbggenerated 2018-04-10
     */
    int insert(CustomerLoginInfo record);

    /**
     *
     * @mbggenerated 2018-04-10
     */
    int insertSelective(CustomerLoginInfo record);

    /**
     *
     * @mbggenerated 2018-04-10
     */
    List<CustomerLoginInfo> selectByExample(CustomerLoginInfoExample example);

    /**
     *
     * @mbggenerated 2018-04-10
     */
    CustomerLoginInfo selectByPrimaryKey(String contractNo);

    /**
     *
     * @mbggenerated 2018-04-10
     */
    int updateByExampleSelective(@Param("record") CustomerLoginInfo record, @Param("example") CustomerLoginInfoExample example);

    /**
     *
     * @mbggenerated 2018-04-10
     */
    int updateByExample(@Param("record") CustomerLoginInfo record, @Param("example") CustomerLoginInfoExample example);

    /**
     *
     * @mbggenerated 2018-04-10
     */
    int updateByPrimaryKeySelective(CustomerLoginInfo record);

    /**
     *
     * @mbggenerated 2018-04-10
     */
    int updateByPrimaryKey(CustomerLoginInfo record);
}