package com.mifa.cloud.voice.server.controller.user;

import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.pojo.CustomerAauthPerson;
import com.mifa.cloud.voice.server.pojo.CustomerAuthCompany;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.pojo.SystemKeyValue;
import com.mifa.cloud.voice.server.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2018/4/11.
 */
@RestController
@Api(value = "用户认证", description = "用户认证", produces = MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
@CrossOrigin
public class UserAuthController {

    @Autowired
    private CustomerAauthPersonService customerAauthPersonService;

    @Autowired
    private CustomerAuthCompanyService customerAuthCompanyService;

    @Autowired
    private CustomerLoginInfoService infoService;

    @Autowired
    private CustomerAuthAuditService customerAuthAuditService;

    @Autowired
    private SystemKeyValueService systemKeyValueService;

    @Autowired
    private ConstConfig aconst;

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

    @GetMapping("/person/{contractNo}")
    @ApiOperation(value = "获得个人认证审核信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "获得个人认证审核信息")
    public CommonResponse<AuthPersionDetailVO> getPersonAuthDetail(@PathVariable("contractNo") String contractNo) {
        CustomerAauthPerson authPersonDetail = customerAauthPersonService.selectByPrimaryKey(contractNo);
        AuthPersionDetailVO vo = new AuthPersionDetailVO();
        if(authPersonDetail != null) {
            SystemKeyValue systemKeyValue = systemKeyValueService.selectByKey(authPersonDetail.getProfession());
            authPersonDetail.setProfession(systemKeyValue.getParamValue());
            authPersonDetail.setIdCardImgBackUrl(StringUtils.isNoneEmpty(authPersonDetail.getIdCardImgBackUrl()) ? aconst.H5_URL_PATH + authPersonDetail.getIdCardImgBackUrl() : "");
            authPersonDetail.setIdCardImgHandheldUrl(StringUtils.isNoneEmpty(authPersonDetail.getIdCardImgHandheldUrl()) ? aconst.H5_URL_PATH + authPersonDetail.getIdCardImgHandheldUrl() : "");
            authPersonDetail.setIdCardImgUpUrl(StringUtils.isNoneEmpty(authPersonDetail.getIdCardImgUpUrl()) ? aconst.H5_URL_PATH + authPersonDetail.getIdCardImgUpUrl() : "");
            BeanUtils.copyProperties(authPersonDetail, vo);
        }
        return CommonResponse.successCommonResponse(vo);
    }

    @GetMapping("/company/{contractNo}")
    @ApiOperation(value = "获得企业认证审核信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    @Loggable(descp = "获得企业认证审核信息")
    public CommonResponse<AuthCompanyDetailVO> getCompanyAuthDetail(@PathVariable("contractNo") String contractNo) {
        CustomerAuthCompany authCompanyDetail = customerAuthCompanyService.selectByPrimaryKey(contractNo);
        AuthCompanyDetailVO vo = new AuthCompanyDetailVO();
        if(authCompanyDetail != null) {
            // 此处可改为从枚举中获得
            SystemKeyValue profession = systemKeyValueService.selectByKey(authCompanyDetail.getProfession());
            SystemKeyValue scale = systemKeyValueService.selectByKey(authCompanyDetail.getScale());
            SystemKeyValue businessLife = systemKeyValueService.selectByKey(authCompanyDetail.getBusinessLife());
            authCompanyDetail.setProfession(profession.getParamValue());
            authCompanyDetail.setScale(scale.getParamValue());
            authCompanyDetail.setBusinessLife(businessLife.getParamValue());
            authCompanyDetail.setBusinessLicenseUrl(StringUtils.isNotEmpty(authCompanyDetail.getBusinessLicenseUrl()) ? aconst.H5_URL_PATH + authCompanyDetail.getBusinessLicenseUrl() : "");
            BeanUtils.copyProperties(authCompanyDetail, vo);
        }
        return CommonResponse.successCommonResponse(vo);
    }


}
