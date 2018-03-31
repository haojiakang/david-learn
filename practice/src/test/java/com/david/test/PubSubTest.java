package com.david.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@Slf4j
public class PubSubTest {

    private Jedis jedisPub = new Jedis("10.73.14.101", 7380);
    private Jedis jedisSub = new Jedis("10.73.14.101", 7380);

    @Test
    public void test() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                jedisSub.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        log.info("onMessage, channel:{}, message:{}", channel, message);
                    }

                    @Override
                    public void onPMessage(String pattern, String channel, String message) {

                    }

                    @Override
                    public void onSubscribe(String channel, int subscribedChannels) {

                    }

                    @Override
                    public void onUnsubscribe(String channel, int subscribedChannels) {

                    }

                    @Override
                    public void onPUnsubscribe(String pattern, int subscribedChannels) {

                    }

                    @Override
                    public void onPSubscribe(String pattern, int subscribedChannels) {

                    }
                }, "redisChat");
            }
        }).start();
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubDir() {
        jedisSub.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                log.info("onMessage, channel:{}, message:{}", channel, message);
            }

            @Override
            public void onPMessage(String pattern, String channel, String message) {

            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {

            }

            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {

            }

            @Override
            public void onPUnsubscribe(String pattern, int subscribedChannels) {

            }

            @Override
            public void onPSubscribe(String pattern, int subscribedChannels) {

            }
        }, "redisChat");
    }

    @Test
    public void testPub() {
        long result = jedisPub.publish("redisChat", "hahahaha");
        log.info("testPub, result:{}", result);
    }

    @Test
    public void testPub2() {
        long result = jedisPub.publish("redisChat", "hahahaha");
        log.info("testPub, result:{}", result);
        for (int i = 0; i < 100; i++) {
            new Jedis("10.73.14.101", 7380).publish("redisChat", "hehehehe");
        }
    }

    @Test
    public void test3() {
        long result = new Jedis("10.73.14.101", 7380).publish("materialConfig", "cao");
        System.out.println(result);
    }
}
