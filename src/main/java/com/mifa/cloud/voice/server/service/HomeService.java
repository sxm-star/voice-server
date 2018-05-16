package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.HomeStatisticRspDTO;
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


    public HomeStatisticRspDTO homeStatistic(String contractNo){
        AccountCapitalDO accountCapitalDO = accountCapitalService.queryOne(AccountCapitalDO.builder().contractNo(contractNo).build());
        CustomerAuthAudit authAudit = authAuditService.selectByContractNo(contractNo, null);
        HomeStatisticRspDTO homeStatisticRspDTO =  HomeStatisticRspDTO.builder().contractNo(contractNo).build();
        if (accountCapitalDO!=null){
            homeStatisticRspDTO.setAvailableAmount(String.valueOf(accountCapitalDO.getAvailableAmount()*1.0/100) +"元"); //分转元
        }else {
            homeStatisticRspDTO.setAvailableAmount("0.00元");
        }
        if (authAudit!=null){
            homeStatisticRspDTO.setAuthStatus(authAudit.getAuditStatus());
            homeStatisticRspDTO.setAuthDesc(AuthEnum.getDesc(authAudit.getAuditStatus()));
            homeStatisticRspDTO.setAuthType(AuthTypeEnum.getDesc(authAudit.getAuthType()));
            if (AuthTypeEnum.PERSON.getCode().equals(authAudit.getAuthType())){
                homeStatisticRspDTO.setCompanyName("");
            }else {
                homeStatisticRspDTO.setCompanyName(authAudit.getCustomerName());
            }

        }
        return homeStatisticRspDTO;
    }
}
