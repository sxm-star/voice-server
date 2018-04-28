package com.mifa.cloud.voice.server.controller;

import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.annotation.AuthQScope;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.commons.enums.AuthQRole;
import com.mifa.cloud.voice.server.service.CallJobService;
import com.mifa.cloud.voice.server.service.CustomerTaskCallDetailService;
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
    @Autowired
    CustomerTaskCallDetailService taskCallDetailService;

    @ApiOperation("拨打任务添加")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @RequestMapping(value = "/call-job", method = RequestMethod.POST)
    @ResponseBody
    @Loggable(descp = "拨打任务添加")
    @AuthQScope(AuthQRole.QIU_SERVICE)
    public CommonResponse<Boolean> addCallJob(@RequestBody @Valid CustomerCallJobDto customerCallJobDto) {

        return CommonResponse.successCommonResponse(callJobService.addCallJob(customerCallJobDto));
    }



    @ApiOperation("拨打任务修改")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @RequestMapping(value = "/call-job", method = RequestMethod.PUT)
    @ResponseBody
    @Loggable(descp = "拨打任务修改")
    @AuthQScope(AuthQRole.QIU_SERVICE)
    public CommonResponse<Boolean> alterCallJob(@RequestBody @Valid CustomerCallJobDto customerCallJobDto) {

        return CommonResponse.successCommonResponse(callJobService.addCallJob(customerCallJobDto));
    }

    @ApiOperation("单个拨打任务查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
            , @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string", value = "客户号"),
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "int", value = "计划ID号")
    })
    @RequestMapping(value = "/call-job", method = RequestMethod.GET)
    @ResponseBody
    @Loggable(descp = "单个拨打任务查询")
    @AuthQScope(AuthQRole.QIU_SERVICE)
    public CommonResponse<Boolean> queryCallJob(@RequestParam(required = true) String contractNo, @RequestParam(required = true) Integer id) {

        return CommonResponse.successCommonResponse(callJobService.queryCallJob(contractNo, id));
    }


    @ApiOperation("拨打任务删除")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
            , @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string", value = "客户号"),
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "int", value = "计划ID号")
    })
    @RequestMapping(value = "/call-job", method = RequestMethod.DELETE)
    @ResponseBody
    @Loggable(descp = "拨打任务删除")
    @AuthQScope(AuthQRole.QIU_SERVICE)
    public CommonResponse<Boolean> delCallJob(@RequestParam(required = true) String contractNo, @RequestParam(required = true) Integer id) {

        return CommonResponse.successCommonResponse(callJobService.delCallJob(contractNo, id));
    }

    @ApiOperation(value = "任务列表查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
            , @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string", value = "客户号"),
            @ApiImplicitParam(paramType = "query", name = "jobName", required = false, dataType = "string", value = "计划名"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", required = true, dataType = "int", value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页条数"),
    })
    @RequestMapping(value = "/call-job-list", method = RequestMethod.GET)
    @Loggable(descp = "任务列表查询")
    @AuthQScope(AuthQRole.QIU_SERVICE)
    public CommonResponse<PageDto<ContactGroupRspDto>> queryJobList(@RequestParam(required = true) String contractNo, @RequestParam(required = false) String jobName, @RequestParam(required = true, defaultValue = "1") Integer pageNum, @RequestParam(required = true, defaultValue = "10") Integer pageSize) {
        return CommonResponse.successCommonResponse(callJobService.queryCallJobList(contractNo, jobName, pageNum, pageSize));
    }


    @ApiOperation(value = "任务拨打明细列表情况查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
            , @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string", value = "客户号"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", required = true, dataType = "int", value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页条数"),
    })
    @RequestMapping(value = "/call-job-detail-list", method = RequestMethod.GET)
    @Loggable(descp = "任务拨打明细列表情况查询")
    @AuthQScope(AuthQRole.QIU_SERVICE)
    public CommonResponse<PageDto<CustomerTaskCallDetailDto>> queryDetailList(@ModelAttribute @Valid CallDetailQueryDto callDetailQueryDto, @RequestParam(required = true, defaultValue = "1") Integer pageNum, @RequestParam(required = true, defaultValue = "10") Integer pageSize) {
        return CommonResponse.successCommonResponse(taskCallDetailService.queryTaskDetailList(callDetailQueryDto, pageNum, pageSize));
    }
}
