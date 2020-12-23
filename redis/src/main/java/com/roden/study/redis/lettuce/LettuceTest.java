package com.roden.study.redis.lettuce;

import com.roden.study.redis.util.ProPertiesUtils;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import io.lettuce.core.resource.DefaultClientResources;
import io.lettuce.core.resource.DirContextDnsResolver;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * @author luo_d
 */
public class LettuceTest {

    private static String host;
    private static Integer port;

    @BeforeClass
    public static void init(){
         host= ProPertiesUtils.getValue("/jedis.properties","jedis.host");
         port= Integer.parseInt(ProPertiesUtils.getValue("/jedis.properties","jedis.port"));
    }

    @Test
    public void connectToRedis () {
        //Syntax: redis://[password@]host[:port][/databaseNumber]
        //RedisClient redisClient = RedisClient.create("redis://password@localhost:6379/0");
        //RedisClient redisClient = RedisClient.create("redis://10.10.1.55:6379/0");

        RedisURI redisURI=null;
        //redisURI=RedisURI.create("redis://10.10.1.55/");
        //redisURI=RedisURI.Builder.redis("10.10.1.55",6379).build();
        //redisURI=new RedisURI("10.10.1.55", 6379, 60, TimeUnit.SECONDS);
        redisURI=new RedisURI(host,port, Duration.ofSeconds(60));

        RedisClient redisClient=RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.set("key", "Hello, Redis!");
        System.out.println(syncCommands.get("key"));
        System.out.println(syncCommands.del("key"));
        System.out.println(syncCommands.set("key","value2", SetArgs.Builder.xx().px(200)));
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void connectToRedisSSL() {
        // Syntax: rediss://[password@]host[:port][/databaseNumber]
        // Adopt the port to the stunnel port in front of your Redis instance
        RedisClient redisClient = RedisClient.create("rediss://password@localhost:6443/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        System.out.println("Connected to Redis using SSL");
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void connectToRedisUsingRedisSentinel() {
        // Syntax: redis-sentinel://[password@]host[:port][,host2[:port2]][/databaseNumber]#sentinelMasterId
        RedisClient redisClient = RedisClient.create("redis-sentinel://localhost:26379,localhost:26380/0#mymaster");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        System.out.println("Connected to Redis using Redis Sentinel");
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void connectToRedisCluster() {
        // Syntax: redis://[password@]host[:port]
        RedisClusterClient redisClient = RedisClusterClient.create("redis://password@localhost:7379");
        StatefulRedisClusterConnection<String, String> connection = redisClient.connect();
        System.out.println("Connected to Redis");
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void ConnectToElastiCacheMaster() {
        // Syntax: redis://[password@]host[:port][/databaseNumber]
        DefaultClientResources clientResources = DefaultClientResources.builder() //
                .dnsResolver(new DirContextDnsResolver()) // Does not cache DNS lookups
                .build();
        RedisClient redisClient = RedisClient.create(clientResources, "redis://password@localhost:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        System.out.println("Connected to Redis");
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void ConnectToMasterSlaveUsingElastiCacheCluster() {
        // Syntax: redis://[password@]host[:port][/databaseNumber]
        RedisClient redisClient = RedisClient.create();
        List<RedisURI> nodes = Arrays.asList(RedisURI.create("redis://host1"), RedisURI.create("redis://host2"),
                RedisURI.create("redis://host3"));
        StatefulRedisMasterSlaveConnection<String, String> connection = MasterSlave.connect(redisClient, StringCodec.UTF8,
                nodes);
        connection.setReadFrom(ReadFrom.MASTER_PREFERRED);
        System.out.println("Connected to Redis");
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void ConnectToRedisClusterSSL() {
        // Syntax: rediss://[password@]host[:port]
        RedisURI redisURI = RedisURI.create("rediss://password@localhost:7379");
        redisURI.setVerifyPeer(false); // depending on your setup, you might want to disable peer verification
        RedisClusterClient redisClient = RedisClusterClient.create(redisURI);
        StatefulRedisClusterConnection<String, String> connection = redisClient.connect();
        System.out.println("Connected to Redis");
        connection.close();
        redisClient.shutdown();
    }
}
