package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * @author: songxm
 * @date: 2018/4/25 17:27
 * @version: v1.0.0
 */
@Data
@ApiModel("拨打详情统计响应结果")
public class CustomerTaskCallDetailDto {
    /**
     * 主键
     */
    @ApiModelProperty("主键ID编号")
    private Integer id;

    /**
     * 客户号
     */
    @NotEmpty
    @ApiModelProperty("客户号")
    private String contractNo;

    /**
     * 语音模板id
     */
    @NotEmpty
    @ApiModelProperty("语音模板id")
    private String templateId;

    /**
     *
     */
    @ApiModelProperty("批次任务ID")
    private String taskId;

    /**
     * 手机号
     */
    @NotEmpty
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 用户姓名
     */
    @NotEmpty
    @ApiModelProperty("用户姓名")
    private String  userName;

    @ApiModelProperty("公司名称")
    private String orgName;
    /**
     * 拨打次数
     */
    @ApiModelProperty("拨打次数")
    private Integer callCnt;

    /**
     * 拨打分钟数
     */
    @ApiModelProperty("拨打分钟数")
    private Integer callTime;

    /**
     * 拨打情况 1:已拨通；2：未拨通
     */
    @NotEmpty
    @ApiModelProperty("拨打情况")
    private String callFlag;

    /**
     * 用户标签属性 1:潜在用户;2:中性用户;3:拒绝用户
     */
    @NotEmpty
    @ApiModelProperty("用户标签属性")
    private String userTag;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("创建时间")
    private Date createdAt;

}
