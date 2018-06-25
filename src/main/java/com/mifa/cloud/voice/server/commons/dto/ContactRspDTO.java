package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * @author: songxm
 * @date: 2018/4/19 13:28
 * @version: v1.0.0
 */
@ApiModel("号码响应")
@Data
public class ContactRspDTO {
    @ApiModelProperty("单个号码的ID号")
    private Long id;
    /**
     * 租户号
     */
    @NotEmpty
    @ApiModelProperty("租户号")
    private String contractNo;

    /**
     * 用户手机号加密处理
     */
    @NotEmpty
    @ApiModelProperty("用户手机号加密处理")
    private String userPhone;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;

    /**
     * M:男性; W:女性; N:未知
     */
    @ApiModelProperty("男; 女; 未知")
    private String userSex;

    /**
     * 机构(公司)名字
     */
    @NotEmpty
    @ApiModelProperty("机构(公司)名字")
    private String orgName;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String userAddress;

    /**
     * 用户职位
     */
    @ApiModelProperty("用户职位")
    private String userJob;

    @ApiModelProperty("创建时间")
    private Date createdAt;

}
