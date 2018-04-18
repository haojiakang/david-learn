package com.david.learn;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class TestJson {
    private static final Logger logger = LoggerFactory.getLogger(TestJson.class);

    public static void main(String[] args) {
        Parent raw = new Parent();
        raw.setAge(25);
        raw.setName("Jack");

        String tmp = JSON.toJSONString(raw);
        Sub sub = JSON.parseObject(tmp, Sub.class);
        sub.setEmail("haha@123.com");
        System.out.println(sub);
        logger.info("{}, {}", "haha", "hehe");
    }
}
