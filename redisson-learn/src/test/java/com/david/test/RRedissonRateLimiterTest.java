package com.david.test;

import com.david.learn.redisson.RRateLimiter;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by jiakang on 2017/12/5.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:redis-rate-limit.xml")
public class RRedissonRateLimiterTest {

    @Resource(name = "rateLimitClient")
    private RedissonClient client;

    @Test
    public void test() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        RRateLimiter rateLimiter = RRateLimiter.create(10.0, client, "hahaha");
//        rateLimiter.acquire();

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    rateLimiter.acquire();
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
        log.info("test method done, cost:{}", stopwatch);
    }

    @Test
    public void testSemaphore() {
        RSemaphore semaphore = client.getSemaphore("redis_rate_limit");
//        boolean setResult = semaphore.trySetPermits(1);
//        log.info("setResult:{}", setResult);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        semaphore.release();
    }

}
