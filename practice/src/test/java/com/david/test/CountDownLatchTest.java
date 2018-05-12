package com.david.test;

import org.jetbrains.annotations.TestOnly;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jiakang on 2018/4/27
 *
 * @author jiakang
 */
public class CountDownLatchTest {

    @Test
    public void test() {
        CountDownLatch countDownLatch = new CountDownLatch(10);

        countDownLatch.countDown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}