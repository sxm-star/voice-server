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
import com.mifa.cloud.voice.server.service.CustomerTaskCallDetailService;
import com.mifa.cloud.voice.server.service.VoiceCheckBillService;
import com.mifa.cloud.voice.server.service.VoiceNotifyLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("即信语音通知回调开始")
    @RequestMapping(value = "/jx/call-back", method = RequestMethod.POST)
    @ResponseBody
    public void callBack(@RequestBody CallBackV2Dto callBackDto) {
        log.info(NotifyEnum.getEnum(callBackDto.getNotify())+ "--" + JSON.toJSONString(callBackDto));
        String data = callBackDto.getData();
        VoiceNotifyLogDO voiceNotifyLogDO;
        VoiceCheckBillLogDO voiceCheckBillLogDO;
        if (data.contains("|")){
          String[]  meta = data.split("|");
            voiceNotifyLogDO = VoiceNotifyLogDO.builder().notify(NotifyEnum.getEnum(callBackDto.getNotify()).getDesc()).called(callBackDto.getSubject().getCalled()).callResponse(JSON.toJSONString(callBackDto)).channel(ChannelEnum.JIXIN.getName()).data(meta[1]).contractNo(meta[0]).build();
            voiceCheckBillLogDO = VoiceCheckBillLogDO.builder().notify(NotifyEnum.getEnum(callBackDto.getNotify()).getDesc()).called(callBackDto.getSubject().getCalled()).channel(ChannelEnum.JIXIN.getName()).contractNo(meta[0]).data(meta[1]).build();
        }else {
            voiceNotifyLogDO =  VoiceNotifyLogDO.builder().notify(NotifyEnum.getEnum(callBackDto.getNotify()).getDesc()).called(callBackDto.getSubject().getCalled()).callResponse(JSON.toJSONString(callBackDto)).channel(ChannelEnum.JIXIN.getName()).data(data).contractNo(callBackDto.getSubject().getCalled()).build();
            voiceCheckBillLogDO = VoiceCheckBillLogDO.builder().notify(NotifyEnum.getEnum(callBackDto.getNotify()).getDesc()).called(callBackDto.getSubject().getCalled()).channel(ChannelEnum.JIXIN.getName()).contractNo(callBackDto.getSubject().getCalled()).data(data).build();
        }
        voiceNotifyLogDO.setCreatedBy(voiceNotifyLogDO.getContractNo());
        voiceNotifyLogDO.setUpdatedBy(voiceNotifyLogDO.getContractNo());
        voiceCheckBillLogDO.setCreatedBy(voiceCheckBillLogDO.getContractNo());
        voiceCheckBillLogDO.setUpdatedBy(voiceCheckBillLogDO.getContractNo());
        voiceNotifyLogService.save(voiceNotifyLogDO);
        switch (NotifyEnum.getEnum(callBackDto.getNotify())) {
            case CDR: {
                //元转分
                voiceCheckBillLogDO.setCost(Double.valueOf(callBackDto.getSubject().getCost()*100).intValue());
                voiceCheckBillLogDO.setDuration(callBackDto.getSubject().getDuration());
                voiceCheckBillService.save(voiceCheckBillLogDO);

                 CustomerTaskCallDetailDO taskCallDetailDO = new CustomerTaskCallDetailDO();
                 taskCallDetailDO.setTaskId(voiceCheckBillLogDO.getData());
                 taskCallDetailDO.setPhone(voiceCheckBillLogDO.getCalled());
                 taskCallDetailDO.setCallFlag(CallFlagEnum.HAS_CALLED.getCode());
                 CustomerTaskCallDetailDO updateTaskCallDO =  taskCallDetailService.queryOne(taskCallDetailDO);
                 taskCallDetailService.updateByIdSelective(updateTaskCallDO);
            }
            break;
            default:
                log.warn("通知结果 未知类型");
                break;
        }

    }

}
