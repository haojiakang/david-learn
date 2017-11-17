package com.david.learn.client;

import com.david.learn.FooService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiakang on 2017/8/17.
 */
public class Client {

//    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:motan_client.xml");
//        FooService fooService = (FooService) applicationContext.getBean("remoteService");
//        System.out.println(fooService.hello("motan"));
//    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:motan_client.xml");
    }
}
