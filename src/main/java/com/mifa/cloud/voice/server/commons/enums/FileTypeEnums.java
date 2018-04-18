package com.mifa.cloud.voice.server.commons.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * Created by Administrator on 2018/4/18.
 */
@ApiModel("文件类型枚举类")
@Getter
public enum FileTypeEnums {

    @ApiModelProperty("图片")
    IMAGE("image"),
    @ApiModelProperty("文件")
    EXCEL("excel");

    private String desc;

    FileTypeEnums(String desc){
        this.desc = desc;
    }


}
