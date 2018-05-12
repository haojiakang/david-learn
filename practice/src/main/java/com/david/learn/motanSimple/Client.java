package com.david.learn.motanSimple;

import cn.hutool.core.lang.Console;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiakang on 2018/5/9
 *
 * @author jiakang
 */
public class Client {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("motan_client.xml");
        FooService service = context.getBean("remoteService", FooService.class);
        String result = service.hello("David");
        Console.log(result);
    }
}