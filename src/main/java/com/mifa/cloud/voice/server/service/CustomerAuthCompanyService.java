package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.dao.CustomerAuthCompanyMapper;
import com.mifa.cloud.voice.server.commons.dto.AuthCheckDTO;
import com.mifa.cloud.voice.server.commons.dto.AuthCompanyDTO;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAudit;
import com.mifa.cloud.voice.server.pojo.CustomerAuthCompany;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.utils.SeqProducerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/11.
 */
@Service
public class CustomerAuthCompanyService {

    @Autowired
    private CustomerAuthCompanyMapper mapper;

    @Autowired
    private CustomerAuthAuditService customerAuthAuditService;

    /**
     * 插入记录（只插入参数非空字段）
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(CustomerAuthCompany record) {
        if (StringUtils.isEmpty(record.getContractNo())) {
            record.setContractNo(SeqProducerUtil.getContractNo());
        }
        return mapper.insertSelective(record);
    }

    /**
     * 根据id查询记录
     * */
    public CustomerAuthCompany selectByPrimaryKey(String contractNo) {
        if (StringUtils.isEmpty(contractNo)) {
            return null;
        }
        return mapper.selectByPrimaryKey(contractNo);
    }

    /**
     * 根据id修改记录（只修改参数非空字段）
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(CustomerAuthCompany record) {
        record.setUpdatedAt(new Date());
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 提交企业认证信息
     */
    @Transactional(rollbackFor=Exception.class)
    public int submitAuthInfo(AuthCompanyDTO param, CustomerLoginInfo customerInfo) {

        // 审核总表插入数据
        CustomerAuthAudit customerAuthAudit = CustomerAuthAudit.builder()
                .contractNo(param.getContractNo())
                .authType("2")
                .customerName(param.getCompanyName())
                .mobile(customerInfo.getMobile())
                .authTime(new Date())
                .auditStatus("1")
                .build();
        int count = customerAuthAuditService.insertSelective(customerAuthAudit);

        CustomerAuthCompany authCompany = new CustomerAuthCompany();
        BeanUtils.copyProperties(param, authCompany);
        authCompany.setAuthStatus("1");
        count += insertSelective(authCompany);

        return count;
    }

    /**
     * 企业认证审核
     */
    @Transactional(rollbackFor = Exception.class)
    public int authCheck(AuthCheckDTO param, CustomerAuthCompany customerAuthCompany) {

        //修改审核总表
        CustomerAuthAudit customerAuthAudit = customerAuthAuditService.selectByContractNo(param.getContractNo());
        customerAuthAudit.setRemark(param.getRemark());
        customerAuthAudit.setAuditStatus(param.getAuthStatus());
        int count = customerAuthAuditService.updateByPrimaryKeySelective(customerAuthAudit);

        customerAuthCompany.setRemark(param.getRemark());
        customerAuthCompany.setAuthStatus(param.getAuthStatus());
        count += updateByPrimaryKeySelective(customerAuthCompany);
        return count;
    }











}
