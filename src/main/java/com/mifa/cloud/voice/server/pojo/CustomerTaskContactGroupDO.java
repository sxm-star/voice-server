package com.mifa.cloud.voice.server.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 */
@Table(name="t_customer_task_contact_group")
@Data
public class CustomerTaskContactGroupDO extends BaseDataDo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 主键
     */
    @NotEmpty
    private String taskId;

    /**
     * 通讯簿组表
     */
    private String groupName;

    /**
     * 通讯录组内成员数量
     */
    private Integer groupCnt;

    /**
     * 来源
     */
    private String source;


}