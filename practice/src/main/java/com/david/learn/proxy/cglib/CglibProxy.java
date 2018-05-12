package com.david.learn.proxy.cglib;

import com.david.common.util.Consoles;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by jiakang on 2018/4/25
 *
 * @author jiakang
 */
public class CglibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        String methodName = method.getName();
        Consoles.info("before method:{}", methodName);
        Object o1 = methodProxy.invokeSuper(o, args);
        Consoles.info("after method:{}", methodName);
        return o1;
    }
}