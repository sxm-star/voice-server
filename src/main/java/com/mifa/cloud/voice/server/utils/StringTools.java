package com.mifa.cloud.voice.server.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringTools extends StringUtils {

    private static final String KEY_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int SECRET_LENGTH = 24; // 秘钥长度
    private static final int DEFAULT_LENGTH = 0; // 秘钥长度

    /**
     * List<String>  转换为  List<Long>
     *
     * @param stringList
     * @return
     */
    public static List<Long> stringList2LongList(List<String> stringList) {
        List<Long> longs = Lists.newArrayList();
        for (String s : stringList) {
            if (!StringTools.isEmpty(s)) {
                longs.add(Long.parseLong(s));
            } else {
                longs.add(new Long(0));
            }
        }
        return longs;
    }


    /**
     * String  转换为  Long
     *
     * @param s
     * @return
     */
    public static long string2long(String s) {
        if (StringTools.isEmpty(s)) {
            return 0;
        }
        return Long.parseLong(s);
    }

    /**
     * long  转换为  String
     *
     * @param lng
     * @return
     */
    public static String long2String(long lng) {
        if (lng == 0) {
            return null;
        }
        return String.valueOf(lng);
    }

    /**
     * long  转换为  String
     *
     * @param bigDecimal
     * @return
     */
    public static String BigDecimal2String(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return String.valueOf(bigDecimal);
    }

    /**
     * string  转换为  BigDecimal
     *
     * @param string
     * @return
     */
    public static BigDecimal string2BigDecimal(String string) {
        if (StringTools.isEmpty(string)) {
            return null;
        }
        return new BigDecimal(string);
    }

    /**
     * string  转换为  int
     *
     * @param string
     * @return
     */
    public static Integer string2Int(String string) {
        if (StringTools.isEmpty(string)) {
            return null;
        }
        return Integer.parseInt(string);
    }


    /**
     * 字符串转换为数组
     *
     * @param join
     * @param strAry
     * @return
     */
    public static List<String> splitStr(String join, String strAry) {
        if (StringTools.isEmpty(strAry)) {
            return Arrays.asList(new String[]{});
        }
        return Arrays.asList(strAry.split(join));
    }

    /**
     * 随机生成指定位数+code.length()的字符串
     *
     * @param code 组织机构代码
     * @return 返回秘钥字符串
     */
    public static String generateKey(String code) {
        return code + getRandomString(SECRET_LENGTH - (StringTools.isEmpty(code) ? DEFAULT_LENGTH : code.trim().length()));
    }

    /**
     * 随机生成指定位数的字符串
     *
     * @param length 长度
     * @return 返回指定长度的字符串
     */
    public static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer(KEY_STRING);
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * 根据指定的长度随机产生指定长度数字的字符串
     *
     * @param len 指定的长度
     * @return
     */
    public static String genRandomString(int len) {
        final char[] mm = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < len; i++) {
            sb.append(mm[random.nextInt(mm.length)]);
        }
        return sb.toString();
    }

    /**
     * 去除符合条件的字符串最后字符
     * @param source
     * @param symbol
     * @return
     */
    public static String CutLastSymbol(String source, String symbol){
        String dest = source;
        if (!isEmpty(source)) {
            int deptsFrontLoca = source.length() - 1;
            int deptsEndLoca = source.length();
            String endComma = source.substring(deptsFrontLoca, deptsEndLoca);
            if (endComma.equals(symbol)) {
                dest = source.substring(0, deptsFrontLoca);
            }
        }
        return dest;
    }

    /**
     * 字符串分割成List
     *
     * @param str 需要分割的字符串
     * @param reg 分隔符
     * @return 分割后List
     */
    public static List<String> splitList(String str, String reg) {
        if (StringUtils.isEmpty(str)) {
            return null;
        } else if (!str.contains(reg)) {
            List<String> list = new ArrayList<String>();
            list.add(str);
            return list;
        }
        // 去除最后有个 ","
        if (str.lastIndexOf(reg) > 0 && (str.length() - 1 == str.lastIndexOf(reg))) {
            str = str.substring(0, str.lastIndexOf(reg));
        }
        return Arrays.asList(StringUtils.split(str, reg));
    }

    /**
     * 获取异常信息
     *
     * @param e
     * @return
     */
    public static String getExceptionInfo(Exception e) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("className: ");
        buffer.append(e.getStackTrace()[0].getClassName());
        buffer.append(" ; methodName: ");
        buffer.append(e.getStackTrace()[0].getMethodName());
        return buffer.toString();
    }

    /**
     * 功能：空值替换,类似于oracle的nvl函数，如果isNull为空，则替换为result
     * @param isNull 比较的字符串
     * @param replace isNull为null则返回该字符串
     * @return 返回的字符串
     */
    public static String nvl(String isNull, String replace) {
        if (isNull == null)
            return replace;
        if ((isNull.trim()).length() == 0 || isNull.equals("")) {

        } else {
            replace = isNull;
        }
        return replace;
    }

    /**
     * 把List组合成字符串，使用 splitChar 分割。
     *
     * @param value 需要组合的List
     * @return 组合后的字符串
     */
    public static String assemble(List<String> value, char splitChar) {
        if (value == null || value.size() == 0) {
            return "";
        }
        StringBuilder temp = new StringBuilder();
        for (String s : value) {
            temp.append(s);
            temp.append(splitChar);
        }
        temp.deleteCharAt(temp.length() - 1);
        return temp.toString();
    }

    /**
     * List转换String类型
     *
     * @param list
     * @return
     */
    public static String getString(List<String> list, String reg) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (String str : list) {
            buffer.append(reg).append(str);
        }
        return buffer.substring(1);
    }

    /**
     * 手机号、身份证号、姓名脱敏处理
     *
     * @param sensitiveInfo
     * @param type
     * @return
     */
    public static String sensitiveInfoConvert(String sensitiveInfo, String type){

        if(sensitiveInfo == null){
            return null;
        }
        if("MOBILE".equals(type)){
            return sensitiveInfo.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        }else if("ID_CARD".equals(type)){
            return sensitiveInfo.replaceAll("(\\d{4})\\d{10}(\\w{4})","$1*****$2");
        }else {
            String name = StringUtils.left(sensitiveInfo, 1);
            return StringUtils.rightPad(name, StringUtils.length(sensitiveInfo), "*");
        }
    }

    //字符串转double
    public static Double tranferStringToDouble(String s) {
        return s.equals("") ? 0.00 : Double.parseDouble(s);
    }

    /**
     * 中文转Unicode
     *
     * @param gbString  中文字符
     * @return  Unicode编码
     */
    public static String gbEncoding(final String gbString) {   //gbString = "测试"
        char[] utfBytes = gbString.toCharArray();   //utfBytes = [测, 试]
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);   //转换为16进制整型字符串
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        System.out.println("unicodeBytes is: " + unicodeBytes);
        return unicodeBytes;
    }

    /**
     * Unicode转中文
     *
     * @param dataStr   请求字符串
     * @return  中文
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }
}
