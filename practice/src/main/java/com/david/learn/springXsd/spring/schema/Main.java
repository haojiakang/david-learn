package com.david.learn.springXsd.spring.schema;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiakang on 2018/5/7
 *
 * @author jiakang
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
        Student people = (Student) ctx.getBean("student1");
        System.out.println(people);
    }
}