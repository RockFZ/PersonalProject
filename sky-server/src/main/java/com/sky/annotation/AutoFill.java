package com.sky.annotation;

import com.sky.enumeration.OperationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/* 
 * 
 * 自定义注释，用于表示某种方法需要进行功能字段自动填充处理
 * 
 */

 @Target(ElementType.METHOD)
 @Retention(RetentionPolicy.RUNTIME)
 public @interface AutoFill {
     OperationType value();//OperationType类是一个枚举类，里面定义了两个属性，insert和update
 }