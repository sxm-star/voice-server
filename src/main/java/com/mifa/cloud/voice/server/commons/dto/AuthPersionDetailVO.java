package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Administrator on 2018/4/11.
 */
@Data
@ApiModel("个人认证详细信息实体类")
public class AuthPersionDetailVO {

    @ApiModelProperty("用户ID")
    private String contractNo;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("身份证号码")
    private String idCard;

    @ApiModelProperty("行业")
    private String profession;

    @ApiModelProperty("身份证正面照")
    private String idCardImgUpUrl;

    @ApiModelProperty("身份证反面照")
    private String idCardImgBackUrl;

    @ApiModelProperty("手持身份证人像面照片")
    private String idCardImgHandheldUrl;

    @ApiModelProperty("认证状态")
    private String authStatus;

    @ApiModelProperty("审核不通过原因")
    private String remark;
}
