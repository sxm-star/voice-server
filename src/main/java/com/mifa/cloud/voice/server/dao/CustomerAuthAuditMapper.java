package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.CustomerAuthAudit;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAuditExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAuthAuditMapper {
    int countByExample(CustomerAuthAuditExample example);

    int deleteByExample(CustomerAuthAuditExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustomerAuthAudit record);

    int insertSelective(CustomerAuthAudit record);

    List<CustomerAuthAudit> selectByExample(CustomerAuthAuditExample example);

    CustomerAuthAudit selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustomerAuthAudit record, @Param("example") CustomerAuthAuditExample example);

    int updateByExample(@Param("record") CustomerAuthAudit record, @Param("example") CustomerAuthAuditExample example);

    int updateByPrimaryKeySelective(CustomerAuthAudit record);

    int updateByPrimaryKey(CustomerAuthAudit record);
}