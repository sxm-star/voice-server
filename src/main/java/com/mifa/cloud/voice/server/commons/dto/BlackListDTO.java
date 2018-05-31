package com.mifa.cloud.voice.server.commons.dto;

import com.mifa.cloud.voice.server.commons.enums.BlackListTagEnum;
import com.mifa.cloud.voice.server.commons.enums.BlackListTypeEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: songxm
 * @date: 2018/5/23 09:51
 * @version: v1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("黑名单实体信息")
public class BlackListDTO {
    /**
     * 客户ID
     */
    @ApiModelProperty("客户号")
    @NotEmpty(message = "客户号不能为空")
    private String contractNo;

    /**
     * 手机号
     */
    @NotEmpty(message = "名单值不能为空")
    @ApiModelProperty("手机号,设备号等 与tag标签对应")
    private String value;

    /**
     * 名单类型：BLACK代表黑名单；GRAY代表灰名单；WHITE代表白名单
     */
    @ApiModelProperty("名单类型 BLACK代表黑名单；GRAY代表灰名单；WHITE代表白名单")
    private BlackListTypeEnum type;

    /**
     * 标签值 PHONE
     */
    @ApiModelProperty("标签值 PHONE")
    private BlackListTagEnum tag;

    /**
     * 黑名单用户姓名等
     */
    @ApiModelProperty(value = "黑名单用户姓名",example = "张三")
    private String name;

    /**
     * 状态：1代表启用  0代表禁用
     */
    @ApiModelProperty("名单状态值")
    private StatusEnum BlackListStatus;

    /*** 备注详情
     */
    @ApiModelProperty(value = "备注详情",example = "备注内容")
    private String comments;

    /**
     * 名单来源，例如内部等
     */
    @ApiModelProperty(value = "名单来源，例如内部等",example = "内部")
    private String source;
}
