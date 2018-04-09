package com.mifa.cloud.voice.server.pojo;

import lombok.Data;

/**
 * @author 
 */
@Data
public class UserDo extends BaseDataDo {
    /**
     * 用户唯一标识
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别. M-男,F-女,U-未知
     */
    private String gender;

    /**
     * 身份证.加密
     */
    private String idCard;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 实名标识.Y-实名;N-未实名;U-未知
     */
    private String realNameStatus;

    /**
     * 邮箱
     */
    private String email;


}