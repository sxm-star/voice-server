package com.mifa.cloud.voice.server.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
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