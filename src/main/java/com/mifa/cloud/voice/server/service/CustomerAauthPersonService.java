package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.enums.AuthEnum;
import com.mifa.cloud.voice.server.dao.CustomerAauthPersonMapper;
import com.mifa.cloud.voice.server.dao.CustomerAuthAuditMapper;
import com.mifa.cloud.voice.server.commons.dto.AuthCheckDTO;
import com.mifa.cloud.voice.server.commons.dto.AuthPersionDTO;
import com.mifa.cloud.voice.server.pojo.CustomerAauthPerson;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAudit;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
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
public class CustomerAauthPersonService {

    @Autowired
    private CustomerAauthPersonMapper mapper;

    @Autowired
    private CustomerAuthAuditMapper customerAuthAuditMapper;

    @Autowired
    private CustomerAuthAuditService customerAuthAuditService;

    /**
     * 插入记录（只插入参数非空字段）
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(CustomerAauthPerson record) {
        if (StringUtils.isEmpty(record.getContractNo())) {
            record.setContractNo(SeqProducerUtil.getContractNo());
        }
        return mapper.insertSelective(record);
    }

    /**
     * 根据id查询记录
     */
    public CustomerAauthPerson selectByPrimaryKey(String contractNo) {
        if (StringUtils.isEmpty(contractNo)) {
            return null;
        }
        return mapper.selectByPrimaryKey(contractNo);
    }

    /**
     * 根据id修改记录（只修改参数非空字段）
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(CustomerAauthPerson record) {
        record.setUpdatedAt(new Date());
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 提交个人认证信息
     */
    @Transactional(rollbackFor = Exception.class)
    public int submitAuthInfo(AuthPersionDTO param, CustomerLoginInfo customerInfo) {

        CustomerAauthPerson customerAauthPerson = selectByPrimaryKey(param.getContractNo());
        Date date = new Date();
        // 初次提交认证信息
        if (customerAauthPerson == null) {
            // 审核总表插入数据
            CustomerAuthAudit customerAuthAudit = CustomerAuthAudit.builder()
                    .contractNo(param.getContractNo())
                    .authType("1") // 认证类型 1-个人认证、2-企业认证
                    .customerName(param.getName())
                    .mobile(customerInfo.getMobile())
                    .authTime(date)
                    .createdAt(date)
                    .createdBy(param.getContractNo())
                    .auditStatus(AuthEnum.AUTH_ING.getCode()) // 审核状态 1:认证中;2:认证通过,3:认证不通过
                    .build();
            int count = customerAuthAuditMapper.insertSelective(customerAuthAudit);

            // 个人认证表插入数据
            CustomerAauthPerson authPerson = new CustomerAauthPerson();
            BeanUtils.copyProperties(param, authPerson);
            authPerson.setAuthStatus(AuthEnum.AUTH_ING.getCode());
            authPerson.setCreatedAt(date);
            authPerson.setCreatedBy(param.getContractNo());
            count += insertSelective(authPerson);
            return count;
        } else {
            // 如果审核信息已存在
            // 更新审核总表数据
            CustomerAuthAudit customerAuthAudit = customerAuthAuditService.selectByContractNo(param.getContractNo(), AuthEnum.AUTH_FAIL);
            customerAuthAudit.setContractNo(param.getContractNo());
            customerAuthAudit.setCustomerName(param.getName());
            customerAuthAudit.setMobile(customerInfo.getMobile());
            customerAuthAudit.setUpdatedAt(date);
            customerAuthAudit.setAuditStatus(AuthEnum.AUTH_ING.getCode());
            customerAuthAudit.setRemark("");
            int count = customerAuthAuditService.updateByPrimaryKeySelective(customerAuthAudit);

            // 更新个人认证表数据
            customerAauthPerson.setName(StringUtils.isNotEmpty(param.getName()) ? param.getName() : customerAauthPerson.getName());
            customerAauthPerson.setIdCard(StringUtils.isNoneEmpty(param.getIdCard()) ? param.getIdCard() : customerAauthPerson.getIdCard());
            customerAauthPerson.setProfession(StringUtils.isNotEmpty(param.getProfession()) ? param.getProfession() : customerAauthPerson.getProfession());
            customerAauthPerson.setIdCardImgUpUrl(StringUtils.isNoneEmpty(param.getIdCardImgUpUrl()) ? param.getIdCardImgUpUrl() : customerAauthPerson.getIdCardImgUpUrl());
            customerAauthPerson.setIdCardImgBackUrl(StringUtils.isNoneEmpty(param.getIdCardImgBackUrl()) ? param.getIdCardImgBackUrl() : customerAauthPerson.getIdCardImgBackUrl());
            customerAauthPerson.setIdCardImgHandheldUrl(StringUtils.isNoneEmpty(param.getIdCardImgHandheldUrl()) ? param.getIdCardImgHandheldUrl() : customerAauthPerson.getIdCardImgHandheldUrl());
            customerAauthPerson.setAuthStatus(AuthEnum.AUTH_ING.getCode());
            customerAauthPerson.setUpdatedAt(date);
            customerAauthPerson.setRemark("");
            count += updateByPrimaryKeySelective(customerAauthPerson);
            return count;
        }

    }

    /**
     * 个人认证审核
     */
    @Transactional(rollbackFor = Exception.class)
    public int authCheck(AuthCheckDTO param, CustomerAauthPerson customerAauthPerson) {
        Date date = new Date();
        //修改审核总表
        CustomerAuthAudit customerAuthAudit = customerAuthAuditService.selectByContractNo(param.getContractNo(), AuthEnum.AUTH_ING);
        if(customerAuthAudit == null) {
            return 0;
        }
        customerAuthAudit.setRemark(param.getRemark());
        customerAuthAudit.setAuditStatus(param.getAuthStatus());
        customerAuthAudit.setUpdatedAt(date);
        customerAuthAudit.setAutitTime(date);
        customerAuthAudit.setUpdatedBy(param.getUpdateBy());

        int count = customerAuthAuditMapper.updateByPrimaryKeySelective(customerAuthAudit);

        customerAauthPerson.setRemark(param.getRemark());
        customerAauthPerson.setAuthStatus(param.getAuthStatus());
        customerAauthPerson.setUpdatedAt(date);
        customerAauthPerson.setUpdatedBy(param.getUpdateBy());
        count += updateByPrimaryKeySelective(customerAauthPerson);

        return count;
    }

}
