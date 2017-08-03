package com.david.learn.test;

import org.junit.Test;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jiakang on 2017/5/31.
 */
public class NewDate {

    @Test
    public void testDate() throws InterruptedException {
        Instant start = Instant.now();
//        runAlogorithm();runAlogorithmΩ
        Thread.sleep(1000);
        Instant end = Instant.now();
        Duration timeElasped = Duration.between(start, end);
        long millis = timeElasped.toMillis();
        System.out.println("millis: " + millis);
    }

    @Test
    public void testLocalDate() {
        LocalDate alonzosBirthday = LocalDate.of(1903, 6, 14);
        alonzosBirthday = LocalDate.of(1903, Month.JUNE, 14);

        LocalDate programmersDay = LocalDate.of(2014, 1, 1).plusDays(255);

    }
}
