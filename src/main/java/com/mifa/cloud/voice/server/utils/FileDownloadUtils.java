package com.mifa.cloud.voice.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
/**
 * @author songxm
 * 将文件打包成ZIP压缩文件
 */
@Slf4j
public class FileDownloadUtils {

    /**
     * 页面文件下载
     *
     * @param file     文件
     * @param response 返回信息
     * @param request  请求信息
     * @return 返回是否操作成功
     */
    public static boolean downloadFileToWeb(File file,
                                            HttpServletResponse response, HttpServletRequest request) {

        if (null == file) {
            return false;
        } else if (!file.exists() && !file.isDirectory()) {
            return false;
        }
        boolean result = false;
        OutputStream out = null;
        BufferedInputStream bin = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename="
                    + java.net.URLEncoder.encode(file.getName(), "UTF-8"));
            response.setContentType("application/octet-stream");
            out = response.getOutputStream();
            bin = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buf = new byte[1024];
            int len;
            while ((len = bin.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            result = true;
        } catch (Exception e) {
            log.error("call FileDownloadUtils downloadFileToWeb Exception:{}", e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(bin);
            if (file.exists()) {
                file.delete();
            }
        }
        return result;
    }

    /**
     * 提供页面文件下载
     */
    public static boolean downloadFileToWeb(File file, boolean deleteFlag, HttpServletResponse response,
                                            HttpServletRequest request) {
        return downloadFileToWeb(file, null, deleteFlag, response, request);
    }

    /**
     * 页面文件下载
     *
     * @param file       文件流
     * @param fileName   文件名称
     * @param deleteFlag 是否下载完成之后删除服务器文件
     * @param response   返回文件流信息
     * @param request    请求信息
     * @return 返回是否操作成功
     */
    public static boolean downloadFileToWeb(File file, String fileName, boolean deleteFlag,
                                            HttpServletResponse response, HttpServletRequest request) {
        if (null == file) {
            return false;
        } else if (!file.exists() && !file.isDirectory()) {
            return false;
        }
        OutputStream out = null;
        BufferedInputStream bin = null;
        FileInputStream fis = null;
        boolean result = false;
        try {
            request.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename="
                    + encodeChineseDownloadFileName(request, fileName == null ? file.getName() : fileName));
            response.setContentType("application/octet-stream");
            out = response.getOutputStream();
            fis = new FileInputStream(file.getPath());
            bin = new BufferedInputStream(fis);
            byte[] buf = new byte[1024];
            int len;
            while ((len = bin.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            result = true;
        } catch (Exception e) {
            log.error("call FileDownloadUtils downloadFileToWeb Exception:{}", e);
        } finally {
            IOUtils.closeQuietly(bin);
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(fis);
            if (file.exists() && deleteFlag) {
                file.delete();
            }
        }
        return result;
    }

    /**
     * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
     *
     * @throws Exception
     */
    public static String encodeChineseDownloadFileName(HttpServletRequest request, String pFileName) throws Exception {
        String agent = request.getHeader("USER-AGENT");
        if (agent == null) {
            return pFileName;
        }
        if (agent.contains("Firefox")) {
            //Firefox
            return "=?UTF-8?B?" + (new String(Base64.encodeBase64(pFileName.getBytes("UTF-8")))) + "?=";
        } else if (agent.contains("Chrome")) {
            //Chrome
            return new String(pFileName.getBytes(), "ISO-8859-1");
        } else {
            //IE7+,替换空格
            return java.net.URLEncoder.encode(pFileName, "UTF-8").replace("+", "%20");
        }
    }

    /**
     * 页面Excel文件下载
     *
     * @param hssfWorkbook Excel文件流
     * @param fileName     下载之后文件名称
     * @param response     返回信息
     */
    public static void downloadFileToWeb(HSSFWorkbook hssfWorkbook, String fileName, HttpServletResponse response) {

        OutputStream output = null;
        try {
            output = setHeader(response, fileName);
            hssfWorkbook.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 正常关闭输入输出流
            IOUtils.closeQuietly(output);
        }
    }

    /**
     * 页面Excel文件下载
     *
     * @param xssfWorkbook Excel文件流
     * @param fileName     下载之后文件名称
     * @param response     返回信息
     */
    public static void downloadFileToWeb(XSSFWorkbook xssfWorkbook, String fileName, HttpServletResponse response) {

        OutputStream output = null;
        try {
            output = setHeader(response, fileName);
            xssfWorkbook.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 正常关闭输入输出流
            IOUtils.closeQuietly(output);
        }
    }

    /**
     * 设置文件下载标题
     *
     * @param response 返回信息
     * @param fileName 文件名称
     * @return 返回文件流
     * @throws Exception 异常信息
     */
    private static OutputStream setHeader(HttpServletResponse response, String fileName) throws Exception {

        // 设置下载头信息.begin
        response.setContentType("octets/stream");
        response.addHeader("Content-Type", "text/html; charset=utf-8");
        response.setDateHeader("Expires", 0);
        // 这个地方一定要进行编码的转换要不然中文字符会出现乱码.
        // 设置下载头信息.end,
        response.setHeader("Content-Disposition", "attachment; filename=" +
                new String(fileName.getBytes("gbk"), "ISO-8859-1"));

        return response.getOutputStream();
    }
}
