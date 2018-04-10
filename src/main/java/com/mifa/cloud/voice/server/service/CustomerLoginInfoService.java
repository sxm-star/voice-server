package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.dao.CustomerLoginInfoMapper;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfoExample;
import com.mifa.cloud.voice.server.utils.SeqProducerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */
@Service
public class CustomerLoginInfoService {

    @Autowired
    private CustomerLoginInfoMapper customerLoginInfoMapper;


    /**
     * 根据loginName查询
     * */
    public CustomerLoginInfo findByLoginName(String loginName) {
        CustomerLoginInfoExample example = new CustomerLoginInfoExample();
        CustomerLoginInfoExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<CustomerLoginInfo> customerLoginInfos = customerLoginInfoMapper.selectByExample(example);
        if (customerLoginInfos.isEmpty()) {
            return null;
        }
        return customerLoginInfos.get(0);
    }

    public int insertSelective(CustomerLoginInfo record) {
        if(StringUtils.isEmpty(record.getContractNo())) {
            record.setContractNo(SeqProducerUtil.getContractNo());
        }
        return customerLoginInfoMapper.insertSelective(record);
    }



}
