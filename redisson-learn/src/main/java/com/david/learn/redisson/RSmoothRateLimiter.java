package com.david.learn.redisson;

import com.google.common.math.LongMath;
import org.redisson.api.RAtomicDouble;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.min;

/**
 * Created by jiakang on 2017/12/4.
 */
abstract class RSmoothRateLimiter extends RRateLimiter {

    RedissonClient client;
    String uniqueToken;
    RAtomicDouble storedPermits;
    RAtomicDouble maxPermits;
    RAtomicDouble stableIntervalMicros;
    private RAtomicLong nextFreeTicketMicros;

    void initParams() {
        storedPermits = client.getAtomicDouble(withUniqueToken("storedPermits"));
        maxPermits = client.getAtomicDouble(withUniqueToken("maxPermits"));
        stableIntervalMicros = client.getAtomicDouble(withUniqueToken("stableIntervalMicros"));
        nextFreeTicketMicros = client.getAtomicLong(withUniqueToken("nextFreeTicketMicros"));
        lock = client.getLock(withUniqueToken("rateLimitLock"));
    }

    private String withUniqueToken(String str) {
        if (str.endsWith("_")) {
            return str + uniqueToken;
        }
        return str + "_" + uniqueToken;
    }

    private RSmoothRateLimiter(RSleepingStopwatch stopwatch) {
        super(stopwatch);
    }

    @Override
    final void doSetRate(double permitsPerSecond, long nowMicros) {
        resync(nowMicros);
        double stableIntervalMicros = TimeUnit.SECONDS.toMicros(1L) / permitsPerSecond;
        this.stableIntervalMicros.set(stableIntervalMicros);
        doSetRate(permitsPerSecond, stableIntervalMicros);
    }

    abstract void doSetRate(double permitsPerSecond, double stableIntervalMicros);

    @Override
    long reserveEarliestAvailable(int requirePermits, long nowMicros) {
        resync(nowMicros);
        long returnValue = nextFreeTicketMicros.get();
        double storedPermitsToSpend = Math.min(requirePermits, this.storedPermits.get());
        double freshPermits = requirePermits - storedPermitsToSpend;
        long waitMicros = storedPermitsToWaitTime(this.storedPermits.get(), storedPermitsToSpend) + (long) (freshPermits * stableIntervalMicros.get());

        try {
            this.nextFreeTicketMicros.set(LongMath.checkedAdd(nextFreeTicketMicros.get(), waitMicros));
        } catch (ArithmeticException e) {
            this.nextFreeTicketMicros.set(Long.MAX_VALUE);
        }
        this.storedPermits.getAndAdd(-storedPermitsToSpend);
        return returnValue;
    }

    abstract long storedPermitsToWaitTime(double storedPermits, double permitsToTake);

    static final class RSmoothBursty extends RSmoothRateLimiter {
        final double maxBurstSeconds;

        RSmoothBursty(RSleepingStopwatch stopwatch, double maxBurstSeconds, Jedis jedis, RedissonClient client, String uniqueToken) {
            super(stopwatch);
            this.maxBurstSeconds = maxBurstSeconds;
            this.client = client;
            this.uniqueToken = uniqueToken;
            this.jedis = jedis;
            initParams();
        }

        @Override
        void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
            double oldMaxPermits = this.maxPermits.get();
            maxPermits.set(maxBurstSeconds * permitsPerSecond);
            if (oldMaxPermits == Double.POSITIVE_INFINITY) {
                storedPermits.set(maxPermits.get());
            } else {
                storedPermits.set((oldMaxPermits == 0.0) ? 0.0 : storedPermits.get() * maxPermits.get() / oldMaxPermits);
            }
        }

        @Override
        long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
            return 0L;
        }

        @Override
        double coolDownIntervalMicros() {
            return stableIntervalMicros.get();
        }
    }

    abstract double coolDownIntervalMicros();

    private void resync(long nowMicros) {
        if (nowMicros > nextFreeTicketMicros.get()) {
            storedPermits.set(min(maxPermits.get(),
                    storedPermits.get()
                            + (nowMicros - nextFreeTicketMicros.get()) / coolDownIntervalMicros()));
            nextFreeTicketMicros.set(nowMicros);
        }
    }
}
