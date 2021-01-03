# study
Redis （REmote DIctionary Server）
 - 命令不区分大小写、key区分
 - help @类型名词 (help @String)
## 数据类型
### String 
订单号 incr key
``
  SET key value [EX seconds] [PX milliseconds] [NX|XX]
  summary: Set the string value of a key
  since: 1.0.0
``
### Hash
Map<String,Map<Object,Object>>
购物车早期
    hset shopcar:uid 3333 1
    hincrby shopcar:uid 3333 1
### List
微信文章订阅公众号
lpush liketitle:id  公从号id 公从号id

### Set
微信抽奖
srndmember set 1 1
spop set 1 
朋友圈点赞 sadd pub:mesid 用户id 用户id
微博好友 并集运算   共同关注 
### Zset
热搜排序
###  BitMap HyperLogLog  GEO 



## Jedis Lettuce
* Jedis 是直连模式，在多个线程间共享一个 Jedis 实例时是线程不安全的，每个线程都去拿自己的 Jedis 实例，当连接数量增多时，物理连接成本就较高了。

* Lettuce的连接是基于Netty的，连接实例可以在多个线程间共享，如果你不知道Netty也没事，大致意思就是一个多线程的应用可以使用同一个连接实例，而不用担心并发线程的数量。通过异步的方式可以让我们更好地利用系统资源。


## 修改底层实现依赖pom配置
``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <exclusions>
        <exclusion>
            <groupId>io.lettuce</groupId>
            <artifactId>lettuce-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```
