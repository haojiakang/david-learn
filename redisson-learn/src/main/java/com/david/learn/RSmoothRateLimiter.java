package com.david.learn;

import com.google.common.math.LongMath;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.min;

/**
 * Created by jiakang on 2017/12/4.
 */
abstract class RSmoothRateLimiter extends RRateLimiter {

    double storedPermits;
    double maxPermits;
    double stableIntervalMicros;
    private long nextFreeTicketMicros = 0L;

    private RSmoothRateLimiter(RSleepingStopwatch stopwatch) {
        super(stopwatch);
    }

    @Override
    final void doSetRate(double permitsPerSecond, long nowMicros) {
        resync(nowMicros);
        double stableIntervalMicros = TimeUnit.SECONDS.toMicros(1L) / permitsPerSecond;
        this.stableIntervalMicros = stableIntervalMicros;
        doSetRate(permitsPerSecond, stableIntervalMicros);
    }

    abstract void doSetRate(double permitsPerSecond, double stableIntervalMicros);

    @Override
    long reserveEarliestAvailable(int requirePermits, long nowMicros) {
        resync(nowMicros);
        long returnValue = nextFreeTicketMicros;
        double storedPermitsToSpend = Math.min(requirePermits, this.storedPermits);
        double freshPermits = requirePermits - storedPermitsToSpend;
        long waitMicros = storedPermitsToWaitTime(this.storedPermits, storedPermitsToSpend) + (long) (freshPermits * stableIntervalMicros);

        try {
            this.nextFreeTicketMicros = LongMath.checkedAdd(nextFreeTicketMicros, waitMicros);
        } catch (ArithmeticException e) {
            this.nextFreeTicketMicros = Long.MAX_VALUE;
        }
        this.storedPermits -= storedPermitsToSpend;
        return returnValue;
    }

    abstract long storedPermitsToWaitTime(double storedPermits, double permitsToTake);

    static final class RSmoothBursty extends RSmoothRateLimiter {
        final double maxBurstSeconds;

        RSmoothBursty(RSleepingStopwatch stopwatch, double maxBurstSeconds) {
            super(stopwatch);
            this.maxBurstSeconds = maxBurstSeconds;
        }

        @Override
        void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
            double oldMaxPermits = this.maxPermits;
            maxPermits = maxBurstSeconds * permitsPerSecond;
            if (oldMaxPermits == Double.POSITIVE_INFINITY) {
                storedPermits = maxPermits;
            } else {
                storedPermits = (oldMaxPermits == 0.0) ? 0.0 : storedPermits * maxPermits / oldMaxPermits;
            }
        }

        @Override
        long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
            return 0L;
        }

        @Override
        double coolDownIntervalMicros() {
            return stableIntervalMicros;
        }
    }

    abstract double coolDownIntervalMicros();

    private void resync(long nowMicros) {
        if (nowMicros > nextFreeTicketMicros) {
            storedPermits = min(maxPermits,
                    storedPermits
                            + (nowMicros - nextFreeTicketMicros) / coolDownIntervalMicros());
            nextFreeTicketMicros = nowMicros;
        }
    }
}
