package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.commons.enums.AccountTransTypeEnum;
import com.mifa.cloud.voice.server.commons.enums.AuthEnum;
import com.mifa.cloud.voice.server.commons.enums.AuthTypeEnum;
import com.mifa.cloud.voice.server.pojo.AccountCapitalDO;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAudit;
import com.mifa.cloud.voice.server.pojo.CustomerCallStatisticsDO;
import com.mifa.cloud.voice.server.pojo.CustomerExperienceDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.BaseDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    @Autowired
    CustomerCallStatisticsService customerCallStatisticsService;
    @Autowired
    CustomerExperienceService customerExperienceService;
    @Autowired
    AccountDetailService accountCapitalDetailService;


    public HomeStatisticRspDTO homeStatistic(String contractNo,String dataTime){
        AccountCapitalDO accountCapitalDO = accountCapitalService.queryOne(AccountCapitalDO.builder().contractNo(contractNo).build());
        CustomerAuthAudit authAudit = authAuditService.selectByContractNo(contractNo, null);
        HomeStatisticRspDTO homeStatisticRspDTO =  HomeStatisticRspDTO.builder().contractNo(contractNo).build();
        Date date = new Date();
        if (StringUtils.isNotEmpty(dataTime)){
            date = BaseDateUtils.parseDate(dataTime);
        }
        if (accountCapitalDO!=null){
            homeStatisticRspDTO.setAvailableAmount(String.valueOf(accountCapitalDO.getAvailableAmount()*1.0/100)); //分转元

        }else {
            homeStatisticRspDTO.setAvailableAmount("0.00");
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
            CustomerCallStatisticsDO customerCallStatisticsDO =  customerCallStatisticsService.queryOneByContactNoAndCreateAt(contractNo, BaseDateUtils.getDayStart(date),BaseDateUtils.getDayEnd(date));
            CallCollectDTO callCollectDTO = null;
            if (null!=customerCallStatisticsDO){
                callCollectDTO =  BaseBeanUtils.convert(customerCallStatisticsDO,CallCollectDTO.class);
            }

            homeStatisticRspDTO.setCallCollect(callCollectDTO!=null?callCollectDTO:CallCollectDTO.builder().callTime(0L).calledCnt(0L).noCalledCnt(0L).build());
        }else {
            homeStatisticRspDTO.setCallCollect(CallCollectDTO.builder().callTime(0L).calledCnt(0L).noCalledCnt(0L).build());
        }
        CustomerExperienceDO customerExperienceDO =  customerExperienceService.queryOne(CustomerExperienceDO.builder().contractNo(contractNo).build());
        VoiceExperienceDTO voiceExperienceDTO;
        if (customerExperienceDO!=null){
            voiceExperienceDTO  =  BaseBeanUtils.convert(customerExperienceDO, VoiceExperienceDTO.class);
            voiceExperienceDTO.setLeftCnt(voiceExperienceDTO.getExperienceTotalCnt()-voiceExperienceDTO.getCurrentCnt());
        }else {
            voiceExperienceDTO = VoiceExperienceDTO.builder().experienceTotalCnt(0).currentCnt(0).leftCnt(0).templateId("").content("").build();
        }
        homeStatisticRspDTO.setVoiceExperience(voiceExperienceDTO);
        return homeStatisticRspDTO;
    }

   public SystemHomeStatisticRspDTO sysHomeStatistic(String contractNo,DateTypeEnum dateTypeEnum){
       List<PointDTO> list = accountCapitalDetailService.queryTotalByDataType(dateTypeEnum.getDay());
       Long recharge =  accountCapitalDetailService.queryTotalRecharge(AccountTransTypeEnum.RECHARGE.getDesc());
       Long cost =  accountCapitalDetailService.queryTotalRecharge(AccountTransTypeEnum.COST.getDesc());
       switch (dateTypeEnum){
           case DAY:
           {
               return SystemHomeStatisticRspDTO.builder().callMoney(cost==null?0.0D:cost*1.0/100).recharge(recharge==null?0.0D:recharge*1.0/100).charts(list).build();
           }
           case MONTH:

               return SystemHomeStatisticRspDTO.builder().callMoney(cost==null?0.0D:cost*1.0/100).recharge(recharge==null?0.0D:recharge*1.0/100).charts(list).build();
           default:
               {
                   return SystemHomeStatisticRspDTO.builder().callMoney(cost==null?0.0D:cost*1.0/100).recharge(recharge==null?0.0D:recharge*1.0/100).charts(list).build();
               }
       }

   }
}
