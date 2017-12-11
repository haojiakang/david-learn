package com.david.test;

import com.david.learn.globalLimiter.RedisRateService;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiakang on 2017/12/6.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dispatch-rate-service.xml")
public class RedisRateServiceTest {

    @Resource
    private RedisRateService redisRateService;
    @Resource(name = "rateLimitClient")
    private RedissonClient client;

    @Test
    public void test() {
        doAcquire(new AcquireAdapter() {
            @Override
            public void acquire() {
                redisRateService.acquire(5);
            }
        });
    }

    @Test
    public void testLimitBySemaphore() {
        RPermitExpirableSemaphore testLimitBySemaphore = client.getPermitExpirableSemaphore("testLimitBySemaphore");
        boolean result = testLimitBySemaphore.trySetPermits(3000);
        log.info("trySetPermits result, result:{}", result);
        doAcquire(new AcquireAdapter() {
            @Override
            public void acquire() {
                try {
                    for (int i = 0; i < 5; i++) {
                        testLimitBySemaphore.acquire(1, TimeUnit.SECONDS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void testLimitByTimeoutSemaphore() {
        RSemaphore testTimeoutSemaphore = client.getSemaphore("testTimeoutSemaphore");
        boolean result = testTimeoutSemaphore.trySetPermits(3000);
        log.info("trySetPermits result, result:{}", result);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private URL address = client.getConfig().useSingleServer().getAddress();
            private Jedis jedis = new Jedis(address.getHost(), address.getPort());

            @Override
            public void run() {
                jedis.set("testTimeoutSemaphore", "3000");
            }
        }, TimeUnit.SECONDS.toMillis(1), TimeUnit.SECONDS.toMillis(1));

        doAcquire(new AcquireAdapter() {
            @Override
            public void acquire() {
                try {
                    boolean acquire = testTimeoutSemaphore.tryAcquire(5, 1, TimeUnit.SECONDS);
                    int count = 1;
                    while (!acquire) {
                        acquire = testTimeoutSemaphore.tryAcquire(5, 1, TimeUnit.SECONDS);
                        count ++;
                    }
//                    log.info("acquire by 5 passed, count:{}", count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doAcquire(AcquireAdapter adapter) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 30000; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
//                    redisRateService.acquire(5);
                    adapter.acquire();
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

    private interface AcquireAdapter {
        void acquire();
    }
}
