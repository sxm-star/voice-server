package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 
 */
@Table(name="t_customer_task_contact_group")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTaskContactGroupDO extends BaseDataDO {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 主键
     */
    @NotEmpty
    private String taskId;
    /**
     * 0:正常; 1:删除
     */
    private String status;

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

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 最后更新人
     */
    @Column(name = "updated_by")
    private String updatedBy;

}