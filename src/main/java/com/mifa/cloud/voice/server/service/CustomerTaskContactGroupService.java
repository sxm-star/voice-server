package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.pojo.CustomerTaskContactGroupDO;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import org.springframework.stereotype.Service;

/**
 * @author: songxm
 * @date: 2018/4/19 11:32
 * @version: v1.0.0
 */
@Service
public class CustomerTaskContactGroupService extends BaseService<CustomerTaskContactGroupDO>{

    public Boolean addContactGroup(String groupName,String source){
        CustomerTaskContactGroupDO customerTaskContactGroupDO = new CustomerTaskContactGroupDO();
        customerTaskContactGroupDO.setTaskId(BaseStringUtils.uuid());
        customerTaskContactGroupDO.setSource(source);
        customerTaskContactGroupDO.setGroupName(groupName);
        this.save(customerTaskContactGroupDO);
        return Boolean.TRUE;
    }
}
