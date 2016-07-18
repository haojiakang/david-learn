package com.david.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by haojk on 6/22/16.
 */
//定义一个切面
@Aspect
public class AuthAspect {

    //匹配com.jackie.service.impl包下所有类的所有方法的执行作为切入点
    @Before("execution(* com.david.service.impl.*.*(..))")
    public void authority(){
        System.out.println("模拟进行权限检查...");
    }

}
