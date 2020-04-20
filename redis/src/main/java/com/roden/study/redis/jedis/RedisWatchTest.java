package com.roden.study.redis.jedis;
/**
 * redis测试抢购
 *
 */

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisWatchTest implements Runnable {
    String watchkeys = "watchkeys";// 监视keys
    Jedis jedis = JedisUtil.getJedis();
    @Override
    public void run() {
        try {
            jedis.watch(watchkeys);// watchkeys
            String val = jedis.get(watchkeys);
            int valint = Integer.valueOf(val);
            String userifo = UUID.randomUUID().toString();
            if (valint < 10) {
                Transaction tx = jedis.multi();// 开启事务
                tx.incr("watchkeys");
                List<Object> list = tx.exec();// 提交事务，如果此时watchkeys被改动了，则返回null
                if (list != null) {
                    System.out.println("用户：" + userifo + "抢购成功，当前抢购成功人数:" + (valint + 1));
                    /* 抢购成功业务逻辑 */
                    jedis.sadd("setsucc", userifo);
                } else {
                    System.out.println("用户：" + userifo + "抢购失败");
                    /* 抢购失败业务逻辑 */
                    jedis.sadd("setfail", userifo);
                }
            } else {
                System.out.println("用户：" + userifo + "抢购失败");
                jedis.sadd("setfail", userifo);
                // Thread.sleep(500);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

    }

    public static void main(String[] args) {
        final String watchkeys = "watchkeys";
        ExecutorService executor = Executors.newFixedThreadPool(20);
        //清空之前数据
        final Jedis jedis = JedisUtil.getJedis();
        jedis.set(watchkeys, "0");// 重置watchkeys为0
        jedis.del("setsucc", "setfail");// 清空抢成功的，与没有成功的
        jedis.close();

        for (int i = 0; i < 100; i++) {// 测试一百人同时访问
            executor.execute(new RedisWatchTest());
        }
        executor.shutdown();
    }

}