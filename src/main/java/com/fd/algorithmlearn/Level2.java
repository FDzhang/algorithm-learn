package com.fd.algorithmlearn;

import java.util.*;
import java.util.stream.Collectors;

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
        int[] x = {1, -1, -1, 0};
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
     * 3、 twoSum(nums, lo, target) : 返回两个值的list， 每个list中的两个值加起来=target (需要注意while循环放置的位置)
     * <p>
     * 4、小优化，当nums[i]>0时，则没必要继续调用twoSum
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                break;
            }

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

    /**
     * 矩阵置零
     * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
     * 进阶：
     * <p>
     * 一个直观的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
     * 一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
     * 你能想出一个仅使用常量空间的解决方案吗？
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvmy42/
     * <p>
     * 思路：
     * 1、遍历矩阵第一遍标记，若 m[i][j] = 0, 则记录i、j
     * 2、分别根据 记录的 i、j,将 i行置0,将 j列置0
     * <p>
     * 进阶思路： 仅使用常量空间
     * 基于 O(m + n) 的额外空间 的方案： 无非需要 m+n 的额外空间 -> 借用矩阵的 第0行和第0列 作为m+n的额外空间
     * 1、遍历第0行判断是否有0,记录一个标记a； 遍历第0列判断是否有0，记录一个标记b
     * 2、从 (1,1) 开始遍历矩阵, 若 m(i,j)==0, 则将 i 记录到 m(i, 0), 将j 记录到 m(0, j)。将两个位置 置0
     * 3、从 (1,1) 开始再次遍历矩阵, 遍历到 m(i,j)时, 若 （m(i, 0)==0 or m(0, j)==0）,则令m(i, j)=0;
     * 4、根据标记a 判断是否需要将第0行置0， 根据标记b判断是否需要将第0列置0
     */
    public void setZeroes(int[][] matrix) {

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
     * 字母异位词分组
     * 给定一个字符串数组，将字母异位词组合在一起。可以按任意顺序返回结果列表。
     * <p>
     * 字母异位词指字母相同，但排列不同的字符串。
     * <p>
     * 示例 1:
     * <p>
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvaszc/
     * <p>
     * 思路：
     * 1、拆散成 char， 排序，再组合
     * 2、排序再组合后一样的放入一组
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] cs = str.toCharArray();
            Arrays.sort(cs);
            String sortStr = Arrays.toString(cs);

            if (!map.containsKey(sortStr)) {
                map.put(sortStr, new ArrayList<>());
            }

            map.get(sortStr).add(str);
        }
        return new ArrayList<>(map.values());
    }


}
