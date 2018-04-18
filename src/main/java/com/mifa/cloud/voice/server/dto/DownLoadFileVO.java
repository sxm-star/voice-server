package com.mifa.cloud.voice.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2018/4/18.
 */
@Data
@ApiModel("文件下载返回实体类")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownLoadFileVO {

    @ApiModelProperty("文件路径")
    private String fileUrl;
}
