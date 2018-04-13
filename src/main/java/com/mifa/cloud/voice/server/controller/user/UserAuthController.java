package com.mifa.cloud.voice.server.controller.user;

import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.dto.AuthCheckDTO;
import com.mifa.cloud.voice.server.dto.AuthCompanyDTO;
import com.mifa.cloud.voice.server.dto.AuthPersionDTO;
import com.mifa.cloud.voice.server.pojo.CustomerAauthPerson;
import com.mifa.cloud.voice.server.pojo.CustomerAuthAudit;
import com.mifa.cloud.voice.server.pojo.CustomerAuthCompany;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.service.CustomerAauthPersonService;
import com.mifa.cloud.voice.server.service.CustomerAuthAuditService;
import com.mifa.cloud.voice.server.service.CustomerAuthCompanyService;
import com.mifa.cloud.voice.server.service.CustomerLoginInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/11.
 */
@RestController
@Api(value = "用户认证", description = "用户认证", produces = MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class UserAuthController {

    @Autowired
    private CustomerAauthPersonService customerAauthPersonService;

    @Autowired
    private CustomerAuthCompanyService customerAuthCompanyService;

    @Autowired
    private CustomerLoginInfoService infoService;

    @Autowired
    private CustomerAuthAuditService customerAuthAuditService;

    @PostMapping("/person")
    @ApiOperation(value = "个人认证")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    paramType = "header",
                    name = HttpHeaders.AUTHORIZATION,
                    required = true,
                    value = "service token",
                    dataType = "string")
    })
    @Loggable(descp = "个人认证")
    public CommonResponse<Void> authPersion(@RequestBody @Valid AuthPersionDTO param) {

        CustomerAauthPerson customerAauthPerson = customerAauthPersonService.selectByPrimaryKey(param.getContractNo());
        if (customerAauthPerson != null) {
            return CommonResponse.failCommonResponse("认证信息已经存在");
        }
        CustomerLoginInfo customerInfo = infoService.selectByPrimaryKey(param.getContractNo());
        if (customerInfo == null) {
            return CommonResponse.failCommonResponse("用户不存在");
        }

        int count = customerAauthPersonService.submitAuthInfo(param, customerInfo);
        if (count > 0) {
            return CommonResponse.successCommonResponse("认证信息提交成功", null);
        }
        return CommonResponse.failCommonResponse("认证信息提交失败");

    }

    @PostMapping("/company")
    @ApiOperation(value = "企业认证")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "企业认证")
    public CommonResponse<Void> authCompany(@RequestBody @Valid AuthCompanyDTO param) {
        CustomerAuthCompany customerAuthCompany = customerAuthCompanyService.selectByPrimaryKey(param.getContractNo());
        if (customerAuthCompany != null) {
            return CommonResponse.failCommonResponse("认证信息已经存在");
        }
        CustomerLoginInfo customerInfo = infoService.selectByPrimaryKey(param.getContractNo());
        if (customerInfo == null) {
            return CommonResponse.failCommonResponse("用户不存在");
        }
        int count = customerAuthCompanyService.submitAuthInfo(param, customerInfo);
        if (count > 0) {
            return CommonResponse.successCommonResponse("认证信息提交成功", null);
        }
        return CommonResponse.failCommonResponse("认证信息提交失败");

    }

    @PutMapping("/person-check/")
    @ApiOperation(value = "个人认证审核")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "个人认证审核")
    public CommonResponse<Void> authPersonCheck(@RequestBody @Valid AuthCheckDTO param) {

        CustomerAauthPerson customerAauthPerson = customerAauthPersonService.selectByPrimaryKey(param.getContractNo());
        if (customerAauthPerson == null) {
            return CommonResponse.failCommonResponse("认证信息不存在");
        }

        int count = customerAauthPersonService.authCheck(param, customerAauthPerson);
        if (count > 0) {
            return CommonResponse.successCommonResponse("审核成功", null);
        }
        return CommonResponse.failCommonResponse("审核失败");

    }

    @PutMapping("/company-check")
    @ApiOperation(value = "企业认证审核")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "企业认证审核")
    public CommonResponse<Void> authCompanyCheck(@RequestBody @Valid AuthCheckDTO param) {

        CustomerAuthCompany customerAuthCompany = customerAuthCompanyService.selectByPrimaryKey(param.getContractNo());
        if (customerAuthCompany == null) {
            return CommonResponse.failCommonResponse("认证信息不存在");
        }

        int count = customerAuthCompanyService.authCheck(param, customerAuthCompany);
        if (count > 0) {
            return CommonResponse.successCommonResponse("审核成功", null);
        }
        return CommonResponse.failCommonResponse("审核失败");

    }


}
