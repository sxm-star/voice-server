package com.mifa.cloud.voice.server.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author songxm
 */
@Data
public class VoiceCategoryDO extends BaseDataDo {
    /**
     * 类目id
     */
    @Id
    @GeneratedValue
    private Integer categoryId;

    /**
     * 父类目parent_id=0时，代表的是一级的类目
     */
    private Integer parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 状态。可选值:1(正常),2(删除)
     */
    private String status;

    /**
     * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
     */
    private Integer sortOrder;

    /**
     * 该类目是否为父类目，1为true，0为false
     */
    private Boolean isParent;

}