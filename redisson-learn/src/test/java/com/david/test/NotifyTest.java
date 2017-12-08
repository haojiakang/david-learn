package com.david.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by jiakang on 2017/12/8.
 */
@Slf4j
public class NotifyTest {

    private final Object obj = new Object();

    @Test
    public void test() {

        for (int i = 0; i < 10; i++) {
            String waitThreadName = "waitThread_" + i;
            Thread waitThread = getWaitThread(waitThreadName);
            waitThread.start();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread() {
            @Override
            public void run() {
                synchronized (obj) {
                    obj.notifyAll();
                }
            }
        }.start();


        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Thread getWaitThread(String name) {
        Thread waitThread = new Thread(name) {
            @Override
            public void run() {
                synchronized (obj) {
                    try {
                        log.info("waitThread was wait on obj, name:{}", name);
                        obj.wait();
                        log.info("waitThread was awakened, name:{}", name);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return waitThread;
    }
}
