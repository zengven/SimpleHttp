package com.yexin.httplib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: zengven
 * date: 2019/4/2 14:53
 * desc: Named key/value pairs for a form-encoded request.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    String value();

    /**
     * Specifies whether the {@linkplain #value() name} and value are already URL encoded.
     */
    boolean encoded() default false;
}
