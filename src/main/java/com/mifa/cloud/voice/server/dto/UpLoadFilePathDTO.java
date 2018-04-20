package com.mifa.cloud.voice.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2018/4/19.
 * 文件上传路径类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpLoadFilePathDTO {

    /**
     * 文件从指定文件夹的位置
     */
    private String initPath;

    /**
     * 文件随机生成的名称
     */
    private String randomFilename;

    /**
     * 文件所在服务器的绝对路径
     */
    private String desPath;



}
