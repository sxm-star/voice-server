package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.enums.AuthEnum;
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
     */
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
    @Transactional(rollbackFor = Exception.class)
    public int submitAuthInfo(AuthCompanyDTO param, CustomerLoginInfo customerInfo) {
        CustomerAuthCompany customerAuthCompany = selectByPrimaryKey(param.getContractNo());
        Date date = new Date();
        // 初次提交认证信息
        if (customerAuthCompany == null) {
            // 审核总表插入数据
            CustomerAuthAudit customerAuthAudit = CustomerAuthAudit.builder()
                    .contractNo(param.getContractNo())
                    .authType("2")
                    .customerName(param.getCompanyName())
                    .mobile(customerInfo.getMobile())
                    .authTime(date)
                    .createdAt(date)
                    .createdBy(param.getContractNo())
                    .auditStatus(AuthEnum.AUTH_ING.getCode())
                    .build();
            int count = customerAuthAuditService.insertSelective(customerAuthAudit);

            // 企业审核表插入数据
            CustomerAuthCompany authCompany = new CustomerAuthCompany();
            BeanUtils.copyProperties(param, authCompany);
            authCompany.setAuthStatus(AuthEnum.AUTH_ING.getCode());
            authCompany.setCreatedAt(date);
            authCompany.setCreatedBy(param.getContractNo());
            count += insertSelective(authCompany);

            return count;
        } else {
            // 更新审核总表数据
            CustomerAuthAudit customerAuthAudit = customerAuthAuditService.selectByContractNo(param.getContractNo(), AuthEnum.AUTH_FAIL);
            customerAuthAudit.setCustomerName(param.getCompanyName());
            customerAuthAudit.setMobile(customerInfo.getMobile());
            customerAuthAudit.setUpdatedAt(date);
            customerAuthAudit.setAuditStatus(AuthEnum.AUTH_ING.getCode());
            customerAuthAudit.setRemark("");
            int count = customerAuthAuditService.updateByPrimaryKeySelective(customerAuthAudit);

            // 更新企业审核表数据
            customerAuthCompany.setCompanyName(StringUtils.isNotEmpty(param.getCompanyName()) ? param.getCompanyName() : customerAuthCompany.getCompanyName());
            customerAuthCompany.setCompanyAddress(param.getCompanyAddress());
            customerAuthCompany.setBusinessLife(param.getBusinessLife());
            customerAuthCompany.setScale(param.getScale());
            customerAuthCompany.setProfession(param.getProfession());
            customerAuthCompany.setOfficialWebsiteUrl(param.getOfficialWebsiteUrl());
            customerAuthCompany.setBusinessLicenseNo(param.getBusinessLicenseNo());
            customerAuthCompany.setBusinessLicenseUrl(param.getBusinessLicenseUrl());
            customerAuthCompany.setAuthStatus(AuthEnum.AUTH_ING.getCode());
            customerAuthCompany.setUpdatedAt(date);
            customerAuthCompany.setRemark("");
            count += updateByPrimaryKeySelective(customerAuthCompany);

            return count;
        }

    }

    /**
     * 企业认证审核
     */
    @Transactional(rollbackFor = Exception.class)
    public int authCheck(AuthCheckDTO param, CustomerAuthCompany customerAuthCompany) {
        Date date = new Date();
        //修改审核总表
        CustomerAuthAudit customerAuthAudit = customerAuthAuditService.selectByContractNo(param.getContractNo(), AuthEnum.AUTH_ING);
        customerAuthAudit.setRemark(param.getRemark());
        customerAuthAudit.setAuditStatus(param.getAuthStatus());
        customerAuthAudit.setAutitTime(date);
        customerAuthAudit.setUpdatedAt(date);
        customerAuthAudit.setUpdatedBy(param.getUpdateBy());
        int count = customerAuthAuditService.updateByPrimaryKeySelective(customerAuthAudit);

        customerAuthCompany.setRemark(param.getRemark());
        customerAuthCompany.setAuthStatus(param.getAuthStatus());
        customerAuthCompany.setUpdatedAt(date);
        customerAuthCompany.setUpdatedBy(param.getUpdateBy());
        count += updateByPrimaryKeySelective(customerAuthCompany);
        return count;
    }


}
