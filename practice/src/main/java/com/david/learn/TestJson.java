package com.david.learn;

import com.alibaba.fastjson.JSON;

public class TestJson {

    public static void main(String[] args) {
        Parent raw = new Parent();
        raw.setAge(25);
        raw.setName("Jack");

        String tmp = JSON.toJSONString(raw);
        Sub sub = JSON.parseObject(tmp, Sub.class);
        sub.setEmail("haha@123.com");
        System.out.println(sub);
    }
}
