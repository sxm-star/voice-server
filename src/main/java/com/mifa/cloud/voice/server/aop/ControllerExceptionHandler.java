package com.mifa.cloud.voice.server.aop;


import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Administrator on 2018/4/9.
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public CommonResponse exceptionHandler(Throwable e) {

        log.error("异常信息：",e);
        String traceId = MDC.get("CORRELATION_ID");
        if (e instanceof MethodArgumentNotValidException) {
            return CommonResponse.failCommonResponse("400", ((MethodArgumentNotValidException) e).getBindingResult().getFieldError()
                    .getDefaultMessage(),traceId);
        }

        return CommonResponse.failCommonResponse("400", e.getCause() != null ? e.getCause().getMessage() : e.getMessage(),traceId);
    }



}
