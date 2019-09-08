package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//指定注解加在哪
@Retention(RetentionPolicy.RUNTIME)//什么时候生效
public @interface AddCache {
}
