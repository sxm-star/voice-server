package com.mifa.cloud.voice.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/13.
 */
@Data
@ApiModel("获得认证审核列表返回实体类")
public class CustomerAuthAuditVO {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("客户ID")
    private String contractNo;

    @ApiModelProperty("认证类型 1-个人认证、2-企业认证")
    private String authType;

    @ApiModelProperty("企业名称或个人姓名")
    private String customerName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("认证时间")
    private Date authTime;

    @ApiModelProperty("审核状态 1:认证中;2:认证通过,3:认证不通过")
    private String auditStatus;

    @ApiModelProperty("审核说明")
    private String remark;

    @ApiModelProperty("审核时间")
    private Date autitTime;

}
