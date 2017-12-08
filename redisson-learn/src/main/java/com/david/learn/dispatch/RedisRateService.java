package com.david.learn.dispatch;

/**
 * Created by jiakang on 2017/12/5.
 * 基于Redis的分布式限速服务
 * @author jiakang
 */
public interface RedisRateService {

    /**
     * 请求1个许可
     * 等同于 acquire(1)
     * @return 许可等待时间 in millis
     */
    long acquire();

    /**
     * 请求许可
     * @param count 请求的许可数
     * @return 许可等待时间 in millis
     */
    long acquire(int count);

    /**
     * 重设最大许可的qps数
     * @param maxPermits 最大许可的qps数
     */
    void reSetMaxPermits(int maxPermits);
}