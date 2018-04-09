package com.mifa.cloud.voice.server.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共字段
 */
@Setter
@Getter
@ToString
public class BaseDataDo implements Serializable {

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
	 * 最后更新人
	 */
	private String updatedBy;

}