package com.mifa.cloud.voice.server.utils;

import org.im4java.core.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class GmImageUtil {

    /**
     * @param args
     * @throws IM4JavaException
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException, IM4JavaException {
        cropImageCenter("/Users/Chen/Desktop/1.gif", "/Users/Chen/Desktop/_b.jpg", 30, 30);
    }

    /**
     * 给图片加水印
     *
     * @param srcPath 源图片路径
     */
    public static void addImgText(String srcPath) throws IOException, InterruptedException, IM4JavaException {
        IMOperation op = new IMOperation();


        op.addImage();
        op.draw("text 5,5 juziku.com");
        op.addImage();
        ConvertCmd convert = new ConvertCmd(true);
        convert.setSearchPath("/usr/local/bin/");

        convert.run(op, srcPath, srcPath);
    }

    /**
     * 两张图片合并
     *
     * @throws IOException
     * @throws InterruptedException
     * @throws IM4JavaException
     */
    public static void composite(String srcd, String srcb, String desPath) throws IOException, InterruptedException, IM4JavaException {
        IMOperation localIMOperation = new IMOperation();
        localIMOperation.addImage();
        localIMOperation.addImage();
        localIMOperation.addImage();
        CompositeCmd localCompositeCmd = new CompositeCmd(true);
        localCompositeCmd.setSearchPath("/usr/local/bin/");
        localCompositeCmd.run(localIMOperation, new Object[]{srcd, srcb, desPath});


    }

    /**
     * 图片旋转
     *
     * @param srcImagePath
     * @param destImagePath
     * @param angle
     */
    public static void rotate(String srcImagePath, String destImagePath, double angle) {
        try {
            IMOperation op = new IMOperation();
            op.rotate(angle);
            op.addImage(srcImagePath);
            op.addImage(destImagePath);
            ConvertCmd cmd = new ConvertCmd(true);
            cmd.setSearchPath("/usr/local/bin/");
            cmd.run(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addBackground(String srcPath, String desPath) throws IOException, InterruptedException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.background("none");

        op.addImage(srcPath);

        op.addImage(desPath);

        ConvertCmd convert = new ConvertCmd(true);
        ConvertCmd.setGlobalSearchPath("/usr/local/bin/");


        convert.run(op);
    }

    /**
     * 根据坐标裁剪图片
     *
     * @param srcPath 要裁剪图片的路径
     * @param newPath 裁剪图片后的路径
     * @param x       起始横坐标
     * @param y       起始纵坐标
     * @param x1      结束横坐标
     * @param y1      结束纵坐标
     */
    public static void cutImage(String srcPath, String newPath, int x, int y, int x1,
                                int y1) throws IOException, InterruptedException, IM4JavaException {
        int width = x1 - x;
        int height = y1 - y;
        IMOperation op = new IMOperation();
        op.background("none");//不处理背景颜色

        op.addImage(srcPath);

        /**
         * width：裁剪的宽度 
         * height：裁剪的高度 
         * x：裁剪的横坐标 
         * y：裁剪的挫坐标 
         */
        op.crop(width, height, x, y);
        op.addImage(newPath);

        ConvertCmd convert = new ConvertCmd(true);

        //linux下不要设置此值，不然会报错  
        convert.setSearchPath("/usr/local/bin/");


        convert.run(op);
    }

    /**
     * 先缩放，后居中切割图片
     *
     * @param srcPath 源图路径
     * @param desPath 目标图保存路径
     * @param rectw   待切割在宽度
     * @param recth   待切割在高度
     * @throws IM4JavaException
     * @throws InterruptedException
     * @throws IOException
     */
    public static void cropImageCenter(String srcPath, String desPath, int rectw, int recth) throws IOException, InterruptedException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.background("none");
        op.addImage();
        op.resize(rectw, recth);
        //op.resize(rectw, recth, '^').gravity("Center").extent(rectw, recth);
        //op.quality(80d);
        op.addImage();

        ConvertCmd convert = new ConvertCmd(true);
        ConvertCmd.setGlobalSearchPath("/usr/local/bin/");

        convert.run(op, srcPath, desPath);

    }

    /**
     * 先缩放，后居中切割图片
     *
     * @param srcPath 源图路径
     * @param desPath 目标图保存路径
     * @param maxWidth   最大宽度
     * @param maxHeight  最大高度
     * @throws IM4JavaException
     * @throws InterruptedException
     * @throws IOException
     */
    public static void scale(String srcPath, String desPath, int maxWidth, int maxHeight) throws IOException, InterruptedException, IM4JavaException {
        IMOperation op = new IMOperation();

        op.addImage();
        op.resize(maxWidth, maxHeight);
        op.addImage();

        ConvertCmd convert = new ConvertCmd(true);
        ConvertCmd.setGlobalSearchPath("/usr/local/bin/");

        convert.run(op, srcPath, desPath);
    }

    /**
     * 合并图片
     * @throws IM4JavaException
     * @throws InterruptedException
     * @throws IOException
     */
    @SuppressWarnings("static-access")
    public static void incorporate(int width, int height, String desPath, String... srcArray) throws IOException, InterruptedException, IM4JavaException {
        IMOperation op = new IMOperation();
        String[] imgs = new String[srcArray.length + 1];
        op.geometry(width, height);
        for (int i = 0, n = srcArray.length; i < n; i++) {
            imgs[i] = srcArray[i];
            op.addImage();
        }
        imgs[srcArray.length] = desPath;
        op.addImage();

        MontageCmd cmd = new MontageCmd(true);
        cmd.setGlobalSearchPath("/usr/local/bin/");

        cmd.run(op, imgs);
    }

    /**
     * 先缩放，后居中切割图片
     *
     * @param srcPath 源图路径
     * @param desPath 目标图保存路径
     * @param maxWidth   最大宽度
     * @param maxHeight  最大高度
     * @throws IM4JavaException
     * @throws InterruptedException
     * @throws IOException
     */
    public static void scaleImg(String srcPath, String desPath, int maxWidth, int maxHeight) throws IOException, InterruptedException, IM4JavaException {
        StringBuilder cmd = new StringBuilder("/usr/bin/gm convert -resize").append(" ").append(maxWidth).append("x").append(maxWidth)
                .append(" ").append(srcPath).append(" ").append(desPath);
        processImg(srcPath, cmd);
    }

    /**
     * 图片旋转
     *
     * @param srcImagePath
     * @param destImagePath
     * @param angle
     */
    public static void rotateImg(String srcImagePath, String destImagePath, double angle) {
        StringBuilder cmd = new StringBuilder("/usr/bin/gm convert  -rotate " + angle).append(" ")
                .append(srcImagePath).append(" ").append(destImagePath);
        processImg(srcImagePath, cmd);

    }

    public static void processImg(String imgSrcPath, StringBuilder cmd) {
        Process p = null;
        try {
            File path = new File(imgSrcPath);
            if (path.exists() && path.isFile()) {
                p = Runtime.getRuntime().exec(cmd.toString());
                //INSERT-20110616-BY LICHUANGANG-START
                String line;
                int ret = -1;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
                while ((line = bufferedReader.readLine()) != null) {

                }
                ret = p.waitFor();
                //INSERT-20110616-BY LICHUANGANG-END
            }
            //if(del) path.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
    }

}
