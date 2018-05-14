package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.HomeStatisticRspDto;
import com.mifa.cloud.voice.server.commons.enums.AuthEnum;
import com.mifa.cloud.voice.server.commons.enums.AuthTypeEnum;
import com.mifa.cloud.voice.server.pojo.AccountCapitalDO;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAudit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SAAS 语音首页统计信息
 * @author: songxm
 * @date: 2018/5/14 19:14
 * @version: v1.0.0
 */
@Service
@Slf4j
public class HomeService {

    @Autowired
    AccountCapitalService accountCapitalService;
    @Autowired
    CustomerLoginInfoService loginInfoService;
    @Autowired
    CustomerAuthAuditService authAuditService;


    public HomeStatisticRspDto homeStatistic(String contractNo){
        AccountCapitalDO accountCapitalDO = accountCapitalService.queryOne(AccountCapitalDO.builder().contractNo(contractNo).build());
        CustomerAuthAudit authAudit = authAuditService.selectByContractNo(contractNo, null);
        HomeStatisticRspDto homeStatisticRspDto =  HomeStatisticRspDto.builder().contractNo(contractNo).build();
        if (accountCapitalDO!=null){
            homeStatisticRspDto.setAvailableAmount(accountCapitalDO.getAvailableAmount()/100); //分转元
        }
        if (authAudit!=null){
            homeStatisticRspDto.setAuthStatus(authAudit.getAuditStatus());
            homeStatisticRspDto.setAuthDesc(AuthEnum.getDesc(authAudit.getAuditStatus()));
            homeStatisticRspDto.setAuthType(AuthTypeEnum.getDesc(authAudit.getAuthType()));
            if (AuthTypeEnum.PERSON.getCode().equals(authAudit.getAuthType())){
                homeStatisticRspDto.setCompanyName("");
            }else {
                homeStatisticRspDto.setCompanyName(authAudit.getCustomerName());
            }

        }
        return homeStatisticRspDto;
    }
}
