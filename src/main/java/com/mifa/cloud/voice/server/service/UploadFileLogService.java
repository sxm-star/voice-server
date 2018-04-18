package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.dao.UploadFileLogMapper;
import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.pojo.UploadFileLogExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */
@Service
public class UploadFileLogService {

    @Autowired
    private UploadFileLogMapper mapper;

    /**
     * 插入记录（只插入参数非空字段）
     */
    @Transactional(rollbackFor = Exception.class)
    public int insert(UploadFileLog record) {
        return mapper.insert(record);
    }


    public List<UploadFileLog> selectByFileTypeAndBizType(String fileType, String bizType, String status) {
        UploadFileLogExample example = new UploadFileLogExample();
        UploadFileLogExample.Criteria criteria = example.createCriteria();
        criteria.andFileTypeEqualTo(fileType);
        criteria.andBizTypeEqualTo(bizType);
        criteria.andFileStatusEqualTo(status);
        List<UploadFileLog> fileLogs = mapper.selectByExample(example);
        if(fileLogs.isEmpty()) {
            return Collections.emptyList();
        }
        return fileLogs;
    }
}
