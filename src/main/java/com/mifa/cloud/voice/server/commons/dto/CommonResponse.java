package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Controller通用返回Json
 * 
 * @author 宋烜明
 *
 */
@Data
@ApiModel("通用响应实体信息")
public class CommonResponse {
	public static final String TRUE 	= "true";
	public static final String FALSE	= "false";

    @ApiModelProperty("成功标志")
	private String success;			//成功标志
    @ApiModelProperty("错误码")
	private String errCode;			//错误码
    @ApiModelProperty("错误信息")
	private String errMsg;			//错误信息
	private Object body;	//Body

	public CommonResponse() {
		setSuccess(TRUE);
	}

	public CommonResponse(String success, String errCode, String errMsg, Object body){
		this.success = success;
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.body = body;
	}
}
