package com.mifa.cloud.voice.server.annotation;

import com.mifa.cloud.voice.server.commons.enums.LOGTYPE;
import com.mifa.cloud.voice.server.commons.enums.SCOPE;

import java.lang.annotation.*;


/**
 * 日志记录 方法注解
 * 
 * @author sxm
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Loggable {
    /**
     * 打印日志范围 ALL-入参与回参, REQUEST-入参, RESPONSE-回参
     *
     */


    /**
     * 是否输出日志
     */
    boolean loggable() default true;

    /**
     * 日志信息描述
     */
    String descp() default "";

    /**
     * 日志类别
     */
    LOGTYPE type() default LOGTYPE.DUBBO;

    /**
     * 日志等级
     */
    String level() default "INFO";

    /**
     * 日志输出范围
     * ALL-入参和出参, BEFORE-入参, AFTER-出参
     */
    SCOPE scope() default SCOPE.ALL;
    
    /**
     * 入参输出范围，值为入参变量名，多个则逗号分割。不为空时，入参日志仅打印include中的变量
     */
    String include() default "";

    /**
     * 是否存入数据库
     */
    boolean db() default false;

    /**
     * 是否存入ELK
     */
    boolean elk() default false;
    
    /**
     * 是否输出到控制台
     * @return
     */
    boolean console() default true;
}
