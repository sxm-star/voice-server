package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.dao.CustomerAuthAuditMapper;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAudit;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAuditExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */
@Service
public class CustomerAuthAuditService {

    @Autowired
    private CustomerAuthAuditMapper mapper;

    /**
     * 插入记录（只插入参数非空字段）
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertSelective(CustomerAuthAudit record) {
        return mapper.insertSelective(record);
    }

    /**
     * 根据contractNo查询审核中的数据
     * auditStatus 1:认证中
     * */
    public CustomerAuthAudit selectByContractNo(String contractNo) {
        CustomerAuthAuditExample example = new CustomerAuthAuditExample();
        CustomerAuthAuditExample.Criteria criteria = example.createCriteria();
        criteria.andContractNoEqualTo(contractNo);
        criteria.andAuditStatusEqualTo("1");
        List<CustomerAuthAudit> authAuditList = mapper.selectByExample(example);
        if(authAuditList.isEmpty()) {
            return null;
        }
        return authAuditList.get(0);
    }

    /**
     * 根据id修改记录（只修改参数非空字段）
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateByPrimaryKeySelective(CustomerAuthAudit record){
        record.setUpdatedAt(new Date());
        return mapper.updateByPrimaryKeySelective(record);
    }



}
