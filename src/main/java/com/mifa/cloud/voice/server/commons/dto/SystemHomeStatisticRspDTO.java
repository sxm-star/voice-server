package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: songxm
 * @date: 2018/5/28 20:02
 * @version: v1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemHomeStatisticRspDTO {
    @ApiModelProperty("图表")
    List<PointDTO> charts;

    @ApiModelProperty("充值的钱 单位元")
    Double recharge;
    @ApiModelProperty("拨打消费的总账 单位元")
    Double callMoney;
}
