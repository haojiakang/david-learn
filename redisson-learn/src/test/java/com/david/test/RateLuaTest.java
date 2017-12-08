package com.david.test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jiakang on 2017/12/5.
 */
public class RateLuaTest {

    private Jedis jedis = new Jedis("10.75.0.27", 6380);

    @Test
    public void test() throws IOException {
        List<String> lines = Files.readLines(new File("/Users/jiakang/IdeaProjects/david-learn/redisson/src/main/resources/rate_limit.lua"), Charsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        lines.forEach(line -> sb.append(line).append("\n"));
        String luaScript = sb.toString();

//        String eval = jedis.eval(luaScript);
        String rate_limit_key = "common_message_key_c";
        String maxPermits = "2000";
        String incrByCount = "100";
        Object result = jedis.eval(luaScript, 3, rate_limit_key, maxPermits, incrByCount);
//        jedis.evalsha(eval)
        System.out.println(result);
    }

    @Test
    public void testToNumber() {
        Object eval = jedis.eval("local value = ARGV[1]; return tonumber(value)", 0, "20.38");
        System.out.println(eval);

    }
}
