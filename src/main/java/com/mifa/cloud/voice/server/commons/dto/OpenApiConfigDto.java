package com.mifa.cloud.voice.server.commons.dto;

import com.mifa.cloud.voice.server.commons.enums.TimeUnitEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: songxm
 * @date: 2018/5/9 16:34
 * @version: v1.0.0
 */
@ApiModel("租户的秘钥配置信息")
@Data
public class OpenApiConfigDto {

    /**
     * 客户号
     */
    @ApiModelProperty("客户号")
    @NotEmpty(message = "客户号不能为空")
    private String contractNo;


    @ApiModelProperty("失效时间单位")
    @NotNull(message = "失效时间单位不能为空")
    private TimeUnitEnum timeUnit;

    @ApiModelProperty("过期时间值")
    @NotNull(message = "失效时间过期值与 时间单位对应")
    private Integer expireValue;

    @ApiModelProperty("创建者")
    @NotEmpty(message = "创建者不能为空")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;


}
