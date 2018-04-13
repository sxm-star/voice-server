package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.dao.CustomerAuthCompanyMapper;
import com.mifa.cloud.voice.server.pojo.CustomerAuthCompany;
import com.mifa.cloud.voice.server.utils.SeqProducerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/11.
 */
@Service
public class CustomerAuthCompanyService {

    @Autowired
    private CustomerAuthCompanyMapper mapper;

    /**
     * 插入记录（只插入参数非空字段）
     */
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
    public int updateByPrimaryKeySelective(CustomerAuthCompany record) {
        record.setUpdatedAt(new Date());
        return mapper.updateByPrimaryKeySelective(record);
    }


}
