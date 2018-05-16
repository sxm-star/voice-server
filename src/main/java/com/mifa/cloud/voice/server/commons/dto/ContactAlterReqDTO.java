package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: songxm
 * @date: 2018/4/19 14:01
 * @version: v1.0.0
 */
@ApiModel("号码修改实体信息")
@Data
public class ContactAlterReqDTO {

    @ApiModelProperty(value = "单个号码ID号",required = true)
    @NotNull(message = "单个号码ID号不能为空")
    private Long id;
    /**
     * 租户号
     */
    @NotEmpty
    @ApiModelProperty(value = "租户号",required = true)
    private String contractNo;

    @ApiModelProperty("组内批次ID号")
    @NotEmpty(message = "组内批次ID号不能为空")
    private String taskId;

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

    /**
     * 用户地址
     */
    @ApiModelProperty("用户地址")
    private String userAddress;

    /**
     * 用户职位
     */
    @ApiModelProperty("用户职位")
    private String userJob;

    /**
     * M:男性; W:女性; N:未知
     */
    private String userSex;
}
