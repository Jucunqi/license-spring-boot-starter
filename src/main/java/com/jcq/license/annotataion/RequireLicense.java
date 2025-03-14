package com.jcq.license.annotataion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果添加这个注解，表示这个方法需要license认证
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLicense {

    boolean value() default true;
}
