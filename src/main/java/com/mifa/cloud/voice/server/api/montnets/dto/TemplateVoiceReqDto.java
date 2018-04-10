package com.mifa.cloud.voice.server.api.montnets.dto;

import lombok.Data;

/**
 * @author: sxm
 * @date: 2018/4/10 11:34
 * @version: v1.0.0
 */
@Data
public class TemplateVoiceReqDto {


    /**
     * 用户唯一标识：32位长度，由梦网提供，与userid及pwd一样用于鉴权，如提交参数中包含userid及pwd，则可以不用填写该参数
     */
    private String apikey;

    /**
     * 用户账号：长度最大6个字符，统一大写，如提交参数中包含apikey，则可以不用填写该参数及pwd，两种鉴权方式中只能选择一种方式来进行鉴权
      */
    private String userid;
    /**
     * 用户密码：定长小写32位字符，如提交参数中包含apikey，则可以不用填写该参数及userid，两种鉴权方式中只能选择一种方式来进行鉴权
     */
    private String pwd;
    /**
     * 接收的手机号
     */
    private String mobile;
    /**
     *  语音内容 urlencode编码
     */
    private String content;
    /**
     * 时间戳 时间戳：24小时制格式：MMDDHHMMSS 密码选择MD5加密方式时必填该参数,密码选择明文方式时则不用填写
     */
    private String timestamp;
    /**
     * 用户自定义流水号：该条语音在您业务系统内的ID，比如订单号或者语音发送记录的流水号。填写后发送状态返回值内将包含用户自定义流水号。
     * 最大可支持32位的ASCII字符串：字母、数字、下划线、减号
     */
    private String custid;
    /**
     * 回拨显示的号码，目前需要备案后才能支持使用，如果没有备案将会返回错误码 -401094（显示号码不合法）
     */
    private String exno;
    /**
     * 语音模版编号
     */
    private String tmplid;
    /**
     * 消息类型
     * 1：语音验证码
     * 3：语音通知（语音id）
     */
    private String msgtype;
}
