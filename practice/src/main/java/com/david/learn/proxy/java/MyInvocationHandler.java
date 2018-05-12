package com.david.learn.proxy.java;

import com.david.common.util.Consoles;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by jiakang on 2018/4/25
 *
 * @author jiakang
 */
public class MyInvocationHandler<T> implements InvocationHandler {
    private T target;

    public MyInvocationHandler(T target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    public T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if ("getName".equals(methodName)) {
            Consoles.info("before method:{}", methodName);
            Object result = method.invoke(target, args);
            Consoles.info("after method:{}", methodName);
            return result;
        }
        return method.invoke(target, args);
    }
}