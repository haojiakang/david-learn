package com.david.test;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    private static ThreadPoolExecutor firehoseTriggerPool = new ThreadPoolExecutor(2, 2, 10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100000));

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ": hahaha");
                }
            };
            thread.setName("myThread" + i);
            firehoseTriggerPool.execute(thread);
        }
    }
}
