package com.david.test;

import com.david.learn.globalLimiter.GlobalRateLimiter;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.Uninterruptibles;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiakang on 2017/11/30.
 */
@Slf4j
public class GlobalRateLimiterTest {

    @Test
    public void test() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        RateLimiter rateLimiter = GlobalRateLimiter.calSingleLimiter("openapi_message_flow_proc", 160, 20);
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    rateLimiter.acquire();
//                    log.info("acquire permit passed");
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
        log.info("pass time:{}", stopwatch);

//        Uninterruptibles.sleepUninterruptibly(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }
}
