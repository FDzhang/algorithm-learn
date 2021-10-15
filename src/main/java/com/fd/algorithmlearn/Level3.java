package com.fd.algorithmlearn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 四数相加 II
     * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
     * 0 <= i, j, k, l < n
     * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-hard/xwqm6c/
     * <p>
     * 思路：
     * 1、A[i]+B[j]=-(C[k]+D[l])
     * <p>
     * ps:
     * 几数之和的总结
     * 1、如果是问几数之和各个数的组合数，如之前的三数之和那题，一般是用到二分或者双指针这种能够得到具体数字情况的方法来降低循环的层数。
     * 2、如果是问几数之和中符合情况的个数，一般都是用hash表降低循环层数，此时我们只需要关注答案的次数即可。
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> map = new HashMap<>();
        int cnt = 0;

        for (int i : nums1) {
            for (int j : nums2) {
                map.merge(i + j, 1, Integer::sum);
            }
        }

        for (int i : nums3) {
            for (int j : nums4) {
                cnt += map.getOrDefault(-(i + j), 0);
            }
        }

        return cnt;
    }


    /**
     * 螺旋矩阵
     * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
     * <p>
     * 示例 1：
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     * <p>
     * 思路：
     * 1、使用 tag[][] 数组标记已经访问过的元素，循环遍历，使用 res 列表装入元素
     * 2、当 res.length == m*n 时， 退出循环
     * <p>
     * ps: （理解 i+1, j+1, i-1, j-1） 预先将matrix[0][0]装入, 然后以此为起点向后看, 若超过了矩阵的边界 或 后面的元素已经访问过, 则转向
     * <p>
     * 空间优化：在原矩阵上用界外值标记访问过的元素
     * <p>
     * 思路2:
     * 1、按圈层剥离，递归遍历
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] tag = new boolean[m][n];
        List<Integer> res = new ArrayList<>();

        int i = 0;
        int j = 0;
        tag[i][j] = true;
        res.add(matrix[i][j]);
        while (true) {
            // 向右
            while (j + 1 < n && !tag[i][j + 1]) {
                tag[i][j + 1] = true;
                res.add(matrix[i][j + 1]);
                j++;
            }
            // 向下
            while (i + 1 < m && !tag[i + 1][j]) {
                tag[i + 1][j] = true;
                res.add(matrix[i + 1][j]);
                i++;
            }
            // 向左
            while (j - 1 >= 0 && !tag[i][j - 1]) {
                tag[i][j - 1] = true;
                res.add(matrix[i][j - 1]);
                j--;
            }
            // 向上
            while (i - 1 >= 0 && !tag[i - 1][j]) {
                tag[i - 1][j] = true;
                res.add(matrix[i - 1][j]);
                i--;
            }
            if (res.size() == m * n) {
                break;
            }
        }
        return res;
    }


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
