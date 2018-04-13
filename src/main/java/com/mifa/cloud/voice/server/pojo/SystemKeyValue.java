package com.mifa.cloud.voice.server.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemKeyValue implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 系统功能类型
     */
    private String bizType;

    /**
     * 参数key
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 字典说明
     */
    private String remark;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * t_system_key_value
     */
    private static final long serialVersionUID = 1L;

}