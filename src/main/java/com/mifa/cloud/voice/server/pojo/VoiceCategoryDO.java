package com.mifa.cloud.voice.server.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @author songxm
 */
@Data
@Table(name = "t_voice_category")
public class VoiceCategoryDO extends BaseDataDo {
    /**
     * 类目id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /**
     * 父类目parent_id=0时，代表的是一级的类目
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 分类名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 状态。可选值:0(正常),1(停用,删除)
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /**
     * 该类目是否为父类目，1为true，0为false
     */
    @Column(name = "is_parent")
    private Boolean isParent;

    /**
     * 业务类型标识
     */
    @Column(name = "bizType")
    private String bizType;

}