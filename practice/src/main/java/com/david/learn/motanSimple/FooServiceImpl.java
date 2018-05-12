package com.david.learn.motanSimple;

import cn.hutool.core.lang.Console;

/**
 * Created by jiakang on 2018/5/9
 *
 * @author jiakang
 */
public class FooServiceImpl implements FooService {

    @Override
    public String hello(String name) {
        Console.log("{} invoked rpc service", name);
        return "hello " + name;
    }
}