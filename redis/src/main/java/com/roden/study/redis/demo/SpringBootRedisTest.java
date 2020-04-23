package com.roden.study.redis.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * opsForValue 对应 String（字符串）
 * opsForList 对应 List（列表）
 * opsForSet 对应 Set（集合）
 * opsForZSet 对应 ZSet（有序集合）
 * opsForHash 对应 Hash（哈希）
 * opsForGeo 对应 Geo（地理位置）
 * opsForHyperLogLog 对应 HyperLogLog（基数估算）
 * opsForCluster 对应 Cluster（集群操作）
 * opsForStream
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestConfiguration
public class SpringBootRedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().set("rKey",423L);
        stringRedisTemplate.opsForValue().set("TestKey", "TestValue");

    }



    /**
     * Spring Boot 提供了 RedisConnection 抽象，用于底层 API 操作 Redis

     */
    @Test
    public void doInRedis() {
        Object status=redisTemplate.execute(new RedisCallback<Object>() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection connection){
                return connection.setNX("cKey".getBytes(), "value".getBytes());
            }
        });
        System.out.println(status);
    }

    /**
     *  RedisTemplate 支持 Pub/Sub 功能，发送消息使用如下的示例代码：
     *
     */
    @Test
    public void message(){
        stringRedisTemplate.convertAndSend("DefaultChannel", "hello, world");
    }
}
