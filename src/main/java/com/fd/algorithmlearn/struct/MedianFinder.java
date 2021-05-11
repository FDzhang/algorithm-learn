package com.fd.algorithmlearn.struct;

import java.util.PriorityQueue;

/**
 * 295. 数据流的中位数 (困难)
 * https://leetcode-cn.com/problems/find-median-from-data-stream/
 *
 * @author zhangxinqiang
 * @create 2021/5/11 10:24
 */
public class MedianFinder {
    private PriorityQueue<Integer> large;
    private PriorityQueue<Integer> small;

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
        // 小顶堆
        large = new PriorityQueue<>();
        // 大顶堆
        small = new PriorityQueue<>((a, b) -> b - a);
    }

    public void addNum(int num) {
        if (small.size() > large.size()) {
            small.offer(num);
            large.offer(small.poll());
        } else {
            large.offer(num);
            small.offer(large.poll());
        }
    }

    public double findMedian() {
        // 如果元素不一样多，多的那个堆的堆顶元素就是中位数
        if (large.size() < small.size()) {
            return small.peek();
        } else if (large.size() > small.size()) {
            return large.peek();
        }
        // 如果元素一样多，两个堆堆顶的元素的平均数是中位数
        return (large.peek() + small.peek()) / 2.0;
    }
}
