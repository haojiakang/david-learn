package com.david.test;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by jiakang on 2017/12/4.
 */
@Slf4j
public class SubThread extends Thread {
    private final JedisPool jedisPool;
    private final Subscriber subscriber = new Subscriber();
    private final String channel = "mychannel";

    public SubThread(JedisPool jedisPool) {
        super("SubThread");
        this.jedisPool = jedisPool;
    }

    @Override
    public void run() {
        log.info("subscribe redis, thread will be blocked, channel:{}", channel);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.subscribe(subscriber, channel);
        } catch (Exception e) {
            log.warn("subscribe channel error", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }
}
