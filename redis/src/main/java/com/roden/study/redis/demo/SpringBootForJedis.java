package com.roden.study.redis.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.commands.JedisCommands;
import redis.clients.jedis.params.SetParams;

public class SpringBootForJedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     EX seconds ： 将键的过期时间设置为 seconds 秒。 执行 SET key value EX seconds 的效果等同于执行 SETEX key seconds value 。
     PX milliseconds ： 将键的过期时间设置为 milliseconds 毫秒。 执行 SET key value PX milliseconds 的效果等同于执行 PSETEX key milliseconds value 。
     NX ： 只在键不存在时， 才对键进行设置操作。 执行 SET key value NX 的效果等同于执行 SETNX key value 。
     XX ： 只在键已经存在时， 才对键进行设置操作。
     */

    @Test
    public void getLock(){
        String status = stringRedisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                SetParams setParams=new SetParams().nx().ex(2);
                return commands.set("key", "锁定的资源", setParams);
            }
        });
    }
}
