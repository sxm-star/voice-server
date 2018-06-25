package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 
 */
@Table(name="t_openapi_config")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenapiConfigDO extends BaseDataDO {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户号
     */
    private String contractNo;

    /**
     * 鉴权token
     */
    private String authToken;
    /**
     * 上次修改前的token
     */
    private String lastAuthToken;

    /**
     * 过期时间
     */
    private Date authExpire;

    /**
     * 商户回调地址配置
     */
    private String callBack;


}