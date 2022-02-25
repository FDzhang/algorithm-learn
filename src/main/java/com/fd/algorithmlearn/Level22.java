package com.fd.algorithmlearn;

import java.util.*;

/**
 * 中级算法
 * LeetCode 官方推出的经典面试题目清单 —— 「中级算法 - 巩固训练」
 *
 * @author zhangxinqiang
 * @create 2022/2/24 11:18
 */
public class Level22 {
    // -------------------------- 数组和字符串 ------------------------------

    // 数组和字符串
    // 数组和字符串问题在面试中出现频率很高，你极有可能在面试中遇到。
    //
    // 我们推荐以下题目：字母异位词分组，无重复字符的最长子串 和 最长回文子串。

    /**
     * 矩阵置零
     * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
     * <p>
     * 进阶：
     * 一个直观的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
     * 一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
     * 你能想出一个仅使用常量空间的解决方案吗？
     * <p>
     * 相关标签 数组 哈希表 矩阵
     * <p>
     * eg:
     * [0,1,2,0],
     * [3,4,5,2],
     * [1,3,1,5]]
     * <p>
     * [1,2,3,4],
     * [5,0,7,8],
     * [0,10,11,12],
     * [13,14,15,0]]
     *
     * <p>
     * 思路1: 数组标记 0（m+n）
     * 1、遍历，记录应该置为0的行，和应该置为0的列
     * 2、遍历，将对于行置为0，将对应的列置为0
     * <p>
     * 思路2: 标记 O(1)
     * 1、由思路1 ： 需要m+n个标记来记录行列是否需要置0
     * 2、声明两个变量r、l, 来预先记录第一行或第一列是否需要置零，
     * 3、借助matrix本身的第一行和第一列来标记当前行，当前列是否需要置0
     * 4、将matrix对应的行列置0
     * 5、借助r、l, 判断第一行或第一列是否置0
     */
    // o(m+n)
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[] r = new int[m];
        int[] l = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    r[i] = 1;
                    l[j] = 1;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (r[i] == 1 || l[j] == 1) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // o(1)
    public void setZeroes2(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int r = 1;
        int l = 1;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                l = 0;
                break;
            }
        }
        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                r = 0;
                break;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (r == 0) {
            Arrays.fill(matrix[0], 0);
        }
        if (l == 0) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    public void setZeroes1(int[][] matrix) {

        Set<Integer> r = new HashSet<>();
        Set<Integer> l = new HashSet<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    r.add(i);
                    l.add(j);
                }
            }
        }

        for (Integer i : r) {
            Arrays.fill(matrix[i], 0);
        }

        for (Integer j : l) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][j] = 0;
            }
        }
    }

    /**
     * 三数之和
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
     * 请你找出所有和为 0 且不重复的三元组。
     * <p>
     * 注意：答案中不可以包含重复的三元组。
     * <p>
     * 相关标签 数组 双指针 排序
     * <p>
     * 思路1：排序+双指针 （注意单向） （17ms）
     * 1、排序（便于增大or减小），定义指针 i,j,k  (0<k<i<j<nuns.length)
     * 2、k一直右移，每次在[k+1, nums.length-1]的范围内找到两个数之和 等于 -nums[k], 将nums[i],nums[j],nums[k]放入结果集res
     * 3、返回res
     * ps： 小优化，当nums[i]>0时，则没必要继续调用twoSum，因为两个大于0的数加起来不可能 < 0
     * <p>
     * 思路2: 6ms左右， 未学习
     */
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) {
            return new ArrayList<>();
        }

        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                break;
            }
            int c = nums[i];
            List<List<Integer>> sub = twoSum(nums, i, -c);
            res.addAll(sub);

            while (i + 1 < nums.length && nums[i + 1] == c) {
                i++;
            }
        }
        return res;
    }

    // nums 有序
    public List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int i = start + 1;
        int j = nums.length - 1;
        while (i < j) {
            int a = nums[i];
            int b = nums[j];
            int c = a + b;
            if (c > target) {
                j--;
            } else if (c < target) {
                i++;
            } else {
                List<Integer> sub = Arrays.asList(a, b, -target);
                res.add(sub);

                while (i < j && a == nums[i]) {
                    i++;
                }
                while (i < j && b == nums[j]) {
                    j--;
                }
            }
        }
        return res;
    }

    public List<List<Integer>> threeSum1(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                break;
            }
            List<List<Integer>> list = twoSum1(nums, i + 1, -nums[i]);

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

    public List<List<Integer>> twoSum1(int[] nums, int start, int target) {
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
                while (nums[lo] == left && lo < hi) {
                    lo++;
                }
                while (nums[hi] == right && lo < hi) {
                    hi--;
                }
            }
        }
        return res;
    }


    // -------------------------- 数组和字符串 ------------------------------end

}
