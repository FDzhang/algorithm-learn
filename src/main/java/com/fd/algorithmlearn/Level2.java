package com.fd.algorithmlearn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/leetbook/detail/top-interview-questions-medium/
 * <p>
 * 中级算法
 * LeetCode 官方推出的经典面试题目清单 —— 「中级算法 - 巩固训练」
 *
 * @author zhangxinqiang
 * @create 2021/7/28 10:16
 */
public class Level2 {


    public static void main(String[] args) {
        int[] x = {1,-1,-1,0};
        System.out.println(threeSum(x));
    }

    /**
     * 三数之和
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
     * 注意：答案中不可以包含重复的三元组。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvpj16/
     * <p>
     * 思路：
     * 1、 排序
     * 2、 三个数字，保证第一个数字不重复。调用twoSum()
     * 3、 twoSum(nums, lo, target) : 返回两个值的list， 每个list中的两个值加起来=target (循环范围：从lo到数组结尾)
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            List<List<Integer>> list = twoSum(nums, i + 1, -nums[i]);

            if (!list.isEmpty()) {
                for (List<Integer> integers : list) {
                    integers.add(nums[i]);
                    res.add(integers);
                }
            }

            while (i + 1 < nums.length && nums[i] == nums[i + 1]) {
                i++;
            }
        }

        return res;
    }

    public static List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int lo = start;
        int hi = nums.length - 1;
        while (lo < hi) {
            int left = nums[lo];
            int right = nums[hi];
            if (left + right < target) {
                lo++;
            } else if (left + right > target) {
                hi--;
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(nums[lo]);
                list.add(nums[hi]);

                res.add(list);
                lo++;
                hi--;
            }
            while (nums[lo] == left && lo < hi) {
                lo++;
            }
            while (nums[hi] == right && lo < hi) {
                hi--;
            }
        }
        return res;
    }
}
