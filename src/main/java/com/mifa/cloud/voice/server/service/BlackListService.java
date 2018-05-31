package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.BlackListDTO;
import com.mifa.cloud.voice.server.commons.dto.BlackListRspDTO;
import com.mifa.cloud.voice.server.commons.dto.PageDTO;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.exception.BaseBizException;
import com.mifa.cloud.voice.server.pojo.BlackListDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/5/22 14:48
 * @version: v1.0.0
 */
@Service
@Slf4j
public class BlackListService extends BaseService<BlackListDO>{

    /**
     * 添加黑名单记录
     * @param blackListDTO
     * @return
     */
    public Boolean addBlackList(BlackListDTO blackListDTO){
        BlackListDO blackListDO =  BaseBeanUtils.convert(blackListDTO,BlackListDO.class);
        blackListDO.setStatus(String.valueOf(blackListDTO.getBlackListStatus().getCode()));
        blackListDO.setType(blackListDTO.getType().name());
        blackListDO.setTag(blackListDTO.getTag().name());
        blackListDO.setCreatedBy(blackListDTO.getContractNo());
        blackListDO.setUpdatedBy(blackListDTO.getContractNo());
        int cnt =  this.saveSelective(blackListDO);
        return cnt>0?Boolean.TRUE:Boolean.FALSE;
    }

    public BlackListDTO getBlackList(String contractNo,String id){
        BlackListDO blackListDO = this.queryById(Long.parseLong(id));
        if (blackListDO.getContractNo().equals(contractNo)){
            BlackListDTO blackListDTO =  BaseBeanUtils.convert(blackListDO,BlackListDTO.class);
            return blackListDTO;
        }
        else {
            throw new BaseBizException("400","不合法用户");
        }
    }

    public Boolean delBlackList(String contractNo,String id){
        BlackListDO blackListDO = this.queryById(Long.parseLong(id));
        if (blackListDO.getContractNo().equals(contractNo)){
           int cnt =  this.updateByIdSelective(BlackListDO.builder().id(Long.parseLong(id)).contractNo(contractNo).status(String.valueOf(StatusEnum.BLOCK.getCode())).build());
            return cnt>0?Boolean.TRUE:Boolean.FALSE;
        }
        else {
            throw new BaseBizException("400","不合法用户");
        }
    }

    public PageDTO<BlackListRspDTO> getBlackLists(String contractNo, Integer pageNum, Integer pageSize){
        try {
            PageInfo<BlackListDO> pageInfo = this.queryListByPageAndOrder(BlackListDO.builder().contractNo(contractNo).status(String.valueOf(StatusEnum.NORMAL.getCode())).build(),pageNum,pageSize,"created_at desc");
            if (null!=pageInfo && pageInfo.getList()!=null) {
                List<BlackListRspDTO> rspDTOList = new ArrayList<>();
                PageDTO pageDTO = BaseBeanUtils.convert(pageInfo, PageDTO.class);
                pageInfo.getList().forEach(blackListDO -> {
                    BlackListRspDTO blackListRspDTO =  BaseBeanUtils.convert(blackListDO,BlackListRspDTO.class);
                    blackListRspDTO.setStatus(StatusEnum.getDesc(blackListRspDTO.getStatus()));
                    rspDTOList.add(blackListRspDTO);
                });
                pageDTO.setList(rspDTOList);
                return pageDTO;
            }
        }catch (Exception e){
            log.error("异常信息:{}",e);
        }
        return null;
    }
}
