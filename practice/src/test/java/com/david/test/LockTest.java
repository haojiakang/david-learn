package com.david.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by jiakang on 2018/4/19
 * 线程安全的+1操作实现种类
 * @author jiakang
 */
public class LockTest extends Thread {

    //整体表现最好，短时间的地并发下比AtomicInteger性能差一点，高并发下性能最高。
    private static LongAdder longAdder = new LongAdder();

    //短时间低并发下，效率比synchronized高，甚至比LongAdder还高出一点，但是高并发下，性能还不如synchronized；不同情况下性能表现很不稳定；可见atomic只适合所竞争不激烈的场景
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    //单线程情况性能最好，随着线程数增加，性能越来越差，但是比cas高
    private static int $synchronized = 0;

    //高并发下，cas性能最差，（貌似unsafe类的方法本身就是线程安全的不需要volatile修饰）
    public static volatile int cas = 0;
    private static long casOffset;

    public static Unsafe UNSAFE;
    private final Object lock = new Object();

    static {
        try {
            @SuppressWarnings("ALL")
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
            casOffset = UNSAFE.staticFieldOffset(LockTest.class.getDeclaredField("cas"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //乐观锁，调用unsafe类实现cas
    public void cas() {
        boolean bl = false;
        int temp;
        while (!bl) {
            temp = cas;
            bl = UNSAFE.compareAndSwapInt(LockTest.class, casOffset, temp, temp + 1);
        }
    }

    //模拟AtomicInteger的实现
    public void atomicInteger() {
        UNSAFE.getAndAddInt(this, casOffset, 1);
    }

    //对a执行+1操作，执行1000次
    public void run() {
        int i = 1;
        while (i <= 10000000) {
            //测试AtomicInteger
            atomicInteger.incrementAndGet();

            //atomicInteger实现;
            atomicInteger();

            //测试LongAdder
            longAdder.increment();

            //测试volatile和cas乐观锁
            cas();

            //测试锁
            synchronized (lock) {
                ++$synchronized;
            }

            i++;
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 100个线程
        int threadCount = 1;
        for (int i = 0; i < threadCount; i++) {
            new LockTest().start();
        }
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(System.currentTimeMillis() - start);
        System.out.println($synchronized);
        System.out.println(atomicInteger);
        System.out.println(longAdder);
        System.out.println(cas);
    }
}