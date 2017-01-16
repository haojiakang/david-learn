package com.david.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by haojk on 12/23/16.
 */
public class DynamicProxy {

    public static <T> T newProxyInstance(Subject subject) {
        //获得ClassLoader
        ClassLoader loader = subject.getClass().getClassLoader();
        //获得接口数组
        Class<?>[] interfaces = subject.getClass().getInterfaces();
        //定义一个Handler
        InvocationHandler handler = new MyInvocationHandler(subject);
        //执行目标,并返回结果
        return (T) Proxy.newProxyInstance(loader, interfaces, handler);
    }
}
