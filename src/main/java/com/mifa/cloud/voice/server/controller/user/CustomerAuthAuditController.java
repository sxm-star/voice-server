package com.mifa.cloud.voice.server.controller.user;

import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.commons.dto.AuthCheckListDTO;
import com.mifa.cloud.voice.server.commons.dto.CustomerAuthAuditVO;
import com.mifa.cloud.voice.server.service.CustomerAuthAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2018/4/13.
 */
@RestController
@Api(value = "认证审核相关", description = "认证审核相关", produces = MediaType.APPLICATION_JSON)
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class CustomerAuthAuditController {

    @Autowired
    private CustomerAuthAuditService customerAuthAuditService;

    @PostMapping("/authAudit-list")
    @ApiOperation(value = "获得认证审核列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "获得认证审核列表")
    public CommonResponse<PageDto<CustomerAuthAuditVO>> getAuthCheckList(@RequestBody(required = false) AuthCheckListDTO param,
                                                                         @RequestParam(required = true, defaultValue = "1") Integer pageNum,
                                                                         @RequestParam(required = true, defaultValue = "10") Integer pageSize) {
        PageDto<CustomerAuthAuditVO> pageDto = customerAuthAuditService.selectAuthCheckList(param, pageNum, pageSize);
        return CommonResponse.successCommonResponse(pageDto);
    }


}
