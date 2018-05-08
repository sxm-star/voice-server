package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Administrator on 2018/4/11.
 */
@Data
@ApiModel("企业认证详细信息实体类")
public class AuthCompanyDetailVO {

    @ApiModelProperty("用户ID")
    private String contractNo;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("企业所在地址")
    private String companyAddress;

    @ApiModelProperty("经营年限")
    private String businessLife;

    @ApiModelProperty("企业规模")
    private String scale;

    @ApiModelProperty("行业")
    private String profession;

    @ApiModelProperty("官网地址")
    private String officialWebsiteUrl;

    @ApiModelProperty("营业执照号")
    private String businessLicenseNo;

    @ApiModelProperty("营业执照")
    private String businessLicenseUrl;

    @ApiModelProperty("认证状态 1:认证中;2:认证通过,3:认证不通过")
    private String authStatus;

    @ApiModelProperty("审核通过、不通过原因")
    private String remark;

}
