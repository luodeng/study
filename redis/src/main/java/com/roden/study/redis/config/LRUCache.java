package com.roden.study.redis.config;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU (Least recently used) 最近最少使用，如果数据最近被访问过，那么将来被访问的几率也更高。
 * LFU (Least frequently used) 最不经常使用，如果一个数据在最近一段时间内使用次数很少，那么在将来一段时间内被使用的可能性也很小。
 * FIFO (Fist in first out) 先进先出， 如果一个数据最先进入缓存中，则应该最早淘汰掉。
 * @author Roden
 *
 * 查找hash 增删链表
 */
@Slf4j
public class LRUCache<K,V> extends LinkedHashMap<K,V> {
    private int cacheSize;
    public LRUCache(int cacheSize){
        super(16,(float)0.75,true);
        this.cacheSize=cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest){
        return size()>cacheSize;
    }

    public static void main(String[] args) {
        LRUCache<String, Integer> cache = new LRUCache<>(10);
        for (int i = 0; i < 10; i++) {
            cache.put("k" + i, i);
        }
        log.info("all cache :{}",cache);
        cache.get("k3");
        log.info("get k3 :{}", cache);
        cache.get("k4");
        log.info("get k4 :{}", cache);
        cache.get("k4");
        log.info("get k4 :{}", cache);
        cache.put("k" + 10, 10);
        log.info("After running the LRU algorithm cache :{}", cache);
    }
}
