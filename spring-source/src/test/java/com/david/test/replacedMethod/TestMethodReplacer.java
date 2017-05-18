package com.david.test.replacedMethod;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * Created by jiakang on 2017/4/28.
 */
public class TestMethodReplacer implements MethodReplacer {

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) {
        System.out.println("我替换了原有的方法");
        return null;
    }
}
