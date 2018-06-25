package com.mifa.cloud.voice.server.api.jx.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: songxm
 * @date: 2018/4/27 16:32
 * @version: v1.0.0
 */
@Data
public class CallBackDto {
    String type;
    String total;
    List<CallBackItemDto> reportList;
}
