package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.dao.SystemKeyValueMapper;
import com.mifa.cloud.voice.server.pojo.SystemKeyValue;
import com.mifa.cloud.voice.server.pojo.SystemKeyValueExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/4/12.
 */
@Service
public class SystemKeyValueService {

    @Autowired
    private SystemKeyValueMapper mapper;

    /**
     * 插入记录（只插入参数非空字段）
     */
    @Transactional(rollbackFor = Exception.class)
    public int insert(SystemKeyValue record) {
        return mapper.insert(record);
    }

    /**
     * 根据字典类型和key获得字典集合
     * */
    public List<SystemKeyValue> getKeyValueListByType(String keyValueType, String key) {
        SystemKeyValueExample example = new SystemKeyValueExample();
        SystemKeyValueExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(key)) {
            criteria.andParamKeyEqualTo(key);
        }
        criteria.andBizTypeEqualTo(keyValueType);
        return mapper.selectByExample(example);
    }


}
