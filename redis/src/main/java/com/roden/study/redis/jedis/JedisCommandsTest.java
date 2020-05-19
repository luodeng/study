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
　　  * get key 获得key值得value

     Set KEY VALUE //设置给定key的值，如果key不存在则添加，如果存在则更新值
     SETEX key Seconds VALUE //为指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX命令将会替换旧的值。
     Get KEY //获取指定 key 的值
     GETRANGE KEY start end //获取存储在指定 key 中字符串的子字符串。字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     GETSET KEY VALUE //设置给定key的值，并返回旧值
     SETNX KEY VALUE //给定key不存在时，为key设定值
     MSET key1 value1 key2 value2 .. keyN valueN //同时设定多个值
     INCR KEY //给定key的值+1 key对应的值不是整型会报错,value is not an integer or out of range
     DECR KEY //给定key的值-1 key对应的值不是整型会报错,value is not an integer or out of range
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
     *
     * Hset key field value //设置给定key 的field的value
     * Hmset Key field1 value1 field2 value 2 //同时设置多个 field value
     * Hget key field //获取key中指定feild的value值
     * Hmget key feild1 feild2 //获取多个feild的值
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
    　lrange list 0 -1 (从0 到-1 元素查看：也就表示查看所有，lrange右闭合，也就是包含end下标所代表的项)
    　lpop list （从左边取，删除）
    　rpop list  (从右边取，删除)


     Lpush key value1 value2  //将一个或多个值插入List的头部(左)
     Rpush key value1 value2  //将一个或多个值插入List的尾部(右)
     Lpop key  //移除列表头的元素
     Rpop key  //移除列表尾元素
     BLpop key timeut//移除列表头的元素，如果没有元素会阻塞直到发现可弹出元素或者超时
     LREM key count value  //根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素。 count> 0 从列表头向尾检索移除count个与value相等的元素并移除；count < 0 从尾向头检索移除count个与value相等的元素并移除；count = 0，移除所有与value相等的元素
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

     SADD KEY member1 member2 //向集合中添加一个或多个成员
     SPOP KEY //移除并返回集合中的一个随机元素
     SRem KEY member1 member2 //移除集合中一个或多个成员
     SInter key1 key2   //返回给定集合的交集
     SUnion key1 key2 //返回给定集合的并集
     SDiff key1 key2 //返回给定集合的差集
     Smembers Key //返回集合中所有member
     Sismember Key member //判断member是否在集合中
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

     ZAdd Key score1 member1 score2 member2 //向一个集合添加成员，如果成员存在就更新分数
     ZCount Key min max //获取指定分数区号的成员数量，包括min和max
     ZRANGE Key start stop [WITHSCORES]//获取指定下标范围的有序成员列表 按照分数排序
     ZREVRANGE Key start stop [WITHSCORES]//获取指定下标范围的有序成员列表 按照分数倒序
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
