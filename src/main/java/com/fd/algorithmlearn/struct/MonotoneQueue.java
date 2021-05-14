package com.fd.algorithmlearn.struct;

import java.util.LinkedList;

/**
 * 239. 滑动窗口最大值
 * https://leetcode-cn.com/problems/sliding-window-maximum/
 *
 * @author zhangxinqiang
 * @create 2021/5/14 10:00
 */
public class MonotoneQueue {
    class MonotonicQueue {
        LinkedList<Integer> q = new LinkedList<>();

        public void push(int n) {
            // 将小于 n 的元素全部删除
            while (!q.isEmpty() && q.getLast() < n) {
                q.pollLast();
            }
            // 将小于 n 的元素全部删除
            q.addLast(n);
        }

        public int max() {
            return q.getFirst();
        }

        public void pop(int n) {
            if (n == q.getFirst()) {
                q.pollFirst();
            }
        }
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        MonotonicQueue win = new MonotonicQueue();

        int j = 0;
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            if (i < k - 1) {
                // 先填满窗口的前 k - 1
                win.push(nums[i]);
            } else {
                // 窗口向前滑动，加入新数字
                win.push(nums[i]);
                res[j++] = win.max();
                // 移除旧数字
                win.pop(nums[i - k + 1]);
            }
        }
        return res;
    }
}
