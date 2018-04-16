package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/4/12 15:17
 * @version: v1.0.0
 */
@Data
@ApiModel("通讯录")
public class ContactDto {

    private String taskId;
    /**
     * 租户号
     */
    @NotEmpty
    private String contractNo;

    /**
     * 用户手机号加密处理
     */
    @NotEmpty
    private String userPhone;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * M:男性; W:女性; N:未知
     */
    private String userSex;

    /**
     * 机构(公司)名字
     */
    @NotEmpty
    private String orgName;

    /**
     * 地址
     */
    private String userAddress;

    /**
     * 用户职位
     */
    private String userJob;

}
