package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.CallDetailQueryDto;
import com.mifa.cloud.voice.server.commons.dto.CustomerTaskCallDetailDto;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.commons.enums.CallFlagEnum;
import com.mifa.cloud.voice.server.dao.CustomerTaskCallDetailDAO;
import com.mifa.cloud.voice.server.pojo.CustomerTaskCallDetailDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/4/25 17:25
 * @version: v1.0.0
 */
@Service
@Slf4j
public class CustomerTaskCallDetailService extends BaseService<CustomerTaskCallDetailDO>{

    @Autowired
    CustomerTaskCallDetailDAO taskCallDetailDAO;

    public PageDto<CustomerTaskCallDetailDto> queryTaskDetailList(CallDetailQueryDto callDetailQueryDto,Integer pageNum,Integer pageSize){
        CustomerTaskCallDetailDO customerTaskCallDetailDO = BaseBeanUtils.convert(callDetailQueryDto,CustomerTaskCallDetailDO.class);
        PageDto<CustomerTaskCallDetailDto> pageDto = null;
        try {
            PageInfo<CustomerTaskCallDetailDO>  pageInfo = this.queryListByPageAndOrder(customerTaskCallDetailDO,pageNum,pageSize," created_at desc ");
            if (pageInfo!=null ||  pageInfo.getList()!=null || pageInfo.getList().size()>0){
                pageDto = BaseBeanUtils.convert(pageInfo,PageDto.class);
                List<CustomerTaskCallDetailDto> resList = new ArrayList<>();
                pageInfo.getList().forEach(item->{
                    CustomerTaskCallDetailDto detailDto = BaseBeanUtils.convert(item,CustomerTaskCallDetailDto.class);
                    detailDto.setCallFlag(CallFlagEnum.getDesc(item.getCallFlag()));
                    resList.add(detailDto);
                });
                pageDto.setList(resList);
            }
        }catch (Exception e){
            log.error("查询异常:{}",e);
        }
        return pageDto;
    }
}
