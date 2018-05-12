package com.david.learn.motanSimple;

import cn.hutool.core.lang.Console;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiakang on 2018/5/9
 *
 * @author jiakang
 */
public class Server {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("motan_server.xml");
        Console.log("server start...");
    }
}