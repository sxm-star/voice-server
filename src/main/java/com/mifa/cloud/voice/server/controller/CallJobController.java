package com.mifa.cloud.voice.server.controller;

import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.ContactGroupRspDto;
import com.mifa.cloud.voice.server.commons.dto.CustomerCallJobDto;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.service.CallJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiOperation(value = "任务列表查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
            ,  @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string",value = "客户号"),
            @ApiImplicitParam(paramType = "query", name = "jobName", required = false, dataType = "string",value = "计划名"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", required = true, dataType = "int", value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页条数"),
    })
    @RequestMapping(value = "/call-job-list",method = RequestMethod.GET)
    @Loggable(descp = "任务列表查询")
    public CommonResponse<PageDto<ContactGroupRspDto>> queryJobList(@RequestParam(required = true)String contractNo, @RequestParam(required = false) String jobName, @RequestParam(required = true, defaultValue = "1") Integer pageNum, @RequestParam(required = true,defaultValue = "10") Integer pageSize){
        return CommonResponse.successCommonResponse(callJobService.queryCallJobList(contractNo,jobName,pageNum,pageSize));
    }
}
