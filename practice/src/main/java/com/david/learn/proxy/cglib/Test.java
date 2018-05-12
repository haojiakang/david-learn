package com.david.learn.proxy.cglib;

import com.david.learn.proxy.java.UserService;
import com.david.learn.proxy.java.UserServiceImpl;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Created by jiakang on 2018/4/25
 *
 * @author jiakang
 */
public class Test {

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(cglibProxy);

        UserService userService = (UserService) enhancer.create();
        userService.getName(1);
        userService.getAge(1);
    }
}