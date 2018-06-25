package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * @author: songxm
 * @date: 2018/5/23 13:51
 * @version: v1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("黑名单实体信息")
public class BlackListRspDTO {

    private Long id;

    /**
     * 客户ID
     */
    private String contractNo;

    /**
     * 手机号
     */
    @NotEmpty
    private String value;

    /**
     * 名单类型：black代表黑名单；gray代表灰名单；white代表白名单
     */
    private String type;

    /**
     * 标签值 phone
     */
    private String tag;

    /**
     * 黑名单用户姓名等
     */
    private String name;

    /**
     * 状态：1代表启用  0代表禁用
     */
    private String status;

    /**
     * 备注详情
     */
    private String comments;

    /**
     * 名单来源，例如内部等
     */
    private String source;

    private Date createdAt;
}
