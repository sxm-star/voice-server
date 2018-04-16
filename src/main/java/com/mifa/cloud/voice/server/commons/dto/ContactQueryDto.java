package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/4/16 11:07
 * @version: v1.0.0
 */
@Data
@ApiModel("用户查询实体信息")
public class ContactQueryDto {
    /**
     * 租户号
     */
    @NotEmpty
    @ApiModelProperty("租户号")
    private String contractNo;

    /**
     * 用户手机号加密处理
     */
    @ApiModelProperty("用户手机号")
    private String userPhone;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;


    /**
     * 机构(公司)名字
     */
    @ApiModelProperty("公司名字")
    private String orgName;
}
