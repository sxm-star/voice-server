package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Administrator on 2018/4/11.
 */
@Data
@ApiModel("个人认证入参实体类")
public class AuthPersionDTO {


    @ApiModelProperty("用户ID")
    @NotEmpty(message = "用户ID不能为空")
    private String contractNo;

    @ApiModelProperty("姓名")
    @NotEmpty(message = "姓名不能为空")
    private String name;

    @ApiModelProperty("身份证号码")
    @NotEmpty(message = "身份证号码不能为空")
    private String idCard;


    @ApiModelProperty("行业")
    private String profession;

    @ApiModelProperty("身份证正面照")
    @NotEmpty(message = "身份证正面照不能为空")
    private String idCardImgUpUrl;

    @ApiModelProperty("身份证反面照")
    @NotEmpty(message = "身份证反面照不能为空")
    private String idCardImgBackUrl;

    @ApiModelProperty("手持身份证人像面照片")
    @NotEmpty(message = "手持身份证人像面照片不能为空")
    private String idCardImgHandheldUrl;

}
