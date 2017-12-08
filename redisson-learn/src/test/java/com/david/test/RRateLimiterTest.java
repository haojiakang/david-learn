package com.david.test;

import com.david.learn.RRateLimiter;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by jiakang on 2017/12/4.
 */
@Slf4j
public class RRateLimiterTest {

    @Test
    public void test() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        RRateLimiter rRateLimiter = RRateLimiter.create(10.0);

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    rRateLimiter.acquire();
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
}
