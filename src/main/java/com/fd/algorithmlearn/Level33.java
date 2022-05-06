package com.fd.algorithmlearn;

import java.util.Arrays;

/**
 * 高级算法
 * LeetCode 官方推出的经典面试题目清单 —— 「高级算法 - 提升进阶」
 * <p>
 * https://leetcode-cn.com/leetbook/detail/top-interview-questions-hard/
 *
 * @author : zxq
 * @create : 2022/5/6 12:51
 */
public class Level33 {
    /**
     * 除自身以外数组的乘积
     * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
     * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
     * 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
     * <p>
     * 示例 1:
     * 输入: nums = [1,2,3,4]
     * 输出: [24,12,8,6]
     * 示例 2:
     * 输入: nums = [-1,1,0,-3,3]
     * 输出: [0,0,9,0,0]
     * 提示：
     * <p>
     * 2 <= nums.length <= 105
     * -30 <= nums[i] <= 30
     * 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内
     * <p>
     * 进阶：你可以在 O(1) 的额外空间复杂度内完成这个题目吗？（ 出于对空间复杂度分析的目的，输出数组不被视为额外空间。）
     * <p>
     * 相关标签 数组 前缀和
     * 思路：前缀和
     * 1、声明两个数组，两遍循环，分别求前后缀的乘积
     * 2、再将两个数组循环相乘即为结果
     * <p>
     * 思路：前缀和 （改进空间复杂度）
     * 1、声明一个结果数组res，和两个数字pre，post记录前后缀的乘积和
     * 2、遍历第一遍利用pre将前缀乘积和放入res中，遍历第二遍借助post与res[i]相乘即为结果
     * 3、返回res
     */
    // O(1)
    public int[] productExceptSelf2(int[] nums) {
        int len = nums.length;
        int[] res = new int[len];
        int pre = 1;
        int post = 1;

        for (int i = 0; i < len; i++) {
            res[i] = pre;
            pre *= nums[i];
        }
        for (int i = len - 1; i > 0; i--) {
            post *= nums[i];
            res[i - 1] *= post;
        }
        return res;
    }
    public int[] productExceptSelf3(int[] nums) {
        int len = nums.length;
        int[] res = new int[len];
        int pre = 1;
        int post = 1;

        for (int i = 0; i < len; i++) {
            res[i] = pre;
            pre *= nums[i];
        }
        for (int i = len - 1; i >= 0; i--) {
            res[i] *= post;
            post *= nums[i];
        }
        return res;
    }

    public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        int[] pre = new int[len];
        int[] post = new int[len];
        pre[0] = 1;
        post[len - 1] = 1;

        for (int i = 1; i < len; i++) {
            pre[i] = pre[i - 1] * nums[i - 1];
        }
        for (int i = len - 2; i >= 0; i--) {
            post[i] = post[i + 1] * nums[i + 1];
        }
        for (int i = 0; i < len; i++) {
            pre[i] *= post[i];
        }
        return pre;
    }


}
