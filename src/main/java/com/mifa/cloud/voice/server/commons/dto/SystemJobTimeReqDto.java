package com.mifa.cloud.voice.server.commons.dto;

import com.mifa.cloud.voice.server.commons.enums.CallTimeTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/5/28 17:27
 * @version: v1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("系统时间段设置信息")
public class SystemJobTimeReqDto {

    /**
     * 0:工作日; 1:节假日
     */
    @ApiModelProperty("日期类型设置")
    @NotNull(message = "日期类型设置不能为空")
    private CallTimeTypeEnum callTimeTypeEnum;

    /**
     * 允许时间段开始时间段 hh:ss
     */
    @ApiModelProperty(value = "允许时间段列表")
    @NotNull(message = "时间设置不能为空")
    private List<CallTimeReqDTO> allowTimeRangs;


    @ApiModelProperty("创建人")
    @NotEmpty(message = "创建人不能为空")
    private String createdBy;
}
