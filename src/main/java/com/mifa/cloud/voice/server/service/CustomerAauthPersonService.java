package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.dao.CustomerAauthPersonMapper;
import com.mifa.cloud.voice.server.dao.CustomerAuthAuditMapper;
import com.mifa.cloud.voice.server.dto.AuthCheckDTO;
import com.mifa.cloud.voice.server.dto.AuthPersionDTO;
import com.mifa.cloud.voice.server.pojo.CustomerAauthPerson;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAudit;
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
    @Transactional(rollbackFor=Exception.class)
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
    @Transactional(rollbackFor=Exception.class)
    public int updateByPrimaryKeySelective(CustomerAauthPerson record) {
        record.setUpdatedAt(new Date());
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 提交个人认证信息
     */
    @Transactional(rollbackFor=Exception.class)
    public int submitAuthInfo(AuthPersionDTO param, CustomerLoginInfo customerInfo) {
        // 审核总表插入数据
        CustomerAuthAudit customerAuthAudit = CustomerAuthAudit.builder()
                .contractNo(param.getContractNo())
                .authType("1") // 认证类型 1-个人认证、2-企业认证
                .customerName(param.getName())
                .mobile(customerInfo.getMobile())
                .authTime(new Date())
                .auditStatus("1") // 审核状态 1:认证中;2:认证通过,3:认证不通过
                .build();
        int count = customerAuthAuditMapper.insertSelective(customerAuthAudit);

        // 个人认证表插入数据
        CustomerAauthPerson authPerson = new CustomerAauthPerson();
        BeanUtils.copyProperties(param, authPerson);
        authPerson.setAuthStatus("1");
        count += insertSelective(authPerson);
        return count;
    }

    /**
     * 个人认证审核
     */
    @Transactional(rollbackFor = Exception.class)
    public int authCheck(AuthCheckDTO param, CustomerAauthPerson customerAauthPerson) {
        //修改审核总表
        CustomerAuthAudit customerAuthAudit = customerAuthAuditService.selectByContractNo(param.getContractNo());
        customerAuthAudit.setRemark(param.getRemark());
        customerAuthAudit.setAuditStatus(param.getAuthStatus());
        customerAuthAudit.setUpdatedAt(new Date());
        int count = customerAuthAuditMapper.updateByPrimaryKeySelective(customerAuthAudit);

        customerAauthPerson.setRemark(param.getRemark());
        customerAauthPerson.setAuthStatus(param.getAuthStatus());
        count += updateByPrimaryKeySelective(customerAauthPerson);

        return count;
    }

}
