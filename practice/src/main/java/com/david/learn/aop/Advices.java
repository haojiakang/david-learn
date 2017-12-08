package com.david.learn.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by jiakang on 2017/10/12.
 * aop切面类
 */
@Slf4j
@Component
@Aspect
public class Advices {

    @Pointcut("execution(* com.david.learn.dynamic.Waiter.server(..))")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) {
        String method = getMethodName(pjp);
        String argsStr = getArgsStr(pjp);

        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = pjp.proceed();
            long end = System.currentTimeMillis();
            long cost = end - start;
            log.info("process method end. method:{}, cost:{}, args:{}, result:{}", method, cost, argsStr, Objects.toString(result));
        } catch (Throwable throwable) {
            log.warn("process method error. method:{}, args:{}", method, argsStr, throwable);
        }
        return result;
    }

    private String getMethodName(ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();

        String fullClassName = pjp.getThis().toString();
        String simpleClassName;
        if (fullClassName.contains(".")) {
            simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        } else {
            simpleClassName = fullClassName;
        }
        if (simpleClassName.contains("@")) {
            simpleClassName = simpleClassName.substring(0, simpleClassName.indexOf("@"));
        }
        return String.join(".", simpleClassName, signature.getName());
    }

    private String getArgsStr(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        return Arrays.toString(args);
    }
}
