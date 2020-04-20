package com.roden.study.redis.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;

public class LettuceTest {
    @Test
    public void main() {
        RedisClient redisClient = RedisClient.create("redis://@redis.roden.com:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.set("key", "Hello, Redis!");
        System.out.println(syncCommands.get("key"));
        connection.close();
        redisClient.shutdown();
    }
}
