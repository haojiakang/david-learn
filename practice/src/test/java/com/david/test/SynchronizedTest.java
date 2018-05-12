package com.david.test;

import com.david.common.util.Consoles;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiakang on 2018/4/19
 *
 * @author jiakang
 */
public class SynchronizedTest {

//    private static final SynchronizedTest synchronizedTest = new SynchronizedTest();

    public static synchronized void staticSynchronized() {
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        Consoles.info("staticSynchronized, Thread:{}", Thread.currentThread());
    }

    public static synchronized void staticSynchronized1() {
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        Consoles.info("staticSynchronized1, Thread:{}", Thread.currentThread());
    }

    public synchronized void objectSynchronized() {
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        Consoles.info("objectSynchronized, Thread:{}", Thread.currentThread());
    }

    public synchronized void objectSynchronized1() {
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        Consoles.info("objectSynchronized1, Thread:{}", Thread.currentThread());
    }

    public class ObjectThread extends Thread {

        @Override
        public void run() {
            objectSynchronized();
        }
    }

    public static void main(String[] args) {
//        Thread thread1 = new Thread() {
//            @Override
//            public void run() {
//                staticSynchronized();
//            }
//        };
//        Thread thread2 = new Thread() {
//            @Override
//            public void run() {
//                staticSynchronized1();
//            }
//        };

        SynchronizedTest synchronizedTest = new SynchronizedTest();
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                synchronizedTest.objectSynchronized();
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                synchronizedTest.objectSynchronized1();
            }
        };
        thread1.start();
        thread2.start();
    }
}