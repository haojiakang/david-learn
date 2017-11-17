package com.david.test;

import org.junit.Test;

/**
 * Created by jiakang on 2017/8/22.
 */
public class TestWait {

    Object lock = new Object();

    @Test
    public void testWait() {
        System.out.println("start...");
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("wait done.");
    }

    public static void main(String[] args) {
        Object lock = new Object();
        System.out.println("start...");

        new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("thread wait start");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("thread wait done");
        }).start();
        System.out.println("thread done");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock) {
            lock.notify();
        }
        System.out.println("final done");
    }
}
