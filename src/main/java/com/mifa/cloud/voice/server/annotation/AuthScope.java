package com.mifa.cloud.voice.server.annotation;


import com.mifa.cloud.voice.server.commons.enums.AuthQRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthScope {
    AuthQRole[] value() default {AuthQRole.MF_SERVICE};
}
