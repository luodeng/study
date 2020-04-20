package com.roden.study.redis.jedis;

import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Test:将一个普通方法修饰成一个测试方法
 *      @Test(excepted=xx.class): xx.class表示异常类，表示测试的方法抛出此异常时，认为是正常的测试通过的
 *      @Test(timeout=毫秒数) :测试方法执行时间是否符合预期
 * @BeforeClass：会在所有的方法执行前被执行，static方法
 * @AfterClass：会在所有的方法执行之后进行执行，static方法
 * @Before：会在每一个测试方法被运行前执行一次
 * @After：会在每一个测试方法运行后被执行一次`
 * @Ignore：所修饰的测试方法会被测试运行器忽略
 * @RunWith：可以更改测试运行器org.junit.runner.Runner
 * Parameters：参数化注解
 *
 *
 */
public class JedisCommandsTest {
    static Jedis jedis;
    @BeforeClass
    public static void beforeClass(){
        jedis=JedisUtil.getJedis();
    }

    /**
    *  set key value 设置一个key 值为 value
　　     get key 获得key值得value
     */
    @Test
    public void str(){
        //注意：redis中的Key和Value时区分大小写的，命令不区分大小写， redis是单线程 不适合存储大容量的数据
        jedis.set("hello","328");
        //incr key      ---对应的value 自增1,如果没有这个key值 自动给你创建创建 并赋值为1
        //decr key     ---对应的value 自减1
        //注意：自增的value是可以转成数字的
        jedis.incr("hello");
        System.out.println(jedis.get("hello"));
        System.out.println(jedis.decr("hello"));
        jedis.del("hello");
    }
    /**
     * 相当于1个key 对应一个map
     *   hset key filed value 设置值
     *   hget key filed 　获取值
     */
    @Test
    public void hash(){
        jedis.hset("user","name","zhangsan");
        jedis.hset("user","age","18");
        System.out.println(jedis.hget("user","name"));
        System.out.println(jedis.del("user"));
    }

    /**
   　　List 有顺序可重复
　　  lpush list 1  2  3  4 从左添加元素　
    　rpush list 1 2 3 4    从右添加元素
    　lrange list 0 -1 (从0 到-1 元素查看：也就表示查看所有)
    　lpop list （从左边取，删除）
    　rpop list  (从右边取，删除)
     */
    @Test
    public void list(){
        System.out.println(jedis.lpush("list","1","2"));
        jedis.rpush("list","3","4");
        System.out.println(jedis.lrange("list",0,-1));
        System.out.println(jedis.lpop("list"));
        System.out.println(jedis.rpop("list"));
        jedis.del("list");

    }
    /**
       Set 无顺序，不能重复　
    　 sadd set1 a b c d d (向set1中添加元素) 元素不重复
       smembers set1 （查询元素）
       srem set1 a （删除元素）
     */
    @Test
    public void set(){
        jedis.sadd("set","a","b","c","d","d");
        System.out.println(jedis.smembers("set"));
        System.out.println(jedis.srem("set","a"));
        System.out.println(jedis.smembers("set"));
        jedis.del("set");
    }
    /**
       有顺序，不能重复
    　　适合做排行榜 排序需要一个分数属性
    　　zadd zset1 9 a 8 c 10 d 1 e   （添加元素 zadd key score member ）
    　　(ZRANGE key start stop [WITHSCORES])(查看所有元素：zrange key  0  -1  withscores)
    　　如果要查看分数，加上withscores.
    　　zrange zset1 0 -1 (从小到大)
    　　zrevrange zset1 0 -1 (从大到小)
    　　zincrby zset2 score member (对元素member 增加 score)
     */
    @Test
    //SortedSet（zset）
    public void zset(){
        jedis.zadd("zset1",8,"a");
        jedis.zadd("zset1",4,"b");
        jedis.zadd("zset1",5,"c");
        jedis.zadd("zset1",1,"d");
        System.out.println(jedis.zrange("zset1",0,-1));
        jedis.zadd("zset1",9,"a");
        System.out.println(jedis.zrange("zset1",0,-1));
        System.out.println(jedis.zrangeWithScores("zset1",0,-1));
        System.out.println(jedis.zrevrange("zset1",0,-1));
        System.out.println(jedis.zincrby("zset1",1,"a"));
        System.out.println(jedis.zrevrangeWithScores("zset1",0,-1));
        jedis.del("zset1");
    }
    /**
       expire key second  (设置key的过期时间)
    　　ttl key (查看剩余时间)（-2 表示不存在，-1 表示已被持久化，正数表示剩余的时间）
    　　persist key (清除过期时间，也即是持久化 持久化成功体提示 1 不成功0)。
    　　del key: 删除key
    　　select 0 表示：选择0号数据库。默认是0号数据库

       redis 通配符说明：* 0到任意多个字符  ? 1个字符
     */
    @Test
    public void key() throws InterruptedException {
        jedis.set("hello","world");

        Set<String> set = jedis.keys("*l?");
        set.forEach(obj-> System.out.print(obj+":"+jedis.get(obj)+"\t"));


        jedis.expire("hello",2);
        System.out.println(jedis.get("hello"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println(jedis.get("hello"));

        jedis.set("hello","world");
        jedis.expire("hello",10);
        TimeUnit.SECONDS.sleep(3);
        System.out.println(jedis.ttl("hello"));
        System.out.println(jedis.persist("hello"));
        System.out.println(jedis.ttl("hello"));
        System.out.println(jedis.del("hello"));
    }

}
