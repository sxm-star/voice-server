package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.PointDTO;
import com.mifa.cloud.voice.server.dao.AccountCapitalDetailDAO;
import com.mifa.cloud.voice.server.pojo.AccountCapitalDetailDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资金明细的流水信息记录操作
 * @author: songxm
 * @date: 2018/5/14 21:35
 * @version: v1.0.0
 */
@Service
@Slf4j
public class AccountDetailService {

    @Autowired
    AccountCapitalDetailDAO accountCapitalDetailDAO;


    /**
     * 统计管理员首页 前n天的 拨打次数
     * @param dayCnt
     * @return
     */
    public  List<PointDTO> queryTotalByDataType(@Param("dayCnt") Integer dayCnt) {
       return accountCapitalDetailDAO.queryTotalByDataType(dayCnt);
    }

    public Long queryTotalRecharge(@Param("transType") String transType){
        return  accountCapitalDetailDAO.queryTotalRecharge(transType);
    }

    public int save(AccountCapitalDetailDO accountCapitalDetailDO) {
        return accountCapitalDetailDAO.insertSelective(accountCapitalDetailDO);
    }

}
