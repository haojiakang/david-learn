package com.david.learn.redisson;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Uninterruptibles;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import redis.clients.jedis.Jedis;

import java.net.URL;
import java.util.List;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by jiakang on 2017/12/4.
 */
public abstract class RRateLimiter {
    private final RSleepingStopwatch stopwatch;
    RLock lock;
    Jedis jedis;

    public static RRateLimiter create(double permitsPerSecond, RedissonClient client, String uniqueToken) {
        Jedis jedis = makeJedis(client);
        return create(RSleepingStopwatch.createFromRedis(jedis), permitsPerSecond, client, uniqueToken);
    }

    private static RRateLimiter create(RSleepingStopwatch stopwatch, double permitsPerSecond, RedissonClient client, String uniqueToken) {
        Jedis jedis = makeJedis(client);
        RRateLimiter rRateLimiter = new RSmoothRateLimiter.RSmoothBursty(stopwatch, 1.0, jedis, client, uniqueToken);
        rRateLimiter.setRate(permitsPerSecond, client);
        return rRateLimiter;
    }

    private static Jedis makeJedis(RedissonClient client) {
        URL address = client.getConfig().useSingleServer().getAddress();
        String host = address.getHost();
        int port = address.getPort();
        return new Jedis(host, port);
    }

    public final void setRate(double permitsPerSecond, RedissonClient client) {
        Preconditions.checkArgument(permitsPerSecond > 0.0 && !Double.isNaN(permitsPerSecond), "rate must be positive");
        lock.lock();
        doSetRate(permitsPerSecond, stopwatch.readMicros());
        lock.unlock();
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
        lock.lock();
        long result = reserveAndGetWaitLength(permits, stopwatch.readMicros());
        lock.unlock();
        return result;
    }

    private long reserveAndGetWaitLength(int permits, long nowMicros) {
        long momentAvailable = reserveEarliestAvailable(permits, nowMicros);
        return Math.max(momentAvailable - nowMicros, 0);
    }

    abstract long reserveEarliestAvailable(int permits, long nowMicros);

    RRateLimiter(RSleepingStopwatch stopwatch) {
        this.stopwatch = stopwatch;
    }

    abstract static class RSleepingStopwatch {

        abstract long readMicros();

        abstract void sleepMicrosUninterruptibly(long micros);

        static RSleepingStopwatch createFromRedis(Jedis jedis) {
            return new RSleepingStopwatch() {
                final long startMicros = getMicroSecondTime();

                @Override
                long readMicros() {
                    return getMicroSecondTime() - startMicros;
                }

                @Override
                void sleepMicrosUninterruptibly(long micros) {
                    if (micros > 0) {
                        Uninterruptibles.sleepUninterruptibly(micros, MICROSECONDS);
                    }
                }

                private long getMicroSecondTime() {
                    List<String> timeList = jedis.time();
                    if (timeList.size() == 2) {
                        long seconds = Long.parseLong(timeList.get(0));
                        long micros = Long.parseLong(timeList.get(1));
                        return seconds * 1000000 + micros;
                    }
                    return 0L;
                }
            };
        }
    }

    private static int checkPermits(int permits) {
        Preconditions.checkArgument(permits > 0, "Request permits (%s) must be positive", permits);
        return permits;
    }
}
