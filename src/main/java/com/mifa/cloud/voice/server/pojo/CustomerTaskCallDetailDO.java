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
@Data
@Table(name="t_customer_task_call_detail")
public class CustomerTaskCallDetailDO extends BaseDataDo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户号
     */
    @NotEmpty
    private String contractNo;

    /**
     * 语音模板id
     */
    @NotEmpty
    private String templateId;

    /**
     * 上传号码批次任务ID
     */
    private String taskId;

    /**
     * 手机号
     */
    @NotEmpty
    private String phone;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 拨打次数
     */
    private Integer callCnt;

    /**
     * 拨打分钟数
     */
    private Integer callTime;

    /**
     * 拨打情况 1:已拨通；2：未拨通
     */
    @NotEmpty
    private String callFlag;

    /**
     * 用户标签属性 1:潜在用户;2:中性用户;3:拒绝用户
     */
    @NotEmpty
    private String userTag;

    /**
     * 备注
     */
    private String note;


}