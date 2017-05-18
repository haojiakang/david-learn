package com.david.test.replacedMethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiakang on 2017/4/28.
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("replaceMethodTest.xml");
        TestChangeMethod test = (TestChangeMethod) ctx.getBean("testChangeMethod");
        test.changeMe();
    }
}
