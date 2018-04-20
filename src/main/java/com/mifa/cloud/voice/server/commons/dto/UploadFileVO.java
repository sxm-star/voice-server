package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2018/4/12.
 */
@Data
@ApiModel("文件上传返回实体类")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileVO {

    //@ApiModelProperty("文件完整路径")
    //private String fullPath;

    @ApiModelProperty("去除域名后的文件路径")
    private String halfPath;


}
