package com.roden.study.redis.demo;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootForLettuce {
    private static Logger logger = LoggerFactory.getLogger(SpringBootForLettuce.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test1() throws InterruptedException{
        stringRedisTemplate.opsForValue().set("hello","world");
        System.out.println(stringRedisTemplate.opsForValue().get("hello"));
        stringRedisTemplate.expire("hello",2, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(4);
        System.out.println(redisTemplate.opsForValue().get("hello"));
    }
    /**
        EX seconds ： 将键的过期时间设置为 seconds 秒。 执行 SET key value EX seconds 的效果等同于执行 SETEX key seconds value 。
        PX milliseconds ： 将键的过期时间设置为 milliseconds 毫秒。 执行 SET key value PX milliseconds 的效果等同于执行 PSETEX key milliseconds value 。
        NX ： 只在键不存在时， 才对键进行设置操作。 执行 SET key value NX 的效果等同于执行 SETNX key value 。
        XX ： 只在键已经存在时， 才对键进行设置操作。
     */

    @Test
    public void getLock(){
        String key="test_key";
        String value="test_value";
        //set方法
        boolean result = stringRedisTemplate.opsForValue().setIfAbsent("key","value",5, TimeUnit.SECONDS);
        //使用原生连接命令
        String status = stringRedisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                String status = null;
                RedisSerializer<String> stringRedisSerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();

                byte[] keyByte = stringRedisSerializer.serialize(key);
                //springboot 2.0以上的spring-data-redis 包默认使用 lettuce连接包

                //lettuce连接包，集群模式，ex为秒，px为毫秒
                if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                    status = ((RedisAdvancedClusterAsyncCommands) nativeConnection).getStatefulConnection().sync()
                            .set(keyByte,keyByte,SetArgs.Builder.nx().ex(30));
                    logger.debug("lettuce Cluster:---status:"+status);
                }
                //lettuce连接包，单机模式，ex为秒，px为毫秒
                if (nativeConnection instanceof RedisAsyncCommands) {
                    status = ((RedisAsyncCommands ) nativeConnection)
                            .getStatefulConnection().sync()
                            .set(keyByte,keyByte, SetArgs.Builder.nx().ex(30));
                    logger.debug("lettuce single:---status:"+status);
                }
                return status;
            }
        });
        //执行正确status="OK"
        logger.info("getLock:---status:"+status);
    }

}
