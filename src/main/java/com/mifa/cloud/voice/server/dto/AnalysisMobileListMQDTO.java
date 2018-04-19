package com.mifa.cloud.voice.server.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Administrator on 2018/4/19.
 * 解析号码列表消息队列dto
 */
@Data
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
