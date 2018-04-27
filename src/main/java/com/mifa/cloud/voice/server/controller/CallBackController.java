package com.mifa.cloud.voice.server.controller;

import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.api.jx.dto.CallBackDto;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.enums.CallFlagEnum;
import com.mifa.cloud.voice.server.pojo.CustomerTaskCallDetailDO;
import com.mifa.cloud.voice.server.service.CustomerTaskCallDetailService;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
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

    @ApiOperation("即信语音通知回调开始")
    @RequestMapping(value = "/jx/call-back", method = RequestMethod.POST)
    @ResponseBody
    @Loggable(descp = "即信语音通知回调开始")
    public CommonResponse<Boolean> callBack(@RequestBody  CallBackDto callBackDto) {
         if (callBackDto!=null && callBackDto.getReportList()!=null)
         {
             CustomerTaskCallDetailDO taskCallDetailDO = new CustomerTaskCallDetailDO();
             callBackDto.getReportList().forEach(report->{
                 taskCallDetailDO.setTaskId(report.getExtend());
                 taskCallDetailDO.setPhone(report.getPhone());
                 String flag = report.getResult().equals("0")? CallFlagEnum.HAS_CALLED.getCode():CallFlagEnum.NO_CALLED.getCode();
                 taskCallDetailDO.setCallFlag(flag);
                 CustomerTaskCallDetailDO updateTaskCallDO =  taskCallDetailService.queryOne(taskCallDetailDO);
                 taskCallDetailService.updateByIdSelective(updateTaskCallDO);
             });
             log.info("即信语音通知 size:{}",callBackDto.getReportList().size());
             return CommonResponse.successCommonResponse();
         }

        return CommonResponse.failCommonResponse("400","回调信息体空列表:"+callBackDto.getReportList()+"", BaseStringUtils.uuid());
    }

}
