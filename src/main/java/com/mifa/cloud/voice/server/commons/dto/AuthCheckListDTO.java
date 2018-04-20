package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/4/13.
 */
@ApiModel("获得认证审核列表入参实体类")
@Data
public class AuthCheckListDTO {

    @ApiModelProperty("认证类型 1-个人认证、2-企业认证")
    private String authType;

    @ApiModelProperty("企业名称或个人姓名")
    private String customerName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("审核状态 1:认证中;2:认证通过,3:认证不通过")
    private String auditStatus;

}
