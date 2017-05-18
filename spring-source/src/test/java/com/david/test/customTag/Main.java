package com.david.test.customTag;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiakang on 2017/4/28.
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("customTagTest.xml");
        User user = (User) ctx.getBean("testBean");
        System.out.println(user);
    }
}
