package com.mifa.cloud.voice.server.commons.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Administrator on 2018/4/11.
 */

@Data
@ApiModel("用户修改头像实体类")
public class UserEditAvatarDTO {


    @ApiModelProperty("用户ID")
    @NotEmpty(message = "用户ID不能为空")
    private String contractNo;

    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String avatarUrl;

}
