package com.raw.nowcoder.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)//作用在方法上的注解
@Retention(RetentionPolicy.RUNTIME)//可以通过运行时产生的字节码文件获取的
public @interface LoginRequired {


}
