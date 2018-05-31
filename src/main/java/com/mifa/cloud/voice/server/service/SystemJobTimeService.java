package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.SystemJobTimeReqDto;
import com.mifa.cloud.voice.server.commons.dto.SystemJobTimeRspDTO;
import com.mifa.cloud.voice.server.commons.enums.CallTimeTypeEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.pojo.SystemJobTimeDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/5/28 16:52
 * @version: v1.0.0
 */
@Service
@Slf4j
public class SystemJobTimeService extends BaseService<SystemJobTimeDO> {

    public boolean addAllowCallTime(SystemJobTimeReqDto systemJobTimeReqDto) {

        try {
            if (systemJobTimeReqDto.getCallTimeTypeEnum()!=null){
                this.deleteByWhere(SystemJobTimeDO.builder().createdBy(systemJobTimeReqDto.getCreatedBy()).callTimeType(systemJobTimeReqDto.getCallTimeTypeEnum().getCode()).build());
                systemJobTimeReqDto.getAllowTimeRangs().forEach(rsp->{
                    SystemJobTimeDO systemJobTimeDO =   BaseBeanUtils.convert(systemJobTimeReqDto,SystemJobTimeDO.class);
                    systemJobTimeDO.setCallTimeType(systemJobTimeReqDto.getCallTimeTypeEnum().getCode());
                    systemJobTimeDO.setStatus(String.valueOf(StatusEnum.NORMAL.getCode()));
                    systemJobTimeDO.setCallAllowTimeStart(rsp.getCallAllowTimeStart());
                    systemJobTimeDO.setCallAllowTimeEnd(rsp.getCallAllowTimeEnd());
                    this.save(systemJobTimeDO);
                });
            }
            return Boolean.TRUE;
        }catch (Exception e){
            log.error("添加异常:{}",e);
            return Boolean.FALSE;
        }
    }

    public List<SystemJobTimeRspDTO> getAllowCallTime(String contractNo){
        List<SystemJobTimeRspDTO> rspDTOs = new ArrayList<>();
        List<SystemJobTimeDO> systemJobTimeDOs =  this.queryListByWhere(SystemJobTimeDO.builder().createdBy(contractNo).build());
       if(null!=systemJobTimeDOs && systemJobTimeDOs.size()>0){

           systemJobTimeDOs.forEach(systemJobTimeDO -> {
               SystemJobTimeRspDTO rspDTO  =   BaseBeanUtils.convert(systemJobTimeDO,SystemJobTimeRspDTO.class);
               rspDTO.setCallTimeType(CallTimeTypeEnum.getDesc(rspDTO.getCallTimeType()));
               rspDTO.setStatus(StatusEnum.getDesc(rspDTO.getStatus()));
               rspDTOs.add(rspDTO);
           });
       }
        return  rspDTOs;
    }
}
