package com.david.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by jiakang on 2017/12/1.
 */
@Slf4j
public class RedissonTest {

    private RedissonClient client;

    @Before
    public void initClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("10.75.0.27:6380");
        client = Redisson.create(config);
    }

    @Test
    public void test() {
        try {
            String configStr = client.getConfig().toJSON();
            log.info("configStr:{}", configStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        RAtomicLong longObject = client.getAtomicLong("testLong");
        boolean result = longObject.compareAndSet(3, 401);
//        longObject.
        log.info("compareAndSet result:{}", result);
    }

//    @Test
//    public void testTime() {
//        client.getTopic()
//    }

    @Test
    public void testRSemaphore() {
        RSemaphore semaphore = client.getSemaphore("semaphore");
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSemaphore() {
        Semaphore semaphore = new Semaphore(5);
        ExecutorService exec = Executors.newCachedThreadPool();

        for (int index = 0; index < 20; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取许可
                        semaphore.acquire();
                        log.info("Accessing:{}", NO);
                        Thread.sleep((long) (Math.random() * 10000));
                        //访问完后，释放
                        semaphore.release();
                        log.info("-----------------, availablePermits:{}", semaphore.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            exec.execute(run);
        }
        //退出线程池
        exec.shutdown();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
