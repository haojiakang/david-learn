package com.david.service.impl;

import com.david.service.Hello;
import org.springframework.stereotype.Component;

/**
 * Created by haojk on 6/22/16.
 */
@Component("hello")
public class HelloImpl implements Hello {

    public void foo() {
        System.out.println("执行Hello组件的foo()方法");
    }

    public int addUser(String name, String pass) {
        System.out.println(String.format("执行Hello组件的addUser添加用户: %s", name));
        return (name+pass).length();
    }
}
