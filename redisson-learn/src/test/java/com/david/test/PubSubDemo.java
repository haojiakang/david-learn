package com.david.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * Created by jiakang on 2017/12/4.
 */
@Slf4j
public class PubSubDemo {

    public static void main(String[] args) {
        String redisIp = "10.75.0.27";
        int port = 6380;
        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), redisIp, port);
        log.info("redis pool is starting, ip:{}, port:{}", redisIp, port);

        SubThread subThread = new SubThread(jedisPool);
        subThread.start();

        Publisher publisher = new Publisher(jedisPool);
        publisher.start();
    }

    @Test
    public void test() {
        Jedis jedis = new Jedis("10.75.0.27", 6380);
        List<String> time = jedis.time();
        System.out.println(time);
        long timeMillis = System.currentTimeMillis();
        System.out.println(timeMillis);
    }
}
