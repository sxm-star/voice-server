package com.mifa.cloud.voice.server.commons.dto;

import com.mifa.cloud.voice.server.commons.enums.SexEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: songxm
 * @date: 2018/4/12 15:17
 * @version: v1.0.0
 */
@Data
@ApiModel("组内号码新增")
public class ContactDto {
    @ApiModelProperty("组ID号")
    @NotNull(message = "不能为空组ID号")
    private Long groupId;
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
    @ApiModelProperty("M:男性; W:女性; N:未知")
    private SexEnum userSex;

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

}
