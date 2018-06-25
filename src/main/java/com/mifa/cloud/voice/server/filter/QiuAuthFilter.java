//package com.mifa.cloud.voice.server.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
//import lombok.extern.slf4j.Slf4j;
//import moxie.cloud.service.common.constants.BaseErrorKeys;
//import moxie.cloud.service.server.ServerException;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.List;
//
///**
// * Created by sxm on 17/4/4.
// */
//@Component
//@Slf4j
//@Qualifier(value = "qiuAuthFilter")
//public class QiuAuthFilter extends HandlerInterceptorAdapter {
//    private KeyValueDao redisComponent;
//    private Object tokenCache;
//    private String login_name;
//
//
//    public QiuAuthFilter(RedisComponent redisComponent) {
//         this.redisComponent = redisComponent;
//    }
//
//
//    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
//        if(handler instanceof HandlerMethod && !HttpMethod.OPTIONS.name().equalsIgnoreCase(req.getMethod())) {
//            Method method = ((HandlerMethod)handler).getMethod();
//
//            try {
//                this.checkAuthScopes(method, req);
//            } finally {
//                resp.setHeader("Access-Control-Allow-Origin", "*");
//            }
//        }
//
//        return true;
//    }
//
//    private void checkAuthScopes(Method method, HttpServletRequest req) {
//        AuthQScope authScope = (AuthQScope)method.getAnnotation(AuthQScope.class);
//        Enumeration<String> headEnum = req.getHeaderNames();
//        while(headEnum.hasMoreElements())
//        {
//            String str =  headEnum.nextElement();
//            String dot = str + ":" + req.getHeader(str);
//            log.info("head-->{}",dot);
//        }
//        Enumeration<String> paraEnum = req.getParameterNames();
//        while(paraEnum.hasMoreElements())
//        {
//            String str =  paraEnum.nextElement();
//            List<String> list = new ArrayList<String>();
//            String[] strs = req.getParameterValues(str);
//            for(String s : strs)
//            {
//                list.add(s);
//            }
//            String dot =  str + ":" + list;
//            log.info("param--->{}",dot);
//        }
//        if(authScope != null) {
//            String jwtToken = this.getToken(req, "Bearer ");
//
//            log.info("请求头中获取到的动态令牌:{}",jwtToken);
//            log.info("redisComponent:{}",redisComponent);
//            log.info("要获取时key-->:{}",JSON.toJSONString(jwtToken));
//            tokenCache = redisComponent.get(JSON.toJSONString(jwtToken));
//            log.info("tokenCache:{}",tokenCache);
//            if (null==tokenCache || !tokenCache.toString().equals(req.getParameter("loginName"))){
//              throw ServerException.fromKey(BaseErrorKeys.UNAUTHORIZED);
//            }
//            log.info("授权通过");
//        }
//    }
//
//    private String getToken(HttpServletRequest request, String prefix) {
//        String authHeader = request.getHeader("Authorization");
//        if(StringUtils.isBlank(authHeader)) {
//            log.error("请求缺少头部:Authorization");
//            throw ServerException.fromKey("unauthorized", new String[0]);
//        } else {
//            int index = authHeader.indexOf(" ");
//            if(index == -1) {
//                log.error("token[{}]缺少token类型", authHeader);
//                throw ServerException.fromKey("unauthorized", new String[0]);
//            } else {
//                String tokenType = authHeader.substring(0, index);
//                if(!tokenType.equalsIgnoreCase(prefix.trim())) {
//                    log.error("token类型不匹配 期望[{}], 实际[{}]", prefix, tokenType);
//                    throw ServerException.fromKey("unauthorized", new String[0]);
//                } else {
//                    return authHeader.substring(index).trim();
//                }
//            }
//        }
//    }
//
//
//}
