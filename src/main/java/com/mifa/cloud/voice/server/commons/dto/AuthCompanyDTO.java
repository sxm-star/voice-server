package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Administrator on 2018/4/11.
 */
@Data
@ApiModel("企业认证入参实体类")
public class AuthCompanyDTO {

    @ApiModelProperty("用户ID")
    @NotEmpty(message = "用户ID不能为空")
    private String contractNo;

    @ApiModelProperty("企业名称")
    @NotEmpty(message = "企业名称不能为空")
    private String companyName;

    @ApiModelProperty("企业所在地址")
    @NotEmpty(message = "企业所在地址不能为空")
    private String companyAddress;

    @ApiModelProperty("经营年限")
    private String businessLife;

    @ApiModelProperty("行业")
    private String profession;

    @ApiModelProperty("官网地址")
    private String officialWebsiteUrl;

    @ApiModelProperty("营业执照号")
    @NotEmpty(message = "营业执照号不能为空")
    private String businessLicenseNo;

    @ApiModelProperty("营业执照")
    @NotEmpty(message = "营业执照地址不能为空")
    private String businessLicenseUrl;

}
