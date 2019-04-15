package com.yexin.httplib.annotation.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: zengven
 * date: 2019/4/2 14:09
 * desc: Make a GET request.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GET {
    /**
     * A relative or absolute path, or full URL of the endpoint
     */
    String value() default "";
}
