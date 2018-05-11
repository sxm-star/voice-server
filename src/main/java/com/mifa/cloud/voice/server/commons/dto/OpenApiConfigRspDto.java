package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author: songxm
 * @date: 2018/5/10 11:10
 * @version: v1.0.0
 */
@Data
@ApiModel("配置openAPI token详情信息")
public class OpenApiConfigRspDto {

    private Long id;

    /**
     * 客户号
     */
    private String contractNo;

    /**
     * 鉴权token
     */
    private String authToken;

    /**
     * 过期时间
     */
    private Date authExpire;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy;
}
