package com.mifa.cloud.voice.server.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
@Data
public class CustomerTaskUserContactsDO implements Serializable {
    /**
     * ID号
     */
    private Long id;

    /**
     * 租户号
     */
    private String contractNo;

    /**
     * 批次上传生成的task_id
     */
    private String taskId;

    /**
     * 0:正常；1:删除
     */
    private String status;

    /**
     * 用户手机号加密处理
     */
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
    private String salt;

    /**
     * 批次下拨通状态1:已拨 2：未拨
     */
    private String callStatus;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 更新人
     */
    private String updatedBy;





}