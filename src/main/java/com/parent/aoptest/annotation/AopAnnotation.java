package com.parent.aoptest.annotation;

import java.lang.annotation.*;

/**
 * 自定义AOP注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopAnnotation {

    String value() default "";

}
