package com.sky.apsect;
import java.time.LocalDateTime;
import java.lang.reflect.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import lombok.extern.slf4j.Slf4j;

/* 
 * 自定义切面，实现公共字段自动填充
 * 
 */

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /* 
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")//直接在mapper包中找AutoFill注解，快
    public void autoFillPointCut(){}

    /* 
     * 前置通知，在通知中进行公共字段的赋值
     */
    
     @Before("autoFillPointCut()")
     public void autoFill(JoinPoint jp){
         log.info("开始进行公共字段自动填充...");

         //获取当前被拦截的方法的数据操作类型
        MethodSignature mSign = (MethodSignature) jp.getSignature(); //方法签名对象
        AutoFill autoFill = mSign.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        
        //获取实体参数,aka employee 实体 etc
        Object[] args  = jp.getArgs();
        if(args == null || args.length == 0){
            return;
        }
        Object entity = args[0];


         //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long curID = BaseContext.getCurrentId();
         //通过反射来进行赋值。根据当前不同的操作类型进行赋值
         if(operationType == OperationType.INSERT){
            //为四个公共字段赋值
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);


                setCreateTime.invoke(entity,now);
                setCreateUser.invoke(entity, curID);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, curID);
            } catch (Exception e) {
                // TODO Caught all types of exceptions
                e.printStackTrace();
            }

         }else if(operationType == OperationType.UPDATE){
            //为两个公共字段赋值
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);


                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, curID);
            } catch (Exception e) {
                // TODO Caught all types of exceptions
                e.printStackTrace();
            }

         }



     }
}
