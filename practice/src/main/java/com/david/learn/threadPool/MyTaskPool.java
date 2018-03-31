package com.david.learn.threadPool;

import java.util.concurrent.*;

/**
 * Created by jiakang on 2017/12/25.
 */
public class MyTaskPool {

    public static void main(String[] args) {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                2,
                60,
                TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(3)
//                new LinkedBlockingQueue<>()
                new SynchronousQueue<>()
        );

        MyTask task1 = new MyTask(1, "task1");
        MyTask task2 = new MyTask(2, "task2");
        MyTask task3 = new MyTask(3, "task3");
        MyTask task4 = new MyTask(4, "task4");
        MyTask task5 = new MyTask(5, "task5");
        MyTask task6 = new MyTask(6, "task6");

        pool.execute(task1);
        pool.execute(task2);
        pool.execute(task3);
//        pool.execute(task4);
//        pool.execute(task5);
//        pool.execute(task6);
        System.out.println("done");
    }
}
