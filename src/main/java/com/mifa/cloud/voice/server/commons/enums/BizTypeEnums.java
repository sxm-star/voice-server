package com.mifa.cloud.voice.server.commons.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * Created by Administrator on 2018/4/18.
 */
@ApiModel("业务类型枚举类")
@Getter
public enum BizTypeEnums {

    @ApiModelProperty("头像")
    AVATAR,

    @ApiModelProperty("营业执照")
    BUSINESS_LICENSE,

    @ApiModelProperty("身份证")
    ID_CARD_IMG,

    @ApiModelProperty("语音模板")
    VOICE_TEMPLATE

}
