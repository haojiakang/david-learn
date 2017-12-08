package com.david.test;

import com.david.learn.dispatch.RedisRateService;
import com.google.common.base.Stopwatch;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by jiakang on 2017/12/6.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dispatch-rate-service.xml")
public class RedisRateServiceTest {

    @Resource
    private RedisRateService redisRateService;

    @Test
    public void test() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 30000; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    redisRateService.acquire(5);
                }
            };
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stopwatch.stop();
        log.info("acquire, cost:{}", stopwatch);
    }
}
