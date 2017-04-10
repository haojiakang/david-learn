package com.david.test.lock;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by haojk on 3/10/17.
 */
public class FairAndUnfairTest {

    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    @Test
    public void fair() {
        System.out.println("fair lock");
        testLock(fairLock);
    }

    @Test
    public void unfair() {
        System.out.println("unfair lock");
        testLock(unfairLock);
    }

    private void testLock(Lock lock) {
        //启动5个job
        for (int i = 0; i < 5; i++) {
            Job job = new Job(lock);
            job.start();
            try {
                job.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Job extends Thread {

        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        public void run() {
            lock.lock();
            try {
                //连续两次打印当前的Thread和等待队列中的Thread(略)
                System.out.println("Lock by [" + Thread.currentThread() + "], Waiting by [" + ((ReentrantLock2) lock).getQueuedThreads() + "]");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Lock by [" + Thread.currentThread() + "], Waiting by [" + ((ReentrantLock2) lock).getQueuedThreads() + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
