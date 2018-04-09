package com.mifa.cloud.voice.server.commons.enums;

/**
 * 统一的异常类型映射
 * @author: 宋烜明
 * @date: 2018/4/9 11:13
 * @version: v1.0.0
 */
public enum ErrorKeyEnums {
    UNAUTHORIZED("401","校验token异常"),
    ILLEGAL_ARGUMENT("4000","不合法的参数"),
    NO_EXIST_USER("4102","客户不存在"),
    PARAM_NOT_BLANK("4103","参数[{}]不能为空"),
    USER_CREATE_FAIL("4104","创建用户失败"),
    THIRD_PARTY_EXCEPTION("4000","三方服务调用异常"),
    RATE_LIMIT_EXCEED("4029","请求太过于频繁,请稍候再试"),
    MEDIA_TYPE_NOT_SUPPORTED("4150","不支持的媒体类型");
    String code;
    String msg;
     ErrorKeyEnums(String errCode,String errMsg){
         this.code = code;
         this.msg = msg;
    }
}
