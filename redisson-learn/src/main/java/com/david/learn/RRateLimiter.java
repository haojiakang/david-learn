package com.david.learn;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.Uninterruptibles;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by jiakang on 2017/12/4.
 */
public abstract class RRateLimiter {
    private final RSleepingStopwatch stopwatch;
    private volatile Object mutexDoNotUseDirectly;

    public static RRateLimiter create(double permitsPerSecond) {
        return create(RSleepingStopwatch.createFromSystemTimer(), permitsPerSecond);
    }

    private static RRateLimiter create(RSleepingStopwatch stopwatch, double permitsPerSecond) {
        RRateLimiter rRateLimiter = new RSmoothRateLimiter.RSmoothBursty(stopwatch, 1.0);
        rRateLimiter.setRate(permitsPerSecond);
        return rRateLimiter;
    }

    public final void setRate(double permitsPerSecond) {
        Preconditions.checkArgument(permitsPerSecond > 0.0 && !Double.isNaN(permitsPerSecond), "rate must be positive");
        synchronized (mutex()) {
            doSetRate(permitsPerSecond, stopwatch.readMicros());
        }
    }

    abstract void doSetRate(double permitsPerSecond, long nowMicros);

    public double acquire() {
        return acquire(1);
    }

    public double acquire(int permits) {
        long microsToWait = reserve(permits);
        stopwatch.sleepMicrosUninterruptibly(microsToWait);
        return 1.0 * microsToWait / SECONDS.toMicros(1L);
    }

    private long reserve(int permits) {
        checkPermits(permits);
        synchronized (mutex()) {
            return reserveAndGetWaitLength(permits, stopwatch.readMicros());
        }
    }

    private long reserveAndGetWaitLength(int permits, long nowMicros) {
        long momentAvailable = reserveEarliestAvailable(permits, nowMicros);
        return Math.max(momentAvailable - nowMicros, 0);
    }

    abstract long reserveEarliestAvailable(int permits, long nowMicros);

    private Object mutex() {
        Object mutex = mutexDoNotUseDirectly;
        if (mutex == null) {
            synchronized (this) {
                mutex = mutexDoNotUseDirectly;
                if (mutex == null) {
                    mutexDoNotUseDirectly = mutex = new Object();
                }
            }
        }
        return mutex;
    }

    //    static final class SmoothBursty extends
    RRateLimiter(RSleepingStopwatch stopwatch) {
        this.stopwatch = stopwatch;
    }

    abstract static class RSleepingStopwatch {

        abstract long readMicros();

        abstract void sleepMicrosUninterruptibly(long micros);

        static RSleepingStopwatch createFromSystemTimer() {
            return new RSleepingStopwatch() {
                final Stopwatch stopwatch = Stopwatch.createStarted();

                @Override
                long readMicros() {
                    return stopwatch.elapsed(MICROSECONDS);
                }

                @Override
                void sleepMicrosUninterruptibly(long micros) {
                    if (micros > 0) {
                        Uninterruptibles.sleepUninterruptibly(micros, MICROSECONDS);
                    }
                }
            };
        }
    }

    private static int checkPermits(int permits) {
        Preconditions.checkArgument(permits > 0, "Request permits (%s) must be positive", permits);
        return permits;
    }
}
