package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.pojo.CustomerCallStatisticsDO;
import com.mifa.cloud.voice.server.pojo.CustomerCallStatisticsDOExample;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/5/22 14:49
 * @version: v1.0.0
 */
@Service
public class CustomerCallStatisticsService extends BaseService<CustomerCallStatisticsDO> {

    /**
     * 获取当日统计信息
     * @param contractNo
     * @param startDateTime
     * @param endDateTime
     * @return
     */


    public CustomerCallStatisticsDO queryOneByContactNoAndCreateAt(String contractNo,Date startDateTime,Date endDateTime){
        CustomerCallStatisticsDOExample example = new CustomerCallStatisticsDOExample();
        example.createCriteria().andContractNoEqualTo(contractNo).andCreatedAtGreaterThan(startDateTime).andCreatedAtLessThan(endDateTime);
        List<CustomerCallStatisticsDO> list =  this.getMapper().selectByExample(example);
        if (null!=list&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
