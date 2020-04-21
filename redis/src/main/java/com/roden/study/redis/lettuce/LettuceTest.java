package com.roden.study.redis.lettuce;

import com.roden.study.redis.util.ProPertiesUtils;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;

public class LettuceTest {
    @Test
    public void test1() {
        //RedisClient redisClient = RedisClient.create("redis://@redis.roden.com:6379/0");
        String host= ProPertiesUtils.getValue("/jedis.properties","jedis.host");
        int port= Integer.parseInt(ProPertiesUtils.getValue("/jedis.properties","jedis.port"));
        RedisClient redisClient=RedisClient.create(RedisURI.create(host,port));
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.set("key", "Hello, Redis!");
        System.out.println(syncCommands.get("key"));
        connection.close();
        redisClient.shutdown();
    }
}
