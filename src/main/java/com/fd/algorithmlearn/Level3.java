package com.fd.algorithmlearn;

/**
 * 高级算法
 * LeetCode 官方推出的经典面试题目清单 —— 「高级算法 - 提升进阶」
 * <p>
 * https://leetcode-cn.com/leetbook/detail/top-interview-questions-hard/
 *
 * @author zhangxinqiang
 * @create 2021/10/13 10:24
 */
public class Level3 {

    /**
     * 除自身以外数组的乘积
     * <p>
     * 给你一个长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-hard/xw8dz6/
     * <p>
     * 示例:
     * 输入: [1,2,3,4]
     * 输出: [24,12,8,6]
     * <p>
     * 思路： from讨论区 （分段求乘积）
     * 1、从左往右遍历，在res数组中，记录下当前位置左边的乘积
     * 2、再 从右往左遍历，此时 r 记录的是当前位置右边的乘积，所以使用 res[i]*r, 即 左边的乘积 * 右边的乘积
     * 3、返回res数组
     */
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];

        int l = 1;
        for (int i = 0; i < nums.length; i++) {
            res[i] = l;
            l *= nums[i];
        }

        int r = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= r;
            r *= nums[i];
        }

        return res;
    }
}
