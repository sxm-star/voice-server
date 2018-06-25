package com.mifa.cloud.voice.server.controller;

import com.alibaba.fastjson.JSON;
import com.mifa.cloud.voice.server.api.jx.dto.CallBackV2Dto;
import com.mifa.cloud.voice.server.api.jx.enums.NotifyEnum;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.enums.CallFlagEnum;
import com.mifa.cloud.voice.server.commons.enums.ChannelEnum;
import com.mifa.cloud.voice.server.pojo.CustomerTaskCallDetailDO;
import com.mifa.cloud.voice.server.pojo.VoiceCheckBillLogDO;
import com.mifa.cloud.voice.server.pojo.VoiceNotifyLogDO;
import com.mifa.cloud.voice.server.service.CustomerCallStatisticsService;
import com.mifa.cloud.voice.server.service.CustomerTaskCallDetailService;
import com.mifa.cloud.voice.server.service.VoiceCheckBillService;
import com.mifa.cloud.voice.server.service.VoiceNotifyLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.mifa.cloud.voice.server.api.jx.enums.NotifyEnum.CDR;

/**
 * @author: songxm
 * @date: 2018/4/27 16:35
 * @version: v1.0.0
 */
@RestController
@CrossOrigin
@Api(value = "三方回调相关API.", tags = "三方回调相关API", description = "回调执行")
@RequestMapping(AppConst.BASE_PATH + "v1")
@Slf4j
public class CallBackController {

    @Autowired
    CustomerTaskCallDetailService taskCallDetailService;
    @Autowired
    VoiceNotifyLogService voiceNotifyLogService;
    @Autowired
    VoiceCheckBillService voiceCheckBillService;
    @Autowired
    CustomerCallStatisticsService customerCallStatisticsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @ApiOperation("即信语音通知回调开始")
    @RequestMapping(value = "/jx/call-back", method = RequestMethod.POST)
    @ResponseBody
    public void callBack(@RequestBody CallBackV2Dto callBackDto) {
        log.info("回调通知过程:" + NotifyEnum.getEnum(callBackDto.getNotify()).getDesc() + "--" + JSON.toJSONString(callBackDto));
        String data = callBackDto.getData();
        VoiceNotifyLogDO voiceNotifyLogDO;
        VoiceCheckBillLogDO voiceCheckBillLogDO;

        String[] meta = data.split("\\|");
        voiceNotifyLogDO = VoiceNotifyLogDO.builder().notify(NotifyEnum.getEnum(callBackDto.getNotify()).getDesc()).called(callBackDto.getSubject().getCalled()).callResponse(JSON.toJSONString(callBackDto)).channel(ChannelEnum.JIXIN.getName()).data(meta[1]).contractNo(meta[0]).build();
        voiceCheckBillLogDO = VoiceCheckBillLogDO.builder().notify(NotifyEnum.getEnum(callBackDto.getNotify()).getDesc()).called(callBackDto.getSubject().getCalled()).channel(ChannelEnum.JIXIN.getName()).contractNo(meta[0]).data(meta[1]).build();

        voiceNotifyLogDO.setCreatedBy(voiceNotifyLogDO.getContractNo());
        voiceNotifyLogDO.setUpdatedBy(voiceNotifyLogDO.getContractNo());
        voiceCheckBillLogDO.setCreatedBy(voiceCheckBillLogDO.getContractNo());
        voiceCheckBillLogDO.setUpdatedBy(voiceCheckBillLogDO.getContractNo());
        voiceNotifyLogService.save(voiceNotifyLogDO);
        NotifyEnum notifyEnum = NotifyEnum.getEnum(callBackDto.getNotify());
        switch (notifyEnum) {
            case CDR: {
                log.info("进入计费:code:{},desc:{}", CDR.getCode(), CDR.getDesc());
                //元转分
                int cost = Double.valueOf(callBackDto.getSubject().getCost() * 100).intValue();
                if (cost == 0) {
                    return;
                }
                voiceCheckBillLogDO.setCost(cost);
                voiceCheckBillLogDO.setDuration(callBackDto.getSubject().getDuration());
                voiceCheckBillService.save(voiceCheckBillLogDO);

                CustomerTaskCallDetailDO taskCallDetailDO = new CustomerTaskCallDetailDO();

                taskCallDetailDO.setContractNo(meta[0]);
                taskCallDetailDO.setTaskId(voiceCheckBillLogDO.getData());
                taskCallDetailDO.setBatchId(meta[2]);
                taskCallDetailDO.setPhone(voiceCheckBillLogDO.getCalled());
                CustomerTaskCallDetailDO updateTaskCallDO = taskCallDetailService.queryOne(taskCallDetailDO);
                if (updateTaskCallDO == null) {
                    log.warn("更新拨打计划为空updateTaskCallDO :{}", updateTaskCallDO);
                    return;
                }
                updateTaskCallDO.setCallFlag(CallFlagEnum.HAS_CALLED.getCode());
                Long startTime = callBackDto.getSubject().getAnswerTime() == null ? 0 : Long.parseLong(callBackDto.getSubject().getAnswerTime());
                Long endTime = callBackDto.getSubject().getReleaseTime() == null ? 0 : Long.parseLong(callBackDto.getSubject().getReleaseTime());
                Integer diffCost = Long.valueOf((endTime - startTime) / 1000).intValue();
                updateTaskCallDO.setCallTime(diffCost);
                taskCallDetailService.updateByIdSelective(updateTaskCallDO);
                log.info("拨打状态结束更新 :{}", updateTaskCallDO);
                rabbitTemplate.convertAndSend("q_voice_account_notify", callBackDto);
            }
            break;
            case CALLEND: {

                CustomerTaskCallDetailDO taskCallDetailDO = new CustomerTaskCallDetailDO();
                taskCallDetailDO.setContractNo(meta[0]);
                taskCallDetailDO.setBatchId(meta[2]);
                taskCallDetailDO.setTaskId(voiceCheckBillLogDO.getData());
                taskCallDetailDO.setPhone(voiceCheckBillLogDO.getCalled());
                CustomerTaskCallDetailDO updateTaskCallDO = taskCallDetailService.queryOne(taskCallDetailDO);
                if (callBackDto.getSubject().getCause() == 0 && callBackDto.getSubject().getDisposition().equalsIgnoreCase("recv_refuse")) {
                    if (updateTaskCallDO == null) {
                        log.warn("更新拨打计划为空updateTaskCallDO :{}", updateTaskCallDO);
                        return;
                    }
                    updateTaskCallDO.setCallFlag(CallFlagEnum.NO_CALLED.getCode());
                    updateTaskCallDO.setCallTime(0);
                    log.info("拨打状态结束更新 :{}", updateTaskCallDO);

                }else {
                    updateTaskCallDO.setCallFlag(CallFlagEnum.HAS_CALLED.getCode());
                }
                taskCallDetailService.updateByIdSelective(updateTaskCallDO);
            }
            break;
            default:
                log.warn("通知结果 类型:{}", NotifyEnum.getEnum(callBackDto.getNotify()).getDesc());
                break;
        }

    }

}
