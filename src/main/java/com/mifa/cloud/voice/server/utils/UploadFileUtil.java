package com.mifa.cloud.voice.server.utils;

import com.mifa.cloud.voice.server.commons.enums.FileStatusEnum;
import com.mifa.cloud.voice.server.commons.enums.FileTypeEnums;
import com.mifa.cloud.voice.server.commons.enums.BizTypeEnums;
import com.mifa.cloud.voice.server.commons.enums.MQMsgEnum;
import com.mifa.cloud.voice.server.config.ConstConfig;
import com.mifa.cloud.voice.server.commons.dto.UpLoadFilePathDTO;
import com.mifa.cloud.voice.server.commons.dto.UploadFileVO;
import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.service.UploadFileLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    private RabbitTemplate rabbitTemplate;


    public UploadFileVO upload(MultipartFile file, FileTypeEnums fileType, BizTypeEnums bizType, String contractNo, String groupId, ConstConfig aconst) throws Exception {
        UploadFileVO vo = null;
        if (!file.isEmpty()) {
            try {
                UpLoadFilePathDTO pathDTO = filePathDeal(file, fileType, aconst.UPLOAD_PATH);
                vo = UploadFileVO.builder()
                        .halfPath(pathDTO.getInitPath() + File.separator + pathDTO.getRandomFilename())
                        .build();

                if(FileTypeEnums.EXCEL.name().equals(fileType.name()) && BizTypeEnums.MOBILE_TEMPLATE.name().equals(bizType.name())) {
                    List<UploadFileLog> fileLogs = uploadFileLogService.selectByFileTypeAndBizType(FileTypeEnums.EXCEL.name(), BizTypeEnums.MOBILE_TEMPLATE.name(), FileStatusEnum.EFFECTIVE.getCode());
                    // 删除重复的模板文件
                    fileLogs.stream().forEach(item -> uploadFileLogService.deleteByPrimaryKey(item.getId()));
                }

                // 插入上传记录表
                UploadFileLog uploadFileLog = UploadFileLog.builder()
                        .fileName(pathDTO.getRandomFilename())
                        .mobileListGroupId(groupId)
                        .fileStatus(FileStatusEnum.EFFECTIVE.getCode())
                        .fileType(fileType.name())
                        .bizType(bizType.name())
                        .fileUrl(pathDTO.getInitPath() + File.separator + pathDTO.getRandomFilename())
                        .fileRealPath(pathDTO.getDesPath())
                        .createAt(new Date())
                        .createBy(contractNo)
                        .build();
                uploadFileLogService.insert(uploadFileLog);

                //如果上传类型为号码列表，发送mq消息通知解析号码excel
                if (FileTypeEnums.EXCEL.name().equals(fileType.name()) && BizTypeEnums.MOBILE_LIST.name().equals(bizType.name())) {
                    log.info("号码列表上传完成发送mq消息groupId：" + groupId);
                    rabbitTemplate.convertAndSend(MQMsgEnum.ANALYSIS_MOBILE_LIST.getCode(), groupId);
                }
            } catch (Exception e) {
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
     *
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

    private UpLoadFilePathDTO filePathDeal(MultipartFile file, FileTypeEnums fileType, String uploadPath) {

        String initPath = getInitPath(fileType.getDesc());
        String absolutePath = uploadPath + initPath;
        File tempFilePath = new File(absolutePath);
        if (!tempFilePath.exists()) {
            tempFilePath.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();

        //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
        String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
        //获得文件的类型
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = getRandomChar() + "." + fileSuffix;
        String randomFilename = getRandomString(4) + newFileName;
        String srcPath = absolutePath + File.separator + newFileName;
        String desPath = absolutePath + File.separator + randomFilename;
        try {
            GmImageUtil.scaleImg(srcPath, desPath, HEADERWIDTH, HEADERHEIGHT);
            //这里将上传得到的文件
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(tempFilePath, randomFilename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UpLoadFilePathDTO(initPath, randomFilename, desPath);

    }

    private String getInitPath(String fileType) {
        LocalDateTime now = LocalDateTime.now();
        String initPath = File.separator + "uploadfiles"
                + File.separator + fileType
                + File.separator + now.format(DateTimeFormatter.ofPattern("yyyy"))
                + File.separator + now.format(DateTimeFormatter.ofPattern("MMdd"))
                + File.separator + now.format(DateTimeFormatter.ofPattern("HH"));
        return initPath;
    }

    /**
     * 文件格式校验
     */
    public Boolean fileTypeReg(@RequestParam("fileType") FileTypeEnums fileType, String filename) {
        // 如果上传的是excel文件
        if(FileTypeEnums.EXCEL.name().equals(fileType.name())) {
            // 表达式对象
            Pattern excelPattern = Pattern.compile("^(?:\\w+\\.xlsx|\\w+\\.xls)$");
            // 创建 Matcher 对象
            Matcher excelMatcher = excelPattern.matcher(filename);
            if(!excelMatcher.matches()) {
                return Boolean.FALSE;
            }
        }
        // 如果上传的事音频
        if(FileTypeEnums.VOICE.name().equals(fileType.name())) {
            // 表达式对象
            Pattern voicePattern = Pattern.compile("^(?:\\w+\\.wav)$");
            // 创建 Matcher 对象
            Matcher voiceMatcher = voicePattern.matcher(filename);
            if(!voiceMatcher.matches()) {
                return Boolean.FALSE;
            }
        }

        // 如果上传的事音频
        if(FileTypeEnums.IMAGE.name().equals(fileType.name())) {
            // 表达式对象
            Pattern imagePattern = Pattern.compile("^(?:\\w+\\.jpg|\\w+\\.jpeg|\\w+\\.bmp|\\w+\\.png|\\w+\\.gif|\\w+\\.pdf)$");
            // 创建 Matcher 对象
            Matcher imageMatcher = imagePattern.matcher(filename);
            if(!imageMatcher.matches()) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
