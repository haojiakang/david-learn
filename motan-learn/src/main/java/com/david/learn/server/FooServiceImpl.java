package com.david.learn.server;

import com.david.learn.FooService;

/**
 * Created by jiakang on 2017/8/17.
 */
public class FooServiceImpl implements FooService {

    @Override
    public String hello(String name) {
        System.out.println(name + " invoked proxy service");
        return "hello " + name;
    }
}
