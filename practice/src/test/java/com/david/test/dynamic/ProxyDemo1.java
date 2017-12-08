package com.david.test.dynamic;

import com.david.learn.dynamic.Waiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by jiakang on 2017/11/17.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
public class ProxyDemo1 {

    @Resource
    private Waiter waitress;

    @Test
    public void demo1() {

        waitress.server();
        log.error("error");
    }
}
