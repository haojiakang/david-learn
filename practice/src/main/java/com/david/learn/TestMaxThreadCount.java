package com.david.learn;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jiakang on 2017/10/9.
 */
public class TestMaxThreadCount extends Thread {

    private static final AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) {
        while (true) {
            (new TestMaxThreadCount()).start();
        }
    }

    @Override
    public void run() {
        System.out.println(count.incrementAndGet());
        while (true) {
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
