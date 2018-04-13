package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.dao.CustomerAauthPersonMapper;
import com.mifa.cloud.voice.server.pojo.CustomerAauthPerson;
import com.mifa.cloud.voice.server.utils.SeqProducerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/11.
 */
@Service
public class CustomerAauthPersonService {

    @Autowired
    private CustomerAauthPersonMapper mapper;

    /**
     * 插入记录（只插入参数非空字段）
     */
    public int insertSelective(CustomerAauthPerson record) {
        if (StringUtils.isEmpty(record.getContractNo())) {
            record.setContractNo(SeqProducerUtil.getContractNo());
        }
        return mapper.insertSelective(record);
    }

    /**
     * 根据id查询记录
     * */
    public CustomerAauthPerson selectByPrimaryKey(String contractNo) {
        if (StringUtils.isEmpty(contractNo)) {
            return null;
        }
        return mapper.selectByPrimaryKey(contractNo);
    }

    /**
     * 根据id修改记录（只修改参数非空字段）
     */
    public int updateByPrimaryKeySelective(CustomerAauthPerson record) {
        record.setUpdatedAt(new Date());
        return mapper.updateByPrimaryKeySelective(record);
    }


}
