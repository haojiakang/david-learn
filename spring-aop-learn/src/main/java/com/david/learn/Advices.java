package com.david.learn;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jiakang on 2017/10/12.
 * 通知类，横切逻辑
 */
@Component
@Aspect
public class Advices {
    private static final Logger logger = LoggerFactory.getLogger(Advices.class);

    @Pointcut("execution(* com.david.learn.Math.*(..))")
    private void pointCut01() {
    }


//    @Before("pointCut01()")
//    public void before(JoinPoint jp) {
//        System.out.println("----------前置通知----------");
//        System.out.println(jp.getSignature().getName());
//    }
//
//    @After("pointCut01()")
//    public void after(JoinPoint jp) {
//        System.out.println("----------最终通知----------");
//    }

    @Around("pointCut01()")
    public Object around(ProceedingJoinPoint pjp) {
        System.out.println(pjp.getSignature().getName());
        long start = System.currentTimeMillis();
        System.out.println("----------环绕前置------------");
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            logger.warn("around name error", throwable);
            System.err.println("around name error, e:" + throwable.getMessage());
        }
        System.out.println("----------环绕后置------------");
        long end = System.currentTimeMillis();
        long cost = end - start;
        System.out.println("cost:" + cost + ", result:" + result);
        return result;
    }
}
