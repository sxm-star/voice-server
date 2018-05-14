package com.mifa.cloud.voice.server.controller;

import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author: songxm
 * @date: 2018/5/14 16:53
 * @version: v1.0.0
 */
@RestController
@CrossOrigin
@Api(value = "后台首页相关API.", tags = "后台首页相关API", description = "首页管理")
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class HomeController {


    @ApiOperation(value = "首页统计信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @RequestMapping(value = "/home-statistic",method = RequestMethod.GET)
    @Loggable(descp = "首页统计信息")
    public CommonResponse<Boolean> homeStatistic(@RequestParam("contactNo") String contactNo){

       // return CommonResponse.successCommonResponse(customerTaskContactGroupService.addContactGroup(contactGroupPostDto));
        return null;
    }
}
