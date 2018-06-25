package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Administrator on 2018/4/11.
 */
@Data
@ApiModel("认证审核入参实体类")
public class AuthCheckDTO {

    @ApiModelProperty("客户ID")
    @NotEmpty(message = "客户ID不能为空")
    private String contractNo;

    @ApiModelProperty("认证状态；2:认证通过,3:认证不通过")
    @NotEmpty(message = "认证状态不能为空")
    private String authStatus;

    @ApiModelProperty("审核不通过原因")
    private String remark;

    @ApiModelProperty("审核人")
    private String updateBy;

}
