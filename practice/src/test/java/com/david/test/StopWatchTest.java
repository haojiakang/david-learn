package com.david.test;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.perf4j.LoggingStopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiakang on 2017/11/29.
 */
@Slf4j
public class StopWatchTest {

    @Test
    public void test() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopwatch.stop();
        stopwatch.start();
        stopwatch.stop();
        System.out.println(stopwatch);
    }

    @Test
    public void test2() {
        StopWatch stopWatch = new StopWatch("test2 StopWatch");
        System.out.println(stopWatch);

        stopWatch.start("task1");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();

        stopWatch.start("task2");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch);
    }

    @Test
    public void test3() {
        org.perf4j.StopWatch stopWatch = new org.perf4j.StopWatch("tag0", "message0");
//        stopWatch.start("tag1", "message1");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String lap2 = stopWatch.lap("tag2", "message2");
        System.out.println(lap2);
        stopWatch.stop("tag3", "message3");
        System.out.println(stopWatch);
    }

    @Test
    public void test4() {
        org.perf4j.StopWatch stopWatch = new LoggingStopWatch();
        stopWatch.start("tag1", "message1");
        stopWatch.stop();
//        System.out.println(stopWatch);
    }

    @Test
    public void test5() {
        org.perf4j.StopWatch stopWatch = new Slf4JStopWatch(log);
        stopWatch.start("tag1", "message1");
        stopWatch.stop();

        stopWatch.start("tag2", "message2");
        stopWatch.stop();
    }
}
