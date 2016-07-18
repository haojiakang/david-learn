package com.david.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by haojk on 6/22/16.
 */
//定义一个切面
@Aspect
public class LogAspect {

    //匹配com.jackie.service.impl包下所有类的所有方法的执行作为切入点
    @AfterReturning(returning = "obj", pointcut = "execution(* com.david.service.impl.*.*(..))")
    //声明obj时指定的类型会限制目标方法必须返回制定类型的值或没有返回值
    //此处将obj的类型声明为Object，意味着对目标方法的返回值不加限制
    public void log(Object obj) {
        System.out.println(String.format("获取目标方法返回值: %s", obj));
        System.out.println("模拟日志记录功能...");
    }

}
