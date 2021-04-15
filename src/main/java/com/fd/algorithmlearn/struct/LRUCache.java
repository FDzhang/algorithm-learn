package com.fd.algorithmlearn.struct;

import java.util.HashMap;

/**
 * 自制轮子的方式 实现 LRU 算法 （一种缓存淘汰策略）
 *
 * @author ：zxq
 * @date ：Created in 2021/4/15 10:34
 */

public class LRUCache {
    /**
     * key -> Node(key, val)
     */
    private HashMap<Integer, Node> map;
    /**
     * Node(k1, v1) <-> Node(k2, v2)...
     */
    private DoubleList cache;
    /**
     * 最大容量
     */
    private int cap;

    public LRUCache(int capacity) {
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }

    /**
     * 将某个 key 提升为最近使用的
     */
    private void makeRecently(int key) {
        Node x = map.get(key);
        // 先从链表中删除这个节点
        cache.remove(x);
        // 重新插到队尾
        cache.addLast(x);
    }

    /**
     * 添加最近使用的元素
     */
    private void addRecently(int key, int val) {
        Node x = new Node(key, val);
        // 链表尾部就是最近使用的元素
        cache.addLast(x);
        // 别忘了在 map 中添加 key 的映射
        map.put(key, x);
    }

    /**
     * 删除某一个 key
     */
    private void deleteKey(int key) {
        Node x = map.get(key);
        // 从链表中删除
        cache.remove(x);
        // 从 map 中删除
        map.remove(x);
    }

    /**
     * 删除最久未使用的元素
     */
    private void removeLeastRecently() {
        // 链表头部的第一个元素就是最久未使用的
        Node deleteNode = cache.removeFirst();
        // 同时别忘了从 map 中删除它的 key
        int deleteKey = deleteNode.key;
        map.remove(deleteKey);
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        // 将该数据提升为最近使用的
        makeRecently(key);
        return map.get(key).val;
    }

    public void put(int key, int val) {
        if (map.containsKey(key)) {
            // 删除旧的数据
            deleteKey(key);
            // 新插入的数据为最近使用的数据
            addRecently(key, val);
            return;
        }
        if (cap == cache.size()) {
            // 删除最久未使用的元素
            removeLeastRecently();
        }
        // 添加为最近使用的元素
        addRecently(key, val);
    }
}
