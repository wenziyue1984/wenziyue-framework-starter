package com.wenziyue.framework.annotation;

import java.lang.annotation.*;

/**
 * @author wenziyue
 */
@Target({ElementType.TYPE, ElementType.METHOD}) //表示这个注解可以加在类（Controller）或方法上。
@Retention(RetentionPolicy.RUNTIME) //表示这个注解在运行时生效，aop可以反射获取。
@Documented
@Inherited
public @interface ResponseResult {
}
