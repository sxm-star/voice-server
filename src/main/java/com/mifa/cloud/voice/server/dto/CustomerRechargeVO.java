package com.mifa.cloud.voice.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/16.
 */
@ApiModel("充值记录列表实体类")
@Data
public class CustomerRechargeVO {

    @ApiModelProperty("充值流水表ID")
    private String rechargeId;

    @ApiModelProperty("充值金额 单位：分")
    private BigDecimal fee;

    @ApiModelProperty("客户的支付充值账号")
    private String payNo;

    @ApiModelProperty("客户号对应的资金账户号")
    private String accountNo;

    @ApiModelProperty("客户号(用户ID)")
    private String contractNo;

    @ApiModelProperty("充值时间")
    private Date rechargeTime;

    @ApiModelProperty("充值状态 1：成功; 0：失败")
    private String status;

    /**
     * 充值账号名称（用户名，login_info中的登陆名）
     */
    @ApiModelProperty("充值账号名称")
    private String rechargeName;

    @ApiModelProperty("充值方式")
    private String rechargeWay;

    @ApiModelProperty("充值备注 充值套餐类型说明")
    private String rechargeRemark;

    @ApiModelProperty("平台收款人的账号")
    private String sysPayeeNo;

}
