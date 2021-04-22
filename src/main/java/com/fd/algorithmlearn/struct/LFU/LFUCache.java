package com.fd.algorithmlearn.struct.LFU;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * 460. LFU 缓存
 * https://leetcode-cn.com/problems/lfu-cache/
 *
 * @author : zxq
 * @date-time : 2021/4/22 22:52
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
