package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.commons.enums.AuthEnum;
import com.mifa.cloud.voice.server.dao.CustomerAuthAuditMapper;
import com.mifa.cloud.voice.server.commons.dto.AuthCheckListDTO;
import com.mifa.cloud.voice.server.commons.dto.CustomerAuthAuditVO;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAudit;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAuditExample;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public CustomerAuthAudit selectByContractNo(String contractNo, AuthEnum authEnum) {
        CustomerAuthAuditExample example = new CustomerAuthAuditExample();
        CustomerAuthAuditExample.Criteria criteria = example.createCriteria();
        criteria.andContractNoEqualTo(contractNo);
        criteria.andAuditStatusEqualTo(authEnum.getCode());
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

    /**
     * 根据条件查询list
     */
    public PageDto<CustomerAuthAuditVO> selectAuthCheckList(AuthCheckListDTO dto, Integer page, Integer rows) {

        CustomerAuthAuditExample example = new CustomerAuthAuditExample();
        CustomerAuthAuditExample.Criteria criteria = example.createCriteria();
        if(dto != null) {
            if(StringUtils.isNotEmpty(dto.getAuthType())) {
                criteria.andAuthTypeEqualTo(dto.getAuthType());
            }
            if (StringUtils.isNotEmpty(dto.getCustomerName())) {
                criteria.andCustomerNameLike("%" + dto.getCustomerName() + "%");
            }
            if (StringUtils.isNotEmpty(dto.getMobile())) {
                criteria.andMobileLike("%" + dto.getMobile() + "%");
            }
            if(StringUtils.isNotEmpty(dto.getAuditStatus())) {
                criteria.andAuditStatusEqualTo(dto.getAuditStatus());
            }
        }
        // 加入分页
        PageHelper.startPage(page, rows);
        example.setOrderByClause("auth_time DESC");
        List<CustomerAuthAudit> list = mapper.selectByExample(example);

        List<CustomerAuthAuditVO> voList = new ArrayList<>();
        PageInfo<CustomerAuthAudit> pageInfo = new PageInfo<>(list);
        pageInfo.getList().stream()
                .forEach(
                        item -> voList.add(BaseBeanUtils.convert(item, CustomerAuthAuditVO.class))
                );
        PageDto<CustomerAuthAuditVO> pageResult =  BaseBeanUtils.convert(pageInfo,PageDto.class);
        pageResult.setList(voList);

        return pageResult;

    }


}
