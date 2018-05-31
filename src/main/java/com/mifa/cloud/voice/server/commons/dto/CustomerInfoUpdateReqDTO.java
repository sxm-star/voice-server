package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/5/20 10:46
 * @version: v1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoUpdateReqDTO {

    /**
     * 客户号 租户ID或者用户ID
     */
    @ApiModelProperty("客户ID号")
    @NotEmpty(message = "客户ID号不能为空")
    private String contractNo;


    private String loginStatus;

    /**
     * 登录密码
     */
    private String loginPasswd;

    /**
     * 操作人
     */
    private String createdBy;
}
