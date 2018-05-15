package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.api.jx.dto.CallBackV2Dto;
import com.mifa.cloud.voice.server.commons.enums.ChannelEnum;
import com.mifa.cloud.voice.server.pojo.AccountCapitalDO;
import com.mifa.cloud.voice.server.pojo.AccountCapitalDetailDO;
import com.mifa.cloud.voice.server.pojo.CustomerVoiceBillDO;
import com.mifa.cloud.voice.server.pojo.VoiceServiceBillRateDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: songxm
 * @date: 2018/5/14 16:17
 * @version: v1.0.0
 */
@Service
@Slf4j
public class AccountCapitalService extends BaseService<AccountCapitalDO>{
    @Autowired
    AccountDetailService accountDetailService;
    @Autowired
    VoiceServiceBillRateService rateService;
    @Autowired
    CustomerVoiceBillService customerVoiceBillService;

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void calcTenantBill(CallBackV2Dto callBackDto){

        String data = callBackDto.getData();
        String[] meta = data.split("\\|");
        String contractNo = meta[0];
        String taskId = meta[1];
        String batchId = meta[2];
        String called = callBackDto.getSubject().getCalled();

        VoiceServiceBillRateDO voiceServiceBillRateDO =  rateService.queryOne(VoiceServiceBillRateDO.builder().contractNo(contractNo).build());
        if (null==voiceServiceBillRateDO){
            log.warn("租户的费率信息没有配置");
            return;
        }

        //实际扣除租户的费用
        Integer cost = 0;
        //转分钟数
        int cnt = callBackDto.getSubject().getDuration()/60;
        int left = callBackDto.getSubject().getDuration()%60;
        if (callBackDto.getSubject().getDuration()>0 && left==0){
            cost = voiceServiceBillRateDO.getRateAmt() * cnt;
        }
        else if(callBackDto.getSubject().getDuration()>0 && left!=0){
            cost = voiceServiceBillRateDO.getRateAmt() * (cnt + 1);
        }

        //租户账单明细
        customerVoiceBillService.save(CustomerVoiceBillDO.builder().contractNo(contractNo).called(called).channel(ChannelEnum.JIXIN.getName()).notify(callBackDto.getNotify()).duration(callBackDto.getSubject().getDuration()).data(batchId).cost(cost).build());

        //租户资金账户
        AccountCapitalDO accountCapitalDO =  this.queryOne(AccountCapitalDO.builder().contractNo(contractNo).build());
        AccountCapitalDetailDO accountCapitalDetailDO =  AccountCapitalDetailDO.builder().contractNo(contractNo).accountNo(accountCapitalDO.getAccountId()).ccy("RMB").remark("语音服务消费").amount(Long.valueOf(cost+"")).transType("支出").receiptNo(System.currentTimeMillis()+"").build();
        accountCapitalDetailDO.setBeforeBal(accountCapitalDO.getAvailableAmount());
        long availableAmount = accountCapitalDO.getAvailableAmount();
        if (availableAmount > cost){
            availableAmount = availableAmount - cost;
            accountCapitalDO.setAvailableAmount(availableAmount);
        }else {
            availableAmount = availableAmount -cost;
            long creditAmount = accountCapitalDO.getCreditAmount() + availableAmount;
            //信用额度透支
            accountCapitalDO.setCreditAmount(creditAmount);
            accountCapitalDO.setAvailableAmount(0L);
        }
        accountCapitalDetailDO.setAfterBal(accountCapitalDO.getAvailableAmount());
        this.updateByIdSelective(accountCapitalDO);
        accountDetailService.save(accountCapitalDetailDO);
    }
}
