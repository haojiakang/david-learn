package com.david.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiakang on 2017/12/11.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:redis-rate-limit.xml")
public class RSemaphoreTest {

    @Resource(name = "rateLimitClient")
    private RedissonClient client;

    @Test
    public void testRSemaphore() {
        RSemaphore semaphore = client.getSemaphore("testRSemaphore");
        boolean result = semaphore.trySetPermits(5);
        log.info("trySetPermits result:{}", result);
        try {
            semaphore.acquire();
            Thread.sleep(1000);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testRSemaphore2() {
//        RSemaphore semaphore = client.getSemaphore("testRSemaphore");
//        boolean result = semaphore.trySetPermits(5);
//        log.info("trySetPermits result:{}", result);
//        try {
//            boolean result = semaphore.tryAcquire(5, 1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testPermitExpirableSemaphore() {
        RPermitExpirableSemaphore permitExpirableSemaphore = client.getPermitExpirableSemaphore("testPermitExpirableSemaphore");
        boolean result = permitExpirableSemaphore.trySetPermits(5);
        log.info("trySetPermits result:{}", result);
        try {
            permitExpirableSemaphore.acquire(10, TimeUnit.SECONDS);
            Thread.sleep(TimeUnit.SECONDS.toMillis(30));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
