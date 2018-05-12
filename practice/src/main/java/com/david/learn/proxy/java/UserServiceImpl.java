package com.david.learn.proxy.java;

import com.david.common.util.Consoles;

/**
 * Created by jiakang on 2018/4/25
 *
 * @author jiakang
 */
public class UserServiceImpl implements UserService {
    @Override
    public String getName(int id) {
        String name = "David";
        Consoles.info("getName, id:{}, name:{}", id, name);
        return name;
    }

    @Override
    public int getAge(int id) {
        int age = 23;
        Consoles.info("getAge, id:{}, age:{}", id, age);
        return age;
    }
}