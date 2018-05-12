package com.david.learn.proxy.java;

/**
 * Created by jiakang on 2018/4/25
 *
 * @author jiakang
 */
public class Test {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        MyInvocationHandler<UserService> invocationHandler = new MyInvocationHandler<>(userService);

        UserService userServiceProxy = invocationHandler.getProxy();

        String name = userServiceProxy.getName(1);
//        Consoles.info("getName, id:{}, name:{}", 1, name);
        int age = userServiceProxy.getAge(1);
//        Consoles.info("getAge, id:{}, age:{}", 1, age);
    }
}