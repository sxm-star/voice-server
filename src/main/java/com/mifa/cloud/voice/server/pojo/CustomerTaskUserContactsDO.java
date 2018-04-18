package com.mifa.cloud.voice.server.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 */
@Table(name="t_customer_task_user_contacts")
@Data
public class CustomerTaskUserContactsDO extends BaseDataDo{
    /**
     * ID号
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 租户号
     */
    @NotEmpty
    private String contractNo;

    /**
     * 批次上传生成的task_id
     */
    @NotEmpty
    private String taskId;

    /**
     * 用户手机号加密处理
     */
    @NotEmpty
    private String userPhone;

    /**
     * 用户姓名
     */
    private String userName;

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

    /**
     * 批次下拨通状态1:已拨 2：未拨
     */
    private String callStatus;

}