package com.mifa.cloud.voice.server.utils;

import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author songxm
 */
@Component
public class FileUploadUtils {

    @Value("${download_local_path}")
    private String PATH;

    private static String ALLOW_TYPE = "";

    private static Integer MAX_SIZE = 1024 * 1024 * 10;

    private static FileUploadUtils fileUploadUtils;

    @PostConstruct
    public void init() {
        fileUploadUtils = this;
        fileUploadUtils.PATH = this.PATH;
    }

    public static Map<String, Object> upload(MultipartFile file) {

        Map<String, Object> resMap = new HashMap<>();

        if (null == file || StringUtils.isEmpty(file.getOriginalFilename())) {
            return resMap;
        }

            if(!sizeCheck(file.getSize(), MAX_SIZE)){
                throw new BusinessException("文件过大");
            }

            String filename = file.getOriginalFilename();
            filename = filename.substring(filename.lastIndexOf("/")+1);
            //得到上传文件的扩展名
            String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
            if(!typeCheck(fileExtName, ALLOW_TYPE)){
                throw new BusinessException("类型不支持");
            }
        String realPath = fileUploadUtils.PATH;
        if(realPath==null){
            throw new BusinessException("文件存储路径不存在");
        }
        try{
            String saveFilename = makeFileName(filename);
            String realSavePath = makePath(saveFilename, realPath);
            InputStream in = file.getInputStream();
            FileOutputStream out = new FileOutputStream(realSavePath + saveFilename);
            byte buffer[] = new byte[1024];
            int len = 0;
            while((len=in.read(buffer))>0){
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
            resMap.put("fileName", filename);
            resMap.put("fileExtName", fileExtName);
            resMap.put("fileSize", file.getSize());
            resMap.put("saveFilename", saveFilename);
            resMap.put("realSavePath", realSavePath);
        }catch (Exception e) {
            throw new BusinessException("系统错误");
        }

        return resMap;
    }

    /**
     * @Method: makeFileName
     * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     * @param filename 文件的原始名称
     * @return uuid+"_"+文件的原始名称
     */
    private static String makeFileName(String filename){ //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString() + "_" + filename;
    }
    /**
     * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
     *
     * @param filename 文件名，要根据文件名生成存储目录
     * @param savePath 文件存储路径
     * @return 新的存储目录
     */
    private static String makePath(String filename,String savePath){
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf; //0--15
        int dir2 = (hashcode&0xf0)>>4; //0-15
        //构造新的保存目录
        String dir = savePath +"document"+"/" + dir1 + "/" + dir2 + "/"; //upload\2\3 upload\3\5
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if(!file.exists()){
            //创建目录
            file.mkdirs();
        }
        return dir;
    }

    /**
     * 文件类型检查
     * @param fileExtName
     * @param Type
     * @return
     */
    private static boolean typeCheck(String fileExtName, String Type){
        boolean flag = false;
        List<String> list = StringTools.splitList(Type, AppConst.COMMA);
        if(list == null){
            return true;
        }
        for(String str : list){
            if (str.toLowerCase().equals(fileExtName.toLowerCase())) {flag = true;}
        }
        return flag;

    }

    /**
     * 文件大小检查
     * @param fileSize
     * @param maxSize
     * @return
     */
    private static boolean sizeCheck(Long fileSize, Integer maxSize){
        return fileSize < maxSize;
    }
}
