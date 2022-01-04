package com.fd.algorithmlearn;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 初级算法
 * LeetCode 官方推出的经典面试题目清单 —— 「初级算法 - 帮助入门」
 * <p>
 * https://leetcode-cn.com/leetbook/detail/top-interview-questions-easy/
 * <p>
 * 整理
 *
 * @author zhangxinqiang
 * @create 2021/12/31 14:16
 */
public class Level1 {

    // -------------------------- 数组 ------------------------------
    // 数组问题在面试中出现频率很高，你极有可能在面试中遇到。
    // 我们推荐以下题目：只出现一次的数字，旋转数组，两个数组的交集 II 和 两数之和。

    /**
     * 存在重复元素
     * 给定一个整数数组，判断是否存在重复元素。
     * 如果存在一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
     * <p>
     * 思路1：排序
     * 1、排序
     * 2、遍历, 判断相邻的两个数字是否相等即可
     * <p>
     * 思路2：哈希
     * 1、创建一个set记录已经出现过的值
     * 2、遍历，遇到第一个重复的值，或者没有重复
     * <p>
     * 思路3：借助数组 实现类似手动哈希
     * 1、创建一个同长的数组
     * 2、遍历, 借助余数 num[i]%num.length 定位位置, 快速判断是否存在重复
     * ps: （注意num[0]=0的特殊情况）
     */
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDuplicate1(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        return false;
    }


    /**
     * 旋转数组
     * 给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * <p>
     * 思路：数学
     * 1、(A^-1 B^-1)^-1 = BA
     * <p>
     * [逆矩阵_百度百科](https://baike.baidu.com/item/%E9%80%86%E7%9F%A9%E9%98%B5/10481136?fr=aladdin)
     */
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        k = k % len;

        reverse(nums, 0, len - k);
        reverse(nums, len - k, len);
        reverse(nums, 0, len);
    }

    public void reverse(int[] nums, int start, int end) {
        for (int i = start, j = end - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    /**
     * 买卖股票的最佳时机 II
     * 给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * <p>
     * (第三遍)
     * 思路：贪心
     * 1、没有任何买卖限制，完全可以站在今天看昨天，昨天比今天低，就假设昨天买了，今天卖了
     * 2、所有正利润的和，为最大利润
     */
    public int maxProfit(int[] prices) {
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            int x = prices[i] - prices[i - 1];
            if (x > 0) {
                sum += x;
            }
        }
        return sum;
    }

    /**
     * 删除排序数组中的重复项
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * <p>
     * <p>
     * 思路：双指针
     * 1、遍历nums, len指向值未重复的下标，i一直往后
     * 2、当遇到不同的值时，将len++, 然后令nums[len]=nums[i]
     * <p>
     * ps：数组有序
     */
    public int removeDuplicates(int[] nums) {
        int len = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[len] != nums[i]) {
                nums[++len] = nums[i];
            }
        }
        return ++len;
    }


    // -------------------------- 其它 ------------------------------end

}
