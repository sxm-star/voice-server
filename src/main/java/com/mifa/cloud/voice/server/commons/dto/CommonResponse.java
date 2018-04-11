package com.mifa.cloud.voice.server.commons.dto;

import com.mifa.cloud.voice.server.commons.enums.ErrorKeyEnums;
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
    @ApiModelProperty("提示信息")
	private String errMsg;			//错误信息
	private Object data;			//data

	public CommonResponse() {
		setSuccess(TRUE);
	}

	public CommonResponse(String success, String errCode, String errMsg, Object data){
		this.success = success;
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.data = data;
	}

    public CommonResponse(String success, ErrorKeyEnums errorKeyEnums, Object body){
        this.success = success;
        this.errCode = errorKeyEnums.getCode();
        this.errMsg = errorKeyEnums.getMsg();
        this.body = body;
    }


	public static CommonResponse successCommonResponse() {
		return new CommonResponse();
	}

	public static CommonResponse successCommonResponse(Object data) {
		return new CommonResponse(TRUE, "", "", data);
	}

	public static CommonResponse successCommonResponse(String errMsg, Object data) {
		return new CommonResponse(TRUE, "", errMsg, data);
	}

	public static CommonResponse failCommonResponse() {
		return new CommonResponse(FALSE, "", "", null);
	}

	public static CommonResponse failCommonResponse(String errMsg) {
		return new CommonResponse(FALSE, "400", errMsg, null);
	}

	public static CommonResponse failCommonResponse(String errCode, String errMsg) {
		return new CommonResponse(FALSE, errCode, errMsg, null);
	}

}
