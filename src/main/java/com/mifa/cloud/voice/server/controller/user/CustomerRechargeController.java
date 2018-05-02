package com.mifa.cloud.voice.server.controller.user;

import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.CustomerRechargeVO;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.service.CustomerRechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2018/4/16.
 */
@RestController
@Api(value = "用户充值记录", description = "用户充值记录", produces = MediaType.APPLICATION_JSON)
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
@CrossOrigin
public class CustomerRechargeController {


    @Autowired
    private CustomerRechargeService customerRechargeService;


    @GetMapping("/customerRecharge-list")
    @ApiOperation(value = "用户充值记录列表")
    @Loggable(descp = "用户充值记录")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    public CommonResponse<PageDto<CustomerRechargeVO>> getRechargeList(
            @RequestParam(required = false, value = "用户名") String rechargeName,
            @RequestParam(required = false, value = "用户ID") String contractNo,
            @RequestParam(defaultValue = "1", value = "页数") Integer pageNum,
            @RequestParam(defaultValue = "10", value = "每页条数") Integer pageSize) {

        return CommonResponse.successCommonResponse(customerRechargeService.selectRechargeList(rechargeName, contractNo, pageNum, pageSize));
    }

}
