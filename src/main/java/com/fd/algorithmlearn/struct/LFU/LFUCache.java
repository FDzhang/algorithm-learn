package com.fd.algorithmlearn.struct.LFU;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * 460. LFU 缓存
 * https://leetcode-cn.com/problems/lfu-cache/
 *
 * @author : zxq
 * @date-time : 2021/4/22 22:52
 * <p>
 * <p>
 * 思路：哈希表
 * 1、频率对应key的映射： mapFK = (f, LinkedList<key>) 队尾是最后加入的，队首是最先加入的
 * 2、key对应频率的映射： mapKF = (k, F)
 * 3、缓存的映射： mapKV = (key, value)
 * 4、操作 get(key) : 若存在，返回value，且频率+1
 * 5、操作 put(k, v):
 * a、若key存在，更新数据，频率+1
 * b、若key不存在，若容量达到上限，删除最久未使用的kv
 * c、若key不存在，存入mapKV, mapKF, mapFK, 且 minF = 1;
 * 6、频率+1
 * a、获取key对应的频率f，在mapKF中更新key对应value为f+1
 * b、在mapFK找到f对应的list，并删除对应的key,
 * c、若mapFK中f对应的list为空，则删除f, 若 f == minF ，则minF++
 * 7、删除最久未使用的kv
 * a、在mapFK中找到minF对应list，取头部的key（mk）,在list中删除mk, 若mapFK中minF对应的list为空，则删除minF, 且minF++
 * b、在mapKF中删除mk
 * c、在mapKV中删除mk
 */
public class LFUCache {
    HashMap<Integer, Integer> keyToVal;
    HashMap<Integer, Integer> keyToFreq;
    HashMap<Integer, LinkedHashSet<Integer>> freqToKeys;

    int minFreq;
    int cap;

    public LFUCache(int cap) {
        keyToVal = new HashMap<>();
        keyToFreq = new HashMap<>();
        freqToKeys = new HashMap<>();

        this.cap = cap;
        this.minFreq = 0;
    }

    public int get(int key) {
        if (!keyToVal.containsKey(key)) {
            return -1;
        }
        increaseFreq(key);
        return keyToVal.get(key);
    }

    public void put(int key, int val) {
        if (this.cap <= 0) {
            return;
        }

        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, val);
            increaseFreq(key);
            return;
        }

        if (this.cap <= keyToVal.size()) {
            removeMinFreqKey();
        }

        keyToVal.put(key, val);
        keyToFreq.put(key, 1);
        freqToKeys.putIfAbsent(1, new LinkedHashSet<>());
        freqToKeys.get(1).add(key);
        this.minFreq = 1;
    }

    /**
     * 淘汰一个 freq 最小的 key
     */
    private void removeMinFreqKey() {
        LinkedHashSet<Integer> keyList = freqToKeys.get(this.minFreq);
        int deleteKey = keyList.iterator().next();
        keyList.remove(deleteKey);

        if (keyList.isEmpty()) {
            freqToKeys.remove(this.minFreq);
        }
        keyToVal.remove(deleteKey);
        keyToFreq.remove(deleteKey);
    }

    /**
     * key 对应的 freq 加一
     */
    private void increaseFreq(int key) {
        int freq = keyToFreq.get(key);

        keyToFreq.put(key, freq + 1);

        freqToKeys.get(freq).remove(key);
        freqToKeys.putIfAbsent(freq + 1, new LinkedHashSet<>());
        freqToKeys.get(freq + 1).add(key);

        if (freqToKeys.get(freq).isEmpty()) {
            freqToKeys.remove(freq);
            if (freq == this.minFreq) {
                this.minFreq++;
            }
        }
    }
}
