package com.david.learn.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by jiakang on 2017/11/17.
 */
@Slf4j
@Service
public class Waitress implements Waiter {

    @Override
    public void server() {
        log.info("at service...");
    }

    @Override
    public String sayHello(String name) {
        return "Hello" + name;
    }
}
