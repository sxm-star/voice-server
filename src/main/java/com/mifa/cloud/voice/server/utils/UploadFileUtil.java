package com.mifa.cloud.voice.server.utils;

import com.mifa.cloud.voice.server.commons.enums.FileTypeEnums;
import com.mifa.cloud.voice.server.commons.enums.BizTypeEnums;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.dto.UploadFileVO;
import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.service.UploadFileLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/9.
 */
@Component
@Slf4j
public class UploadFileUtil {

    private static final int HEADERWIDTH = 1080;
    private static final int HEADERHEIGHT = 1920;
    @Autowired
    private UploadFileLogService uploadFileLogService;

    public UploadFileVO upload(MultipartFile file, FileTypeEnums fileType, BizTypeEnums bizType, String contractNo, ConstConfig aconst) throws Exception {
        UploadFileVO vo = null;
        if (!file.isEmpty()) {
            try {
                Date date = new Date();

                String initPath = "/uploadfiles/" + fileType.getDesc() + "/" + new SimpleDateFormat("yyyy").format(date)
                        + "/" + new SimpleDateFormat("MMdd").format(date) + "/" + new SimpleDateFormat("HH").format(date);

                String path = aconst.UPLOAD_PATH + initPath;
                File tempFilePath = new File(path);
                if (!tempFilePath.exists()) {
                    tempFilePath.mkdirs();
                }
                String fileName = file.getOriginalFilename();
                //索引到最后一个反斜杠
                int start = fileName.lastIndexOf("\\");
                //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
                String filename = fileName.substring(start+1);
                String fileEnd = filename.substring(filename.lastIndexOf(".") + 1);
                String newFileName = getRandomChar()+"."+fileEnd;
                String pathname = getRandomString(4) + newFileName;
                String srcPath = path+"/" + newFileName;
                String desPath = path +"/"+ pathname;
                GmImageUtil.scaleImg(srcPath, desPath, HEADERWIDTH, HEADERHEIGHT);
                //这里将上传得到的文件
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(tempFilePath, pathname));
                vo = UploadFileVO.builder()
                        //.fullPath(aconst.H5_URL_PATH + initPath + "/" + pathname)
                        .halfPath(initPath + "/" + pathname)
                        .build();

                // 插入上传记录表
                UploadFileLog uploadFileLog = UploadFileLog.builder()
                        .fileName(newFileName)
                        .fileStatus("0")
                        .fileType(fileType.name())
                        .bizType(bizType.name())
                        .fileUrl(initPath + "/" + pathname)
                        .fileRealPath(path + "/" + pathname)
                        .createAt(new Date())
                        .createBy(contractNo)
                        .build();
                uploadFileLogService.insert(uploadFileLog);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.error("上传文件为空");
            throw new IOException("上传文件为空");
        }
        return vo;
    }



    private static String getRandomString(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    /**
     * JAVA获得0-9,a-z,A-Z范围的随机数
     * @param length 随机数长度
     * @return String
     */
    public static String getRandomChar(int length) {
        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(62)]);
        }
        return buffer.toString();
    }

    public static String getRandomChar() {
        return getRandomChar(16);
    }
}
