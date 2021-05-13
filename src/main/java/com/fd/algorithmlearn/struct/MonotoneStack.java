package com.fd.algorithmlearn.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 496. 下一个更大元素 I
 * https://leetcode-cn.com/problems/next-greater-element-i/
 * <p>
 * 739. 每日温度
 * https://leetcode-cn.com/problems/daily-temperatures/
 * <p>
 * 503. 下一个更大元素 II
 * https://leetcode-cn.com/problems/next-greater-element-ii/
 *
 * @author zhangxinqiang
 * @create 2021/5/13 10:02
 */
public class MonotoneStack {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = nextGreaterElement(nums2);
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }

    /**
     * 单调栈
     */
    public HashMap<Integer, Integer> nextGreaterElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>(nums.length);
        Stack<Integer> s = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!s.empty() && s.peek() <= nums[i]) {
                s.pop();
            }
            int val = s.empty() ? -1 : s.peek();
            s.push(nums[i]);
            map.put(nums[i], val);
        }
        return map;
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int[] res = new int[temperatures.length];
        Stack<Integer> s = new Stack<>();
        for (int i = temperatures.length - 1; i >= 0; i--) {
            while (!s.empty() && temperatures[s.peek()] <= temperatures[i]) {
                s.pop();
            }
            res[i] = s.empty() ? 0 : (s.peek() - i);
            s.push(i);
        }
        return res;
    }

    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> s = new Stack<>();
        for (int i = 2 * n - 1; i >= 0; i--) {
            while (!s.empty() && s.peek() <= nums[i % n]) {
                s.pop();
            }
            res[i % n] = s.empty() ? -1 : s.peek();
            s.push(nums[i % n]);
        }
        return res;
    }

}
