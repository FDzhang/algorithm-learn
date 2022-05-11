package com.fd.algorithmlearn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // -------------------------- 数组和字符串 ------------------------------
    // 数组和字符串
    // 数组和字符串问题在面试中出现频率很高，你极有可能在面试中遇到。
    //
    // 我们推荐以下题目：除本身之外的数组之和，螺旋矩阵，第一个缺失的正数，滑动窗口最大值 和 最小窗口子串。
    //
    // 后面两道题的难度会稍有增加，如果你解题遇到困难，请随时跳过这些题目，你可以留到之后再回来思考。
    //
    // 除自身以外数组的乘积

    /**
     * 螺旋矩阵
     * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
     * <p>
     * 示例 1：
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     * 示例 2：
     * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
     * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
     * <p>
     * 提示：
     * <p>
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 10
     * -100 <= matrix[i][j] <= 100
     * 相关标签 数组 矩阵 模拟
     * <p>
     * 思路：方向数组
     * 1、初始化一个顺时针的方向数组 {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}
     * 2、遍历matrix，如果越界或者已经访问过则转向
     * ps: 转向: 先回退一步，再转向，再前进一步
     * ps2: 999是matrix[i][j]中不存在的数，若matrix[i][j]没限制范围，可以声明一个m x n的布尔矩阵来标记。
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int[][] d = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int m = matrix.length;
        int n = matrix[0].length;
        int len = m * n;
        List<Integer> res = new ArrayList<>(len);
        int i = 0;
        int j = 0;
        int x = 0;
        while (res.size() < len) {
            if (matrix[i][j] != 999) {
                res.add(matrix[i][j]);
                matrix[i][j] = 999;
            }
            i += d[x][0];
            j += d[x][1];
            if (i >= m || i < 0 || j >= n || j < 0 || matrix[i][j] == 999) {
                i -= d[x][0];
                j -= d[x][1];
                x = (x + 1) % 4;
                i += d[x][0];
                j += d[x][1];
            }
        }
        return res;
    }


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
    // -------------------------- 数组和字符串 ------------------------------ end

}
