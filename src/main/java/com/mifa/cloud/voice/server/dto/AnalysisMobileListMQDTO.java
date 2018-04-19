package com.mifa.cloud.voice.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2018/4/19.
 * 解析号码列表消息队列dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalysisMobileListMQDTO {


    /**
     * 号码列表所属组id
     */
    private String groupId;

    /**
     * 号码列表所属服务器地址
     */
    private String fileRealPath;

}
