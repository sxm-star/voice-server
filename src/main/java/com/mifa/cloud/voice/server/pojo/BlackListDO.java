package com.mifa.cloud.voice.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author songxm
 */
@Table(name="t_black_list")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlackListDO extends BaseDataDO {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户ID
     */
    private String contractNo;

    /**
     * 手机号
     */
    @NotEmpty
    private String value;

    /**
     * 名单类型：black代表黑名单；gray代表灰名单；white代表白名单
     */
    private String type;

    /**
     * 标签值 phone
     */
    private String tag;

    /**
     * 黑名单用户姓名等
     */
    private String name;

    /**
     * 状态：1代表启用  0代表禁用
     */
    private String status;

    /**
     * 备注详情
     */
    private String comments;

    /**
     * 名单来源，例如内部等
     */
    private String source;

}