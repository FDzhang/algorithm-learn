package com.fd.algorithmlearn.struct;

import java.util.LinkedHashMap;

/**
 * 使用 Java 的内置类型 LinkedHashMap 来实现 LRU 算法
 * <p>
 * 146.LRU缓存机制（中等）
 * https://leetcode-cn.com/problems/lru-cache/
 *
 * @author ：zxq
 * @date ：Created in 2021/4/15 10:48
 */

public class LRUCache1 {
    int cap;
    LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();

    public LRUCache1(int capacity) {
        this.cap = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        // 将key变为最近使用
        makeRecently(key);
        return cache.get(key);
    }

    public void put(int key, int val) {
        if (cache.containsKey(key)) {
            // 修改key的值
            cache.put(key, val);
            // 将key变为最近使用
            makeRecently(key);
            return;
        }

        if (cache.size() >= this.cap) {
            // 链表头部就是最久未使用的 key
            Integer oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        // 将新的 key 添加链表尾部
        cache.put(key, val);
    }

    private void makeRecently(int key) {
        int val = cache.get(key);
        // 删除 key，重新插入到队尾
        cache.remove(key);
        cache.put(key, val);
    }

}
