package com.mifa.cloud.voice.server.controller.user;

import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.dto.UserInfoVO;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.service.CustomerLoginInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2018/4/11.
 */

@RestController
@Api(value = "用户信息管理", description = "用户信息管理", produces = MediaType.APPLICATION_JSON)
@Slf4j
public class UserInfoController {

    @Autowired
    private CustomerLoginInfoService infoService;



    @PostMapping("/user_info/get_user_info/{contractNo}")
    @ApiOperation(value = "获得用户信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    public UserInfoVO getUserInfo(@PathVariable("contractNo") String contractNo) {

        CustomerLoginInfo customerInfo = infoService.selectByPrimaryKey(contractNo);
        if(customerInfo == null) {
            //return CommonResponse.failCommonResponse("用户不存在");
        }

        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(customerInfo, vo);

        /** TODO 获取用户认证信息*/


        //return CommonResponse.successCommonResponse(vo);
        return vo;
    }
































}
