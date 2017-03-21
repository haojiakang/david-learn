package com.david.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by haojk on 3/1/17.
 */
public class SleepUtils {

    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
