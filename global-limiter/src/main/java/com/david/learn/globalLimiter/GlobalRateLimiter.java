package com.david.learn.globalLimiter;

import com.david.learn.globalLimiter.util.OnlinePoolClient;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiakang on 2017/11/30.
 * 全局限速器，基于Guava的RateLimiter
 * 实现原理：
 *  根据poolNamePrefix查出服务所属的池子一共有多少台机器，然后根据全局最大qps和机器数就可以计算出单机最大qps
 *  根据计算出的单机最大qps组装RateLimiter
 *
 * 使用示例：
 *  RateLimiter rateLimiter = GlobalRateLimiter.calSingleLimiter("openapi_message_flow_proc", 160, 20);
 *  然后就可以使用rateLimiter的各种方法进行限速了：
 *      rateLimiter.acquire() or rateLimiter.acquire(int count)
 *      rateLimiter.tryAcquire() or rateLimiter.tryAcquire(int count)
 * 注意：
 *  如果对GlobalRateLimiter.calSingleLimiter的几种构造方法不是很清楚，则推荐使用
 *      public static RateLimiter calSingleLimiter(String poolNamePrefix, double globalPermitsPerSecond, int defaultPoolIpsCount)；
 *  其他构造方法具体实现方式请阅读Guava RateLimiter的原理
 *
 * @author jiakang
 */
@Slf4j
public class GlobalRateLimiter {

    /**
     * 计算单机所需要的RateLimiter
     * 如果计算失败，则缺省的池子ip数为1
     * 不推荐使用，应该强制设置缺省的池子ip数
     * @param poolNamePrefix 服务所属的池子名称前缀
     * @param globalPermitsPerSecond 全局最大qps
     * @return 计算完成后的单机所需要的RateLimiter
     */
    @Deprecated
    public static RateLimiter calSingleLimiter(String poolNamePrefix, double globalPermitsPerSecond) {
        return calSingleLimiter(poolNamePrefix, globalPermitsPerSecond, 1);
    }

    /**
     * 计算单机所需要的RateLimiter
     * 默认每分钟重设一次Rate
     * @param poolNamePrefix 服务所属的池子名称前缀
     * @param globalPermitsPerSecond 全局最大qps
     * @param defaultPoolIpsCount 如果计算失败，缺省的池子ip数
     * @return 计算完成后的单机所需要的RateLimiter
     */
    public static RateLimiter calSingleLimiter(String poolNamePrefix, double globalPermitsPerSecond, int defaultPoolIpsCount) {
        return calSingleLimiter(poolNamePrefix, globalPermitsPerSecond, defaultPoolIpsCount, true, TimeUnit.MINUTES.toMillis(1));
    }

    /**
     * 计算单机所需要的RateLimiter，无预热时间，内部基于SmoothBursty
     * @param poolNamePrefix 服务所属的池子名称前缀
     * @param globalPermitsPerSecond 全局最大qps
     * @param defaultPoolIpsCount 如果计算失败，缺省的池子ip数
     * @param monitorRate 是否监视并重设rate
     * @param ratePeriodInMillis 监视间隔，以毫秒为单位
     *                           (如果monitorRate为true，需要此值；如果monitorRate为false，此值没有意义)
     * @return 计算完成后的单机所需要的RateLimiter
     */
    public static RateLimiter calSingleLimiter(String poolNamePrefix, double globalPermitsPerSecond, int defaultPoolIpsCount, boolean monitorRate, long ratePeriodInMillis) {
        checkArguments(poolNamePrefix, globalPermitsPerSecond, defaultPoolIpsCount);
        double singePermitsPerSecond = calSingePermitsPerSecond(poolNamePrefix, globalPermitsPerSecond, defaultPoolIpsCount);
        RateLimiter rateLimiter = RateLimiter.create(singePermitsPerSecond);
        if (monitorRate) {
            startMonitorRate(rateLimiter, poolNamePrefix, globalPermitsPerSecond, defaultPoolIpsCount, ratePeriodInMillis);
        }
        return rateLimiter;
    }

    /**
     * 计算单机所需要的RateLimiter，有预热时间，内部基于SmoothWarmingUp
     * @param poolNamePrefix 服务所属的池子名称前缀
     * @param globalPermitsPerSecond 全局最大qps
     * @param defaultPoolIpsCount 如果计算失败，缺省的池子ip数
     * @param warmupPeriod 预热时间
     * @param unit 预热的时间单位
     * @param monitorRate 是否监视并重设rate
     * @param ratePeriodInMillis 监视间隔，以毫秒为单位
     *                           (如果monitorRate为true，需要此值；如果monitorRate为false，此值没有意义)
     * @return 计算完成后的单机所需要的RateLimiter
     */
    public static RateLimiter calSingleLimiter(String poolNamePrefix, double globalPermitsPerSecond, int defaultPoolIpsCount, long warmupPeriod, TimeUnit unit, boolean monitorRate, long ratePeriodInMillis) {
        checkArguments(poolNamePrefix, globalPermitsPerSecond, defaultPoolIpsCount, warmupPeriod, unit);
        double singePermitsPerSecond = calSingePermitsPerSecond(poolNamePrefix, globalPermitsPerSecond, defaultPoolIpsCount);
        RateLimiter rateLimiter = RateLimiter.create(singePermitsPerSecond, warmupPeriod, unit);
        if (monitorRate) {
            startMonitorRate(rateLimiter, poolNamePrefix, globalPermitsPerSecond, defaultPoolIpsCount, ratePeriodInMillis);
        }
        return rateLimiter;
    }

    /**
     * 检查参数
     * @param poolNamePrefix 服务所属的池子名称前缀
     * @param globalPermitsPerSecond 全局最大qps
     * @param defaultPoolIpsCount 如果计算失败，缺省的池子ip数
     * @param warmupPeriod 预热时间
     * @param unit 预热的时间单位
     */
    private static void checkArguments(String poolNamePrefix, double globalPermitsPerSecond, int defaultPoolIpsCount, long warmupPeriod, TimeUnit unit) {
        checkArguments(poolNamePrefix, globalPermitsPerSecond, defaultPoolIpsCount);
        if (warmupPeriod < 0L) {
            throw new IllegalArgumentException(String.format("arguments not permit: warmupPeriod(%s) should not be negative.", warmupPeriod));
        }
    }

    /**
     * 检查参数
     * @param poolNamePrefix 服务所属的池子名称前缀
     * @param globalPermitsPerSecond 全局最大qps
     * @param defaultPoolIpsCount 如果计算失败，缺省的池子ip数
     */
    private static void checkArguments(String poolNamePrefix, double globalPermitsPerSecond, int defaultPoolIpsCount) {
        if (Strings.isEmpty(poolNamePrefix)) {
            throw new IllegalArgumentException(String.format("arguments not permit: poolNamePrefix(%s) should not be empty.", poolNamePrefix));
        }
        if (globalPermitsPerSecond < 0.0) {
            throw new IllegalArgumentException(String.format("arguments not permit: globalPermitsPerSecond(%s) should not be negative.", globalPermitsPerSecond));
        }
        if (defaultPoolIpsCount <= 0) {
            throw new IllegalArgumentException(String.format("arguments not permit: defaultPoolIpsCount(%s) should be positive.", defaultPoolIpsCount));
        }
    }

    /**
     * 计算单机最大qps
     * @param poolNamePrefix 服务所属的池子名称前缀
     * @param globalPermitsPerSecond 全局最大qps
     * @param defaultPoolIpsCount 如果计算失败，缺省的池子ip数
     * @return 计算后的单机最大qps
     */
    private static double calSingePermitsPerSecond(String poolNamePrefix, double globalPermitsPerSecond, int defaultPoolIpsCount) {
        int poolIpsCount;
        try {
            poolIpsCount = OnlinePoolClient.getPoolIpsCount(poolNamePrefix);
        } catch (Exception e) {
            log.warn("calSingePermitsPerSecond error, poolNamePrefix:{}, globalPermitsPerSecond:{}, defaultPoolIpsCount:{}", poolNamePrefix, globalPermitsPerSecond, defaultPoolIpsCount, e);
            poolIpsCount = defaultPoolIpsCount;
        }
        return globalPermitsPerSecond / poolIpsCount;
    }

    /**
     * 开始监控rateLimiter，每ratePeriodInMillis执行一次检查，并设置新的rate
     * @param rateLimiter 被监控的rateLimiter
     * @param poolNamePrefix 监控的池子名称前缀
     * @param globalPermitsPerSecond 全局最大qps
     * @param defaultPoolIpsCount 如果计算失败，缺省的池子ip数
     */
    private static void startMonitorRate(RateLimiter rateLimiter, String poolNamePrefix, double globalPermitsPerSecond, int defaultPoolIpsCount, long ratePeriodInMillis) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                double preSingePermitsPerSecond = rateLimiter.getRate();
                double singePermitsPerSecond = calSingePermitsPerSecond(poolNamePrefix, globalPermitsPerSecond, defaultPoolIpsCount);
                rateLimiter.setRate(singePermitsPerSecond);
                log.info("rateLimiter reset rate, preSingePermitsPerSecond:{}, singePermitsPerSecond:{}", preSingePermitsPerSecond, singePermitsPerSecond);
            }
        }, ratePeriodInMillis, ratePeriodInMillis);
    }
}
