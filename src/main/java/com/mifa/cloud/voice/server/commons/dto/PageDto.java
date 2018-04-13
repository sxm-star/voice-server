package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: songxm
 * @date: 2018/4/13 15:06
 * @version: v1.0.0
 */
@ApiModel("分页实体信息")
@Data
public class PageDto<T> {

    @ApiModelProperty("结果集")
    List<T> list;
    @ApiModelProperty("当前页")
    private int pageNum;
    @ApiModelProperty("每页的数量")
    private int pageSize;
    @ApiModelProperty("总条数")
    private long total;
    @ApiModelProperty("总页数")
    private int pages;
    @ApiModelProperty("当前页的数量")
    private int size;
}
