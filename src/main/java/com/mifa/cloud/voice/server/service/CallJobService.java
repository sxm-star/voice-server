package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.CustomerCallJobDto;
import com.mifa.cloud.voice.server.dao.CallJobDAO;
import com.mifa.cloud.voice.server.pojo.CallJobDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: songxm
 * @date: 2018/4/16 19:20
 * @version: v1.0.0
 */
@Service
public class CallJobService extends BaseService<CallJobDO>{
    @Autowired
    CallJobDAO customerCallJobDAO;

    public Boolean addCallJob(CustomerCallJobDto customerCallJobDto){
        CallJobDO customerCallJobDO = BaseBeanUtils.convert(customerCallJobDto,CallJobDO.class);
        int cnt = customerCallJobDAO.insert(customerCallJobDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

}
