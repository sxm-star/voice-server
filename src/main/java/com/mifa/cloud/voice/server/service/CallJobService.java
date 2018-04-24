package com.mifa.cloud.voice.server.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.CallJobDto;
import com.mifa.cloud.voice.server.commons.dto.CustomerCallJobDto;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.dao.CallJobDAO;
import com.mifa.cloud.voice.server.pojo.CallJobDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/4/16 19:20
 * @version: v1.0.0
 */
@Service
@Slf4j
public class CallJobService extends BaseService<CallJobDO>{
    @Autowired
    CallJobDAO customerCallJobDAO;

    public Boolean addCallJob(CustomerCallJobDto customerCallJobDto){
        CallJobDO customerCallJobDO = BaseBeanUtils.convert(customerCallJobDto,CallJobDO.class);
        int cnt = customerCallJobDAO.insert(customerCallJobDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    public PageDto<CallJobDto> queryCallJobList(String contractNo ,String  jobName, Integer pageNum, Integer pageize) {
        CallJobDO callJobDO = new CallJobDO();
        callJobDO.setJobName(jobName);
        callJobDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        PageDto<CallJobDto> pageDto = null;
        try {
            PageHelper.startPage(pageNum, pageize);
            Example example = new Example(CallJobDO.class);
            // 声明条件
            Example.Criteria createCriteria = example.createCriteria();
            if (StringUtils.isNotEmpty(jobName)){
                createCriteria.andLike("jobName", "%"+jobName+"%");
            }
            createCriteria.andEqualTo("contractNo",contractNo);
            List<CallJobDO> listDOs = customerCallJobDAO.selectByExample (example);
            if (listDOs!=null&& listDOs.size()>0){
                PageInfo<CallJobDO> pageInfo = new PageInfo<CallJobDO>(listDOs);
                List<CallJobDto> rspList = new ArrayList<>();

                listDOs.forEach(item -> {
                    CallJobDto callJobDto = BaseBeanUtils.convert(item, CallJobDto.class);
                    rspList.add(callJobDto);
                });
                pageDto = BaseBeanUtils.convert(pageInfo, PageDto.class);
                pageDto.setList(rspList);
            }
            return pageDto;
        } catch (Exception e) {
            log.error("拨打计划分页查询错误:{}", e);
            return pageDto;
        }
    }

}
