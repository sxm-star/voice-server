package com.mifa.cloud.voice.server.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author sxm
 */
@Table(name="t_customer_user_contacts")
@Data
public class CustomerUserContactsDO  extends BaseDataDO {
    /**
     * 用户姓名
     */
    private String userName;

    @Id
    private String contractNo;

    /**
     * 用户手机号加密处理
     */
    @Id
    private String userPhone;

    /**
     * M:男性; W:女性; N:未知
     */
    private String userSex;

    /**
     * 机构(公司)名字
     */
    @NotEmpty
    private String orgName;

    /**
     * 地址
     */
    @NotEmpty
    private String userAddress;

    /**
     * 用户职位
     */
    private String userJob;

    /**
     * 加密因子
     */
    @NotEmpty
    private String salt;

}