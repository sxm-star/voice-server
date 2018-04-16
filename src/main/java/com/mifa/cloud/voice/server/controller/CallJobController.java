package com.mifa.cloud.voice.server.controller;

import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.CustomerCallJobDto;
import com.mifa.cloud.voice.server.service.CallJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: songxm
 * @date: 2018/4/16 19:16
 * @version: v1.0.0
 */
@RestController
@CrossOrigin
@Api(value = "拨打任务管理相关API.", tags = "拨打任务管理相关API", description = "拨打任务管理")
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class CallJobController {
    @Autowired
    CallJobService callJobService;

    @ApiOperation("拨打任务添加")
    @RequestMapping(value = "/call-job",method = RequestMethod.POST)
    @ResponseBody
    @Loggable(descp = "拨打任务添加")
    public CommonResponse<Boolean> addCallJob(@RequestBody @Valid CustomerCallJobDto customerCallJobDto){

        return  CommonResponse.successCommonResponse(callJobService.addCallJob(customerCallJobDto));
    }
}
