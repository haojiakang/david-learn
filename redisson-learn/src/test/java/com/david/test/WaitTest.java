package com.david.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by jiakang on 2017/12/11.
 */
@Slf4j
public class WaitTest {

    private final Object obj1 = new Object();
    private final Object obj2 = new Object();

    @Test
    public void test() {
        Thread thread1 = new Thread("thread1") {
            @Override
            public void run() {
                waitOnObj();
            }
        };
        thread1.start();
        Thread thread2 = new Thread("thread1") {
            @Override
            public void run() {
                waitOnObj();
            }
        };
        thread2.start();
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitOnObj() {
        synchronized (obj1) {
            log.info("i am coming, thread:{}", Thread.currentThread());
            synchronized (obj2) {
                try {
                    obj2.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
