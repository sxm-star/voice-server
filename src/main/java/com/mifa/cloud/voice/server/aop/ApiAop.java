package com.mifa.cloud.voice.server.aop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author 宋烜明
 */
@Slf4j
@Aspect
@Component
public class ApiAop {
    //监控所有接口的耗时
    @Around("execution(* com.mifa.cloud.voice.server.api.controller.*.*(..)) " +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object useTimeLog(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String method = methodSignature.getName();
        String className = pjp.getTarget().getClass().getName();
        RequestMapping classMapping = pjp.getTarget().getClass().getAnnotation(RequestMapping.class);
        String classUrl = "";
        if(null != classMapping) {
            String[] classUrls = classMapping.value();
            if (null != classUrls && classUrls.length > 0) {
                classUrl = classUrls[0];
            }
        }

        String methodUrl = "";
        RequestMapping methodMapping = methodSignature.getMethod().getAnnotation(RequestMapping.class);
        if(null != methodMapping) {
            String[] methodUrls = methodMapping.value();
            if (null != methodUrls && methodUrls.length > 0) {
                methodUrl = methodUrls[0];
            }
        }
        String url = "";
        if(classUrl.length() >= 1 && '/' == (classUrl.charAt(classUrl.length()-1))){
            url = classUrl+methodUrl;
        }else{
            url = classUrl+"/"+methodUrl;
        }
        Object ret = pjp.proceed();
        log.info(String.format("耗时usetime=%s毫秒, 接口url=%s, className=%s, method=%s", (System.currentTimeMillis()-begin),url, className, method));
        return ret;
    }
}
