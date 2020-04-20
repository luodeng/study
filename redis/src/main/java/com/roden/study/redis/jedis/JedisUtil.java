package com.roden.study.redis.jedis;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisUtil {
    public static Jedis getJedis() {
        Properties properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream("/jedis.properties");
        try {
            properties.load(inputStream);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        String host = properties.getProperty("jedis.host");
        Object port = properties.getOrDefault("jedis.port", 6379);
        String passowrd = properties.getProperty("jedis.password", "");
        Jedis jedis = new Jedis(host, Integer.parseInt(port.toString()));
        if (!"".equals(passowrd)) {
            jedis.auth(passowrd);
        }
        // 连接其它db(0-15) select index
        //jedis.select(0);
        //查看服务是否运行
        System.out.println(" jedis init ping:" + jedis.ping());
        return jedis;
    }
}
