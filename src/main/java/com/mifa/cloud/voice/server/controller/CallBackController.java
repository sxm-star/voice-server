package com.mifa.cloud.voice.server.controller;

import com.alibaba.fastjson.JSON;
import com.mifa.cloud.voice.server.api.jx.dto.CallBackV2Dto;
import com.mifa.cloud.voice.server.api.jx.enums.NotifyEnum;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.pojo.VoiceNotifyLogDO;
import com.mifa.cloud.voice.server.service.CustomerTaskCallDetailService;
import com.mifa.cloud.voice.server.service.VoiceNotifyLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.mifa.cloud.voice.server.api.jx.enums.NotifyEnum.*;

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

    @ApiOperation("即信语音通知回调开始")
    @RequestMapping(value = "/jx/call-back", method = RequestMethod.POST)
    @ResponseBody
    public void callBack(@RequestBody CallBackV2Dto callBackDto) {
        log.info(callBackDto.getNotify() + "开始" + NotifyEnum.getDesc(callBackDto.getNotify()));
        voiceNotifyLogService.save(VoiceNotifyLogDO.builder().notify(NotifyEnum.getEnum(callBackDto.getNotify()).getDesc()).data(callBackDto.getData()).called(callBackDto.getSubject().getCalled()).callResponse(JSON.toJSONString(callBackDto)).callTime(new Date()).build());
        switch (NotifyEnum.getEnum(callBackDto.getNotify())) {
            case CALLSTATE: {
                log.info(CALLSTATE.getDesc() + "--" + JSON.toJSONString(callBackDto));
            }
            break;
            case CALLCREATE: {
                log.info(CALLCREATE.getDesc() + "--" + JSON.toJSONString(callBackDto));
            }
            break;
            case CALLPROCESS: {
                log.info(CALLPROCESS.getDesc() + "--" + JSON.toJSONString(callBackDto));
            }
            break;
            case CALLANSWER: {
                log.info(CALLANSWER.getDesc() + "--" + JSON.toJSONString(callBackDto));
            }
            break;
            case CALLEND: {
                log.info(CALLEND.getDesc() + "--" + JSON.toJSONString(callBackDto));
            }
            break;
            case CDR: {
                log.info(CDR.getDesc() + "--" + JSON.toJSONString(callBackDto));
            }
            break;
            default:
                log.warn("通知结果 未知类型");
                break;
        }

//         if (callBackDto!=null && callBackDto.getReportList()!=null)
//         {
//             CustomerTaskCallDetailDO taskCallDetailDO = new CustomerTaskCallDetailDO();
//             callBackDto.getReportList().forEach(report->{
//                 taskCallDetailDO.setTaskId(report.getExtend());
//                 taskCallDetailDO.setPhone(report.getPhone());
//                 String flag = report.getResult().equals("0")? CallFlagEnum.HAS_CALLED.getCode():CallFlagEnum.NO_CALLED.getCode();
//                 taskCallDetailDO.setCallFlag(flag);
//                 CustomerTaskCallDetailDO updateTaskCallDO =  taskCallDetailService.queryOne(taskCallDetailDO);
//                 taskCallDetailService.updateByIdSelective(updateTaskCallDO);
//             });
//             log.info("即信语音通知 size:{}",callBackDto.getReportList().size());
//             return CommonResponse.successCommonResponse();
//         }
    }

}
