package com.fd.algorithmlearn;

import com.fd.algorithmlearn.entity.Node;
import com.fd.algorithmlearn.entity.TreeNode;
import com.fd.algorithmlearn.linked.ListNode;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 中级算法
 * LeetCode 官方推出的经典面试题目清单 —— 「中级算法 - 巩固训练」
 * <p>
 * https://leetcode-cn.com/leetbook/detail/top-interview-questions-medium/
 *
 * @author zhangxinqiang
 * @create 2022/2/24 11:18
 */
public class Level22 {

    /**
     * 动态规划
     * 本章为大家介绍一些经典动态规划问题。
     *
     * 我们推荐以下题目：不同路径，零钱兑换 和 最长递增子序列
     */


    // -------------------------- 动态规划 ------------------------------
    // 动态规划
    // 本章为大家介绍一些经典动态规划问题。
    //
    // 我们推荐以下题目：不同路径，零钱兑换 和 最长递增子序列

    /**
     * 最长递增子序列
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     * <p>
     * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     * <p>
     *  
     * 示例 1：
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
     * <p>
     * 示例 2：
     * 输入：nums = [0,1,0,3,2,3]
     * 输出：4
     * <p>
     * 示例 3：
     * 输入：nums = [7,7,7,7,7,7,7]
     * 输出：1
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= nums.length <= 2500
     * -104 <= nums[i] <= 104
     *  
     * 进阶：
     * 你能将算法的时间复杂度降低到 O(n log(n)) 吗?
     * 相关标签 数组 二分查找 动态规划
     * <p>
     * 思路1：动态规划
     * 1、dp[i] 以nums[i]为结尾的最长子序列的长度
     * 2、递推公式： 在j=[0, i),若nums[i]>num[j], 则dp[i] = Math.max(dp[i], dp[j] + 1)
     * ps: 如果存在nums[i]>num[j]，则以nums[i]结尾的最长子序列起码等于 dp[j]+1
     * 思路2：二分查找（ +耐心排序）
     * 1、初始化 top 数组（最大不超过nums.length）, 遍历nums
     * a、(在0~index范围)二分查找top数组，找到top数组中小于nums[i]的左边界left，并令top[left]=nums[i]
     * b、若找不到，则index++，将nums[i]放在index-1的位置上
     * 2、返回 index
     */
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];

        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(dp[i], res);
        }
        return res;
    }

    public int lengthOfLIS1(int[] nums) {
        int[] top = new int[nums.length];
        int res = 0;
        for (int t : nums) {
            int left = 0;
            int right = res - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (top[mid] >= t) {
                    right = mid - 1;
                } else if (top[mid] < t) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            top[left] = t;
            if (left == res) {
                res++;
            }
        }
        return res;
    }

    /**
     * 零钱兑换
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     * <p>
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
     * <p>
     * 你可以认为每种硬币的数量是无限的。
     * <p>
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入：coins = [1, 2, 5], amount = 11
     * 输出：3
     * 解释：11 = 5 + 5 + 1
     * 示例 2：
     * <p>
     * 输入：coins = [2], amount = 3
     * 输出：-1
     * 示例 3：
     * <p>
     * 输入：coins = [1], amount = 0
     * 输出：0
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= coins.length <= 12
     * 1 <= coins[i] <= 231 - 1
     * 0 <= amount <= 10^4
     * 相关标签 广度优先搜索  数组 动态规划
     * <p>
     * 思路：动态规划
     * 1、dp[i]: 到达当前值的最小硬币数
     * 2、初始化：dp[i]初始化为Integer.MAX_VALUE - 1
     * 3、递推公式：若 coin <= i ,则  dp[i] = Math.min(dp[i], dp[i - coin]) + 1
     */

    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for (int i = 1; i < dp.length; i++) {
            int temp = Integer.MAX_VALUE - 1;
            for (int coin : coins) {
                if (coin <= i) {
                    temp = Math.min(temp, dp[i - coin]);
                }
            }
            dp[i] = temp + 1;
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    public int coinChange1(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int coin : coins) {
                int next = coin + i;
                if (next >= 0 && next < dp.length && dp[i] != Integer.MAX_VALUE) {
                    dp[next] = Math.min(dp[i] + 1, dp[next]);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }


    /**
     * 不同路径
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
     * <p>
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     * <p>
     * 问总共有多少条不同的路径？
     * <p>
     * 示例 1：
     * 输入：m = 3, n = 7
     * 输出：28
     * 示例 2：
     * <p>
     * 输入：m = 3, n = 2
     * 输出：3
     * 解释：
     * 从左上角开始，总共有 3 条路径可以到达右下角。
     * 1. 向右 -> 向下 -> 向下
     * 2. 向下 -> 向下 -> 向右
     * 3. 向下 -> 向右 -> 向下
     * 示例 3：
     * <p>
     * 输入：m = 7, n = 3
     * 输出：28
     * 示例 4：
     * <p>
     * 输入：m = 3, n = 3
     * 输出：6
     * <p>
     * 提示：
     * <p>
     * 1 <= m, n <= 100
     * 题目数据保证答案小于等于 2 * 10^9
     * 相关标签
     * 数学
     * 动态规划
     * 组合数学
     * <p>
     * 思路：动态规划
     * 1、到达前网格的不同路径数（dp[i][j]） = 到达左边网格的不同路径数(dp[i][j-1]) + 到达上边网格的不同路径数(dp[i-1][j])
     * 2、返回dp[m-1][n-1]
     * ps: 空间优化 ：由于当前行的状态，由上一行推出，所以 dp[i][j] -> dp[j]
     */
    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j - 1];
            }
        }
        return dp[n - 1];
    }

    /**
     * 跳跃游戏
     * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
     * <p>
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * <p>
     * 判断你是否能够到达最后一个下标。
     * <p>
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [2,3,1,1,4]
     * 输出：true
     * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
     * 示例 2：
     * <p>
     * 输入：nums = [3,2,1,0,4]
     * 输出：false
     * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= nums.length <= 3 * 104
     * 0 <= nums[i] <= 105
     * 相关标签
     * 贪心
     * 数组
     * 动态规划
     * <p>
     * 思路1：贪心
     * 1、循环nums， 令最远可以到达的距离 res = 0;
     * a、判断当前位置是否可以可以到达，可以到达，则更新最远的到达位置 res = max(i+nums[i], res), 反之直接返回false
     * b、如果 res >= nums.length-1, 返回true
     * 2、返回false
     */
    public boolean canJump(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i <= res) {
                res = Math.max(res, i + nums[i]);
            } else {
                return false;
            }
            if (res >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    public boolean canJump2(int[] nums) {
        int maxFar = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (i <= maxFar) {
                maxFar = Math.max(maxFar, nums[i] + i);
            }
        }
        return maxFar >= nums.length - 1;
    }


    // -------------------------- 动态规划 ------------------------------

    // -------------------------- 排序和搜索 ------------------------------
    // 排序和搜索
    // 本章涵盖了在有序结构中的排序和搜索问题。
    //
    // 我们推荐一下题目：分类颜色，搜索范围，合并区间，搜索旋转排序数组 和 搜索二维矩阵 II。
    //

    /**
     * 搜索二维矩阵 II
     * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
     * <p>
     * 每行的元素从左到右升序排列。
     * 每列的元素从上到下升序排列。
     *  
     * <p>
     * 示例 1：
     * 输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
     * 输出：true
     * <p>
     * 示例 2：
     * 输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
     * 输出：false
     * <p>
     * 提示：
     * <p>
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= n, m <= 300
     * -109 <= matrix[i][j] <= 109
     * 每行的所有元素从左到右升序排列
     * 每列的所有元素从上到下升序排列
     * -109 <= target <= 109
     * 相关标签 数组 二分查找 分治 矩阵
     * <p>
     * 思路： 二分查找
     * 1、从右上角开始搜索 （从左上角的话 向右和向下都是变大，而从右上角 向左是变小， 向下是变大，天然二分）
     * 2、m[i][j] > target 向左， m[i][j] < target 向下
     * 3、结束条件： m[i][j] == target 或者 到了最左边和最下面 为止
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        for (int i = 0, j = matrix[0].length - 1; i < matrix.length && j >= 0; ) {
            if (matrix[i][j] > target) {
                j--;
            } else if (matrix[i][j] < target) {
                i++;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 搜索旋转排序数组
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     * <p>
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ...,
     * nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
     * <p>
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     * <p>
     *  
     * <p>
     * 示例 1：
     * 输入：nums = [4,5,6,7,0,1,2], target = 0
     * 输出：4
     * 示例 2：
     * <p>
     * 输入：nums = [4,5,6,7,0,1,2], target = 3
     * 输出：-1
     * 示例 3：
     * <p>
     * 输入：nums = [1], target = 0
     * 输出：-1
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= nums.length <= 5000
     * -10^4 <= nums[i] <= 10^4
     * nums 中的每个值都 独一无二
     * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
     * -10^4 <= target <= 10^4
     * <p>
     * 进阶：你可以设计一个时间复杂度为 O(log n) 的解决方案吗？
     * <p>
     * 相关标签 数组 二分查找
     * <p>
     * 思路1：
     * 1、遍历nums查找target
     * <p>
     * 思路2：二分 （from讨论区热评top1  二分查找的本质——根据有序元素段判断元素位置以不断收缩边界选择搜索空间）
     * 1、依据 局部有序，不断收缩边界
     * 2、nums[mid]>=nums[i], 则左端有序。
     * a、target < nums[mid] 且 target >= nums[i] 则元素在左端，令j=mid-1，反之，令i=mid+1;
     * 3、nums[mid] < nums[i], 则右端有序。
     * a、target > nums[mid] 且 target <= nums[j] 则元素在右端，令i=mid+1，反之，令j=mid-1;
     */
    public int search(int[] nums, int target) {
        int i = 0;
        int j = nums.length - 1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] >= nums[i]) {
                if (target < nums[mid] && target >= nums[i]) {
                    j = mid - 1;
                } else {
                    i = mid + 1;
                }
            } else if (nums[mid] < nums[i]) {
                if (target > nums[mid] && target <= nums[j]) {
                    i = mid + 1;
                } else {
                    j = mid - 1;
                }
            }
        }
        return -1;
    }

    public int search1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 合并区间
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
     * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出：[[1,6],[8,10],[15,18]]
     * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     * 示例 2：
     * <p>
     * 输入：intervals = [[1,4],[4,5]]
     * 输出：[[1,5]]
     * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= intervals.length <= 104
     * intervals[i].length == 2
     * 0 <= starti <= endi <= 10^4
     * 相关标签 数组 排序
     * <p>
     * 思路：先排序，再遍历
     * 1、依据左端点排序
     * 2、遍历 intervals
     * a、pre = 当前区间，再判断是否还有下一个区间
     * b、判断是否有下一个区间，则令 next = 下一个区间，
     * b1、依据 pre的右端点 和 next的左端点，判断是否需要合并区间
     * b2、pre的右端点 >= next的左端点，则pre的右端点 = Math.max(pre的右端点, next的右端点)
     * b3、循环执行 b,b1,b2
     * c、将pre加入结果集
     * 3、返回结果集
     * <p>
     * 思路1：先排序，再遍历
     * 1、依据左端点排序
     * 2、遍历 intervals
     * a、pre = 当前区间
     * b、循环遍历下一个区间
     * b1、令next = 下一个区间
     * b2、若存在，pre的右端点 >= next的左端点，则合并区间，令pre的右端点 = Math.max(pre的右端点, next的右端点)
     * b3、反之，则无法继续合并，跳出当前循环，进行下一次合并
     * c、将pre加入结果集
     * 3、返回结果集
     * <p>
     * 思路2：bit数组 （参考详情中的2ms答案）
     * 1、声明一个 BitSet
     * 2、遍历 intervals, 将对应范围的所有bit位 置为true, 并记录最远处的 bit位 max
     * 3、遍历bitSet (max之内), 找到每一段的true的左右边界，计入结果集
     * 4、返回结果集
     */
    public int[][] merge2(int[][] intervals) {
        BitSet bitSet = new BitSet();
        int max = 0;
        for (int[] interval : intervals) {
            int left = interval[0] * 2;
            int right = interval[1] * 2 + 1;
            bitSet.set(left, right, true);
            max = Math.max(right, max);
        }

        int index = 0, count = 0;
        while (index < max) {
            int start = bitSet.nextSetBit(index);
            int end = bitSet.nextClearBit(start);

            int[] item = {start / 2, (end - 1) / 2};
            intervals[count++] = item;

            index = end;
        }
        int[][] ret = new int[count][2];
        if (count >= 0) {
            System.arraycopy(intervals, 0, ret, 0, count);
        }
        return ret;
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> res = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            int[] pre = intervals[i];
            if (++i < intervals.length) {
                int[] next = intervals[i];
                while (pre[1] >= next[0]) {
                    pre[1] = Math.max(pre[1], next[1]);
                    if (++i >= intervals.length) {
                        break;
                    }
                    next = intervals[i];
                }
                i--;
            }
            res.add(pre);
        }

        return res.toArray(new int[res.size()][]);
    }

    public int[][] merge1(int[][] intervals) {
        List<int[]> res = new ArrayList<>();
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        for (int i = 0; i < intervals.length; i++) {
            int[] m = intervals[i];

            for (int j = i + 1; j < intervals.length; j++, i++) {
                int[] n = intervals[j];
                if (m[1] >= n[0]) {
                    m[1] = Math.max(m[1], n[1]);
                } else {
                    break;
                }
            }

            res.add(m);
        }
        return res.toArray(new int[res.size()][]);
    }


    /**
     * 在排序数组中查找元素的第一个和最后一个位置
     * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
     * <p>
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     * <p>
     * 进阶：
     * 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？
     *  
     * <p>
     * 示例 1：
     * 输入：nums = [5,7,7,8,8,10], target = 8
     * 输出：[3,4]
     * <p>
     * 示例 2：
     * 输入：nums = [5,7,7,8,8,10], target = 6
     * 输出：[-1,-1]
     * <p>
     * 示例 3：
     * 输入：nums = [], target = 0
     * 输出：[-1,-1]
     * <p>
     * 提示：
     * <p>
     * 0 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     * nums 是一个非递减数组
     * -109 <= target <= 109
     * <p>
     * 相关标签 数组 二分查找
     * 思路：二分查找
     * 1、利用二分查找，找到目标值的左边界
     * 2、若没有找目标值返回 [-1,-1]
     * 3、若找到了目标值，则从左边界开始遍历，找到对应的右边界，返回左右边界的下标
     */
    public int[] searchRange(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int[] rIndex = new int[]{-1, -1};

        while (left <= right) {
            int mid = (left + right) / 2;
            int midV = nums[mid];
            if (midV == target) {
                rIndex[0] = mid;
                right = mid - 1;
            } else if (midV > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        if (rIndex[0] == -1) {
            return rIndex;
        } else {
            int l = rIndex[0];
            int r = rIndex[0];
            while (r < nums.length && nums[l] == nums[r]) {
                rIndex[1] = r++;
            }
            return rIndex;
        }
    }

    /**
     * 寻找峰值
     * 峰值元素是指其值严格大于左右相邻值的元素。
     * <p>
     * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
     * <p>
     * 你可以假设 nums[-1] = nums[n] = -∞ 。
     * <p>
     * 你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
     * <p>
     * 示例 1：
     * 输入：nums = [1,2,3,1]
     * 输出：2
     * 解释：3 是峰值元素，你的函数应该返回其索引 2。
     * 示例 2：
     * <p>
     * 输入：nums = [1,2,1,3,5,6,4]
     * 输出：1 或 5
     * 解释：你的函数可以返回索引 1，其峰值元素为 2；
     *      或者返回索引 5， 其峰值元素为 6。
     * <p>
     * 提示：
     * <p>
     * 1 <= nums.length <= 1000
     * -231 <= nums[i] <= 231 - 1
     * 对于所有有效的 i 都有 nums[i] != nums[i + 1]
     * 相关标签 数组 二分查找
     * <p>
     * 思路：数组遍历
     * 1、处理特殊情况：数组的开头，结尾， 数组长度<2的情况
     * 2、找下标，满足nums[i-1]<num[i]>nums[i+1] 的 i
     */
    public int findPeakElement(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        // 边界
        if (nums[0] > nums[1]) {
            return 0;
        }
        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i] > nums[i - 1] && nums[i] > nums[i + 1]) {
                return i;
            }
        }
        // 边界
        if (nums[nums.length - 1] > nums[nums.length - 2]) {
            return nums.length - 1;
        }
        return -1;
    }

    public int findPeakElement1(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (i - 1 >= 0 && nums[i - 1] > nums[i]) {
                continue;
            }
            if (i + 1 < nums.length && nums[i + 1] > nums[i]) {
                continue;
            }
            return i;
        }
        return -1;
    }

    /**
     * 数组中的第K个最大元素
     * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
     * <p>
     * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     * 示例 1:
     * <p>
     * 输入: [3,2,1,5,6,4] 和 k = 2
     * 输出: 5
     * 示例 2:
     * <p>
     * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
     * 输出: 4
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= k <= nums.length <= 104
     * -104 <= nums[i] <= 104
     * 相关标签 数组 分治 快速选择 排序 堆（优先队列）
     * <p>                   1  2  3   4
     * eg: [3,2,3,1,2,4,5,5,6]
     * 思路1：排序+遍历
     * 1、排序后，倒数第k个元素
     * ps!!!: 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     * 思路2：优先队列
     * 思路3：快速排序
     * 概念：快速排序是先选择一个中枢（一般我们选第一个），然后遍历后面的元素，最终会把数组分为两部分，前面部分比中枢值小，后面部分大于或等于中枢值。
     * 1、利用快速排序的概念
     * a、依据中枢值，把指定区间的分为两个部分，前面部分比中枢值大，后面部分小于或等于中枢值。
     * b、若中枢的下标p == k-1, 则为第K个最大元素
     * c、若中枢的下标p != k-1, 则依据p, 在子区间继续进行（a、b、c）步骤
     */
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    public int findKthLargest3(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int p = left;
            for (int q = left + 1; q <= right; q++) {
                if (nums[q] > nums[left]) {
                    swap(nums, q, ++p);
                }
            }
            swap(nums, left, p);
            if (p == k - 1) {
                return nums[p];
            } else if (p > k - 1) {
                right = p - 1;
            } else {
                left = p + 1;
            }
        }
        return -1;
    }

    public int findKthLargest2(int[] nums, int k) {
        k = nums.length - k;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int p = left;
            for (int q = left + 1; q <= right; q++) {
                if (nums[q] < nums[left]) {
                    swap(nums, q, ++p);
                }
            }
            swap(nums, left, p);
            if (p == k) {
                return nums[p];
            } else if (p > k) {
                right = p - 1;
            } else {
                left = p + 1;
            }
        }
        return -1;
    }

    public int findKthLargest1(int[] nums, int k) {
        Arrays.sort(nums);
        int res = -1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res = nums[i];
            if (--k == 0) {
                break;
            }
        }
        return res;
    }

    /**
     * 前 K 个高频元素
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     * <p>
     *  
     * <p>
     * 示例 1:
     * 输入: nums = [1,1,1,2,2,3], k = 2
     * 输出: [1,2]
     * 示例 2:
     * <p>
     * 输入: nums = [1], k = 1
     * 输出: [1]
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= nums.length <= 105
     * k 的取值范围是 [1, 数组中不相同的元素的个数]
     * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的
     *  
     * <p>
     * 进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
     * <p>
     * 相关标签 数组 哈希表 分治 桶排序 计数 快速选择 排序 堆（优先队列）
     * <p>
     * 思路1：哈希计数+优先队列
     * 1、哈希计数: 数字 x 对应的频率 xF
     * 2、将 {x, xF} 放入最大堆 （优先队列）
     * 3、从最大堆中取出前k个元素
     * <p>
     * 思路2：哈希计数+桶排序
     */
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> xxF = new HashMap<>();

        Arrays.stream(nums).forEach(x -> {
            xxF.merge(x, 1, Integer::sum);
        });

        PriorityQueue<int[]> priority = new PriorityQueue<>((a, b) -> Integer.compare(b[1], a[1]));
        xxF.forEach((x, xF) -> {
            priority.offer(new int[]{x, xF});
        });

        int[] res = new int[k];
        for (int i = 0; i < res.length; i++) {
            if (!priority.isEmpty()) {
                res[i] = priority.poll()[0];
            }
        }
        return res;
    }

    public int[] topKFrequent1(int[] nums, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(b[1], a[1]));

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            Integer cnt = map.getOrDefault(num, 0);
            map.put(num, ++cnt);
        }
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            pq.offer(new int[]{e.getKey(), e.getValue()});
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = pq.poll()[0];
        }
        return res;
    }


    /**
     * 颜色分类
     * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * 必须在不使用库的sort函数的情况下解决这个问题。
     * <p>
     * 示例 1：
     * 输入：nums = [2,0,2,1,1,0]
     * 输出：[0,0,1,1,2,2]
     * <p>
     * <p>
     * 示例 2：
     * 输入：nums = [2,0,1]
     * 输出：[0,1,2]
     *  
     * <p>
     * 提示：
     * <p>
     * n == nums.length
     * 1 <= n <= 300
     * nums[i] 为 0、1 或 2
     *  
     * <p>
     * 进阶：
     * <p>
     * 你可以不使用代码库中的排序函数来解决这道题吗？
     * 你能想出一个仅使用常数空间的一趟扫描算法吗？
     * <p>
     * 相关标签 数组 双指针 排序
     * <p>
     * <p>
     * 思路：双指针
     * 1、left i right 分别指向 0 1 2
     * 2、遍历nums
     * a、nums[i]==0, swap(nums, left, i), left++
     * b、nums[i]==2, swap(nums, right, i), right--, i-- (类似 2 0 1 2)
     * c、i++
     * ps: 情况b需要i--,  防止 （2 0 1 2 的情况）i 需要原地在判断一次
     * ps2: 注意 i <= right 中的‘=’号， （eg: 2，0，1 如果没有等号，结果会是 1,0,2）
     * eg:
     * [2,0,2,1,1,0]
     * 0 2 2 1 1 0
     * 0 0 2 1 1 2
     * 0 0 1 1 2 2
     * <p>
     * <p>
     * 思路2：数组计数
     * 1、int[3] : 红色、白色和蓝色
     * 2、遍历第一遍计数，遍历第二遍覆盖性填充颜色
     */
    public void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        for (int i = 0; i <= right; i++) {
            if (nums[i] == 0) {
                swap(nums, left++, i);
            }
            if (nums[i] == 2) {
                swap(nums, right--, i--);
            }
        }
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // 方式2
    public void sortColors2(int[] nums) {
        int[] color = new int[]{0, 0, 0};

        for (int num : nums) {
            color[num]++;
        }
        int i = 0;
        for (int j = 0; j < color.length; j++) {
            int len = i + color[j];
            for (; i < len; i++) {
                nums[i] = j;
            }
        }
    }


    // -------------------------- 排序和搜索 ------------------------------end
    // -------------------------- 回溯算法 ------------------------------
    // 回溯算法
    //本章精选了一些回溯算法的面试题。
    //
    //电话号码的字母组合 以及 生成括号 都是经典问题。同时，请确保你可以独立完成 全排列 和 子集的解法，它们也是非常经典的例题。

    /**
     * 单词搜索
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * <p>
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     * <p>
     * 示例 1：
     * <p>
     * <p>
     * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     * 输出：true
     * 示例 2：
     * <p>
     * <p>
     * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
     * 输出：true
     * 示例 3：
     * <p>
     * <p>
     * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
     * 输出：false
     *  
     * <p>
     * 提示：
     * <p>
     * m == board.length
     * n = board[i].length
     * 1 <= m, n <= 6
     * 1 <= word.length <= 15
     * board 和 word 仅由大小写英文字母组成
     *  
     * <p>
     * 进阶：你可以使用搜索剪枝的技术来优化解决方案，使其在 board 更大的情况下可以更快解决问题？
     * 相关标签 数组 回溯 矩阵
     * <p>
     * <p>
     * 思路：回溯
     * 1、路径：走过的路径
     * 2、选择列表： 相邻的且没被选过的字符
     * 3、结束条件： 单词完全被匹配 | 无法继续匹配
     * ps:空间优化，使用board[][]自身标记是否访问过
     */
    public boolean exist(char[][] board, String word) {
        char[] ws = word.toCharArray();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (existBacktrack(board, i, j, ws, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean existBacktrack(char[][] board, int i, int j, char[] ws, int index) {
        if (index == ws.length) {
            return true;
        }
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return false;
        }
        if (ws[index] != board[i][j]) {
            return false;
        }

        board[i][j] = '\0';
        boolean res = existBacktrack(board, i + 1, j, ws, index + 1)
                || existBacktrack(board, i - 1, j, ws, index + 1)
                || existBacktrack(board, i, j + 1, ws, index + 1)
                || existBacktrack(board, i, j - 1, ws, index + 1);
        board[i][j] = ws[index];

        return res;
    }

    public boolean exist1(char[][] board, String word) {
        boolean[][] valid = new boolean[board.length][board[0].length];
        char[] ws = word.toCharArray();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (existBacktrack(board, i, j, ws, 0, valid)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean existBacktrack(char[][] board, int i, int j, char[] ws, int index, boolean[][] valid) {
        if (index == ws.length) {
            return true;
        }
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return false;
        }

        if (valid[i][j]) {
            return false;
        }

        if (ws[index] != board[i][j]) {
            return false;
        }

        valid[i][j] = true;
        boolean a = existBacktrack(board, i + 1, j, ws, index + 1, valid);
        boolean b = existBacktrack(board, i - 1, j, ws, index + 1, valid);
        boolean c = existBacktrack(board, i, j + 1, ws, index + 1, valid);
        boolean d = existBacktrack(board, i, j - 1, ws, index + 1, valid);
        valid[i][j] = false;

        return a || b || c || d;
    }

    /**
     * 子集
     * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     * 示例 2：
     * <p>
     * 输入：nums = [0]
     * 输出：[[],[0]]
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= nums.length <= 10
     * -10 <= nums[i] <= 10
     * <p>
     * nums 中的所有元素 互不相同
     * 相关标签 位运算 数组 回溯
     * <p>
     * 思路：回溯
     * 1、路径：已经选择过的数字
     * 2、选择列表： 可以选择的数字
     * 3、结束条件： 无
     * <p>
     * 思路2：位运算解决
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();

        subsetsBacktrack(path, nums, 0, res);
        return res;
    }

    private void subsetsBacktrack(List<Integer> path, int[] nums, int index, List<List<Integer>> res) {
        res.add(new ArrayList<>(path));

        for (int i = index; i < nums.length; i++) {
            path.add(nums[i]);
            subsetsBacktrack(path, nums, i + 1, res);
            path.remove(path.size() - 1);
        }
    }

    public List<List<Integer>> subsets1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        for (int i = 0; i <= nums.length; i++) {
            subsetsDfs(nums, 0, i, cur, res);
        }
        return res;
    }

    private void subsetsDfs(int[] nums, int index, int len, List<Integer> cur, List<List<Integer>> res) {
        if (cur.size() == len) {
            res.add(new ArrayList<>(cur));
            return;
        }
        for (int i = index; i < nums.length; i++) {
            cur.add(nums[i]);
            subsetsDfs(nums, i + 1, len, cur, res);
            cur.remove(cur.size() - 1);
        }
    }


    /**
     * 全排列
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [1,2,3]
     * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * 示例 2：
     * <p>
     * 输入：nums = [0,1]
     * 输出：[[0,1],[1,0]]
     * 示例 3：
     * <p>
     * 输入：nums = [1]
     * 输出：[[1]]
     * <p>
     * 提示：
     * <p>
     * 1 <= nums.length <= 6
     * -10 <= nums[i] <= 10
     * nums 中的所有整数 互不相同
     * 相关标签 数组 回溯
     * <p>
     * 思路：回溯
     * 1、路径：已经选择过的数字
     * 2、选择列表：可以选择的数字
     * 3、结束条件：已选择数字的长度等于 数组的长度
     */
    public List<List<Integer>> permute(int[] nums) {

        List<Integer> path = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();

        boolean[] valid = new boolean[nums.length];
        permuteBackTrack(path, nums, valid, res);

        return res;
    }

    public void permuteBackTrack(List<Integer> path, int[] nums, boolean[] valid, List<List<Integer>> res) {
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (valid[i]) {
                continue;
            }

            path.add(nums[i]);
            valid[i] = true;
            permuteBackTrack(path, nums, valid, res);
            valid[i] = false;
            path.remove(path.size() - 1);
        }
    }

    // 通过交换
    public List<List<Integer>> permute1(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        List<Integer> output = new ArrayList<Integer>();
        for (int num : nums) {
            output.add(num);
        }

        int n = nums.length;
        backtrack(n, output, res, 0);
        return res;
    }

    public void backtrack(int n, List<Integer> output, List<List<Integer>> res, int first) {
        // 所有数都填完了
        if (first == n) {
            res.add(new ArrayList<Integer>(output));
        }
        for (int i = first; i < n; i++) {
            // 动态维护数组
            Collections.swap(output, first, i);
            // 继续递归填下一个数
            backtrack(n, output, res, first + 1);
            // 撤销操作
            Collections.swap(output, first, i);
        }
    }

    public List<List<Integer>> permute2(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> cur = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        permuteDfs(cur, nums, set, res);
        return res;
    }

    private void permuteDfs(LinkedList<Integer> cur, int[] nums, Set<Integer> set, List<List<Integer>> res) {
        if (cur.size() == nums.length) {
            res.add(new ArrayList<>(cur));
        }

        for (int num : nums) {
            if (set.contains(num)) {
                continue;
            }

            cur.add(num);
            set.add(num);
            permuteDfs(cur, nums, set, res);
            set.remove(num);
            cur.removeLast();
        }
    }

    /**
     * 括号生成
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * <p>
     * 示例 1：
     * <p>
     * 输入：n = 3
     * 输出：["((()))","(()())","(())()","()(())","()()()"]
     * 示例 2：
     * <p>
     * 输入：n = 1
     * 输出：["()"]
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= n <= 8
     * 相关标签 字符串 动态规划 回溯
     *
     * <p>
     * 思路：回溯
     * 1、路径：当前 括号组合 的字符串
     * 2、选择列表：是有效的括号
     * - a、放左括号的时候，剩余左括号的数量必须大于0
     * - b、放右括号的时候，剩余左括号的数量必须小于右括号
     * 3、结束条件：n对左右括号放置完毕，即没有剩余的左右括号
     */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        generateParenthesisDfs(n, n, sb, res);
        return res;
    }

    private void generateParenthesisDfs(int left, int right, StringBuilder sb, List<String> res) {
        if (left == 0 && right == 0) {
            res.add(sb.toString());
            return;
        }
        if (left > 0) {
            sb.append("(");
            generateParenthesisDfs(left - 1, right, sb, res);
            sb.deleteCharAt(sb.length() - 1);
        }

        if (left < right) {
            sb.append(")");
            generateParenthesisDfs(left, right - 1, sb, res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }


    /**
     * 电话号码的字母组合
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     * <p>
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * <p>
     * 示例 1：
     * <p>
     * 输入：digits = "23"
     * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
     * 示例 2：
     * <p>
     * 输入：digits = ""
     * 输出：[]
     * 示例 3：
     * <p>
     * 输入：digits = "2"
     * 输出：["a","b","c"]
     *  
     * <p>
     * 提示：
     * 0 <= digits.length <= 4
     * digits[i] 是范围 ['2', '9'] 的一个数字。
     * <p>
     * 相关标签 哈希表 字符串 回溯
     * <p>
     * 思路：(回溯：路径， 选择列表，结束条件)
     * 1、路径：已经选过的字母
     * 2、选择列表：当前数字对应可以选择的字母
     * 3、结束条件：字母数组长度 等于 号码长度
     */
    public List<String> letterCombinations(String digits) {
        Map<Character, String> map = new HashMap<>();
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");

        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        letterCombinationsDfs(digits.toCharArray(), map, sb, res);
        return res;
    }

    private void letterCombinationsDfs(char[] digits, Map<Character, String> map, StringBuilder sb, List<String> res) {
        if (sb.length() >= digits.length) {
            if (sb.length() > 0) {
                res.add(sb.toString());
            }
            return;
        }
        String cs = map.get(digits[sb.length()]);
        for (char c : cs.toCharArray()) {
            sb.append(c);
            letterCombinationsDfs(digits, map, sb, res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }


    // -------------------------- 回溯算法 ------------------------------ end


    // -------------------------- 树和图 ------------------------------
    // 树和图
    // 树是图的简单表示形式，所以我们常用的两种图的遍历方法同样适用于树。
    //
    // 我们推荐以下题目：中序遍历二叉树，每个节点的右向指针 和 岛屿的个数。
    //
    // 请注意，很多树的问题会以 N 叉树 的形式出现，所以请确保你了解什么是 N 叉树 。
    //
    // 说明：岛屿的个数这道题并不是一个树的问题，它可以用图的形式呈现，因此我们将它归类为图的问题。
    //

    /**
     * 岛屿数量
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * <p>
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     * <p>
     * 此外，你可以假设该网格的四条边均被水包围。
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入：grid = [
     * ["1","1","1","1","0"],
     * ["1","1","0","1","0"],
     * ["1","1","0","0","0"],
     * ["0","0","0","0","0"]
     * ]
     * 输出：1
     * 示例 2：
     * <p>
     * 输入：grid = [
     * ["1","1","0","0","0"],
     * ["1","1","0","0","0"],
     * ["0","0","1","0","0"],
     * ["0","0","0","1","1"]
     * ]
     * 输出：3
     * <p>
     * 提示：
     * <p>
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 300
     * grid[i][j] 的值为 '0' 或 '1'
     * 相关标签 深度优先搜索 广度优先搜索 并查集 数组 矩阵
     * <p>
     * 思路：dfs
     * 1、路径：走过的点 （grid）
     * 2、选择列表：上下左右
     * 3、结束条件：遇到了 '0' or 越界
     * ps：若不希望改变grid[][]，声明一个tag[][],进行标记即可
     * <p>
     * 思路2：bfs
     * 思路3：并查集
     */
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != '0') {
                    numIslandsDfs(grid, i, j);
                    res++;
                }
            }
        }
        return res;
    }

    private void numIslandsDfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        numIslandsDfs(grid, i + 1, j);
        numIslandsDfs(grid, i, j + 1);
        numIslandsDfs(grid, i - 1, j);
        numIslandsDfs(grid, i, j - 1);
    }

    public int numIslands1(char[][] grid) {
        boolean[][] tags = new boolean[grid.length][grid[0].length];

        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && !tags[i][j]) {
                    landsBackTrack(grid, i, j, tags);
                    res++;
                }
            }
        }

        return res;
    }

    private void landsBackTrack(char[][] grid, int p, int q, boolean[][] tags) {
        if (p >= grid.length || p < 0 || q >= grid[0].length || q < 0) {
            return;
        }
        if (tags[p][q] || grid[p][q] == '0') {
            return;
        }

        tags[p][q] = true;
        landsBackTrack(grid, p + 1, q, tags);
        landsBackTrack(grid, p - 1, q, tags);
        landsBackTrack(grid, p, q + 1, tags);
        landsBackTrack(grid, p, q - 1, tags);
    }

    /**
     * 二叉搜索树中第K小的元素
     * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。
     * <p>
     * 示例 1：
     * <p>
     * 输入：root = [3,1,4,null,2], k = 1
     * 输出：1
     * 示例 2：
     * <p>
     * 输入：root = [5,3,6,2,4,null,null,1], k = 3
     * 输出：3
     * <p>
     * 提示：
     * <p>
     * 树中的节点数为 n 。
     * 1 <= k <= n <= 104
     * 0 <= Node.val <= 104
     *  
     * <p>
     * 进阶：如果二叉搜索树经常被修改（插入/删除操作）并且你需要频繁地查找第 k 小的值，你将如何优化算法？
     * <p>
     * 相关标签 树 深度优先搜索 二叉搜索树 二叉树
     * <p>
     * 思路：中序遍历
     * 1、二叉搜索树的中序遍历，是一个从小到大的有序序列
     * ps:为何用int[]? 值传递是复制传递
     * 思路2：统计节点个数
     */
    public int kthSmallest(TreeNode root, int k) {
        int[] res = new int[]{k, -1};
        kthHelp(root, res);
        return res[1];
    }

    public void kthHelp(TreeNode root, int[] res) {
        if (root == null) {
            return;
        }
        kthHelp(root.left, res);
        res[0]--;
        if (res[0] == 0) {
            res[1] = root.val;
            return;
        }
        kthHelp(root.right, res);
    }

    private int rank = 0;
    private int val;

    public int kthSmallest1(TreeNode root, int k) {
        kth(root, k);
        return val;
    }

    public void kth(TreeNode root, int k) {
        if (root == null) {
            return;
        }
        kth(root.left, k);
        rank++;
        if (rank == k) {
            val = root.val;
            return;
        }
        kth(root.right, k);
    }


    /**
     * 填充每个节点的下一个右侧节点指针
     * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
     * <p>
     * struct Node {
     * int val;
     * Node *left;
     * Node *right;
     * Node *next;
     * }
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     * <p>
     * 初始状态下，所有 next 指针都被设置为 NULL。
     * <p>
     * 示例 1：
     * <p>
     * 输入：root = [1,2,3,4,5,6,7]
     * 输出：[1,#,2,3,#,4,5,6,7,#]
     * 解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化的输出按层序遍历排列，同一层节点由 next 指针连接，'#' 标志着每一层的结束。
     * 示例 2:
     * <p>
     * 输入：root = []
     * 输出：[]
     * <p>
     * 提示：
     * <p>
     * 树中节点的数量在 [0, 212 - 1] 范围内
     * -1000 <= node.val <= 1000
     * <p>
     * 进阶：
     * 你只能使用常量级额外空间。
     * 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
     * 相关标签 树 深度优先搜索 广度优先搜索 链表 二叉树
     * <p>
     * 思路：bfs
     * 1、二叉树的层序遍历
     * 思路2：递归
     * 1、链接节点的左右子节点, 在递归的链接 左节点的左右子节点， 右节点的左右子节点， 左节点的右节点和右节点的子节点
     * 思路3：dfs
     */
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        connectHelp(root.left, root.right);
        return root;
    }

    private void connectHelp(Node left, Node right) {
        if (left == null || right == null) {
            return;
        }
        left.next = right;

        connectHelp(left.left, left.right);
        connectHelp(right.left, right.right);
        connectHelp(left.right, right.left);
    }

    public Node connect1(Node root) {
        connectDfs(root, null);
        return root;
    }

    public void connectDfs(Node curr, Node next) {
        if (curr == null) {
            return;
        }
        curr.next = next;
        connectDfs(curr.left, curr.right);
        connectDfs(curr.right, curr.next == null ? null : curr.next.left);
    }

    // 写法2
    public Node connect2(Node root) {
        if (null == root || root.left == null) {
            return root;
        }

        root.left.next = root.right;
        if (root.next != null) {
            root.right.next = root.next.left;
        }
        connect2(root.left);
        connect2(root.right);
        return root;
    }

    /**
     * 从前序与中序遍历序列构造二叉树
     * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
     * <p>
     * 示例 1:
     * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * 输出: [3,9,20,null,null,15,7]
     * 示例 2:
     * 输入: preorder = [-1], inorder = [-1]
     * 输出: [-1]
     * <p>
     * 提示:
     * <p>
     * 1 <= preorder.length <= 3000
     * inorder.length == preorder.length
     * -3000 <= preorder[i], inorder[i] <= 3000
     * preorder 和 inorder 均 无重复 元素
     * inorder 均出现在 preorder
     * preorder 保证 为二叉树的前序遍历序列
     * inorder 保证 为二叉树的中序遍历序列
     * <p>
     * 相关标签 树 数组 哈希表 分治 二叉树
     * <p>
     * <p>
     * 思路：分治
     * 1、辅助函数  build(pre, lo1, hi1, in, lo2, hi2);  pre: 前序遍历数组， in: 后序遍历的数组
     * 2、根据 pre的第一个值构造根节点 root， 根据root的val，在in的lo2~hi2中，找到rootVal的位置
     * 3、递归调用构建左右子树, 分别传递左右子树的 前序、中序遍历范围，
     * <p>
     * 优化点：在递归中，每次都需要部分遍历 inorder 数组，来找到 rootVal 的位置 mid
     * 思路： (空间换时间，O(1)时间复杂度获取 mid)
     * 构建一个hashmap，放入 key=inorder[i], val=i。这样 mid = map.get(rootVal)。
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map);
    }

    public TreeNode build(int[] preorder, int lo1, int hi1, int[] inorder, int lo2, int hi2, Map<Integer, Integer> map) {
        if (lo1 > hi1 || lo2 > hi2) {
            return null;
        }
        int rootVal = preorder[lo1];
        int mid = map.get(rootVal);

        int leftSize = mid - lo2;

        TreeNode root = new TreeNode(rootVal);
        root.left = build(preorder, lo1 + 1, lo1 + leftSize, inorder, lo2, mid - 1, map);
        root.right = build(preorder, lo1 + leftSize + 1, hi1, inorder, mid + 1, hi2, map);
        return root;
    }

    public TreeNode buildTree1(int[] preorder, int[] inorder) {
        return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    public TreeNode build(int[] preorder, int lo1, int hi1, int[] inorder, int lo2, int hi2) {
        if (lo1 > hi1 || lo2 > hi2) {
            return null;
        }
        int rootVal = preorder[lo1];
        int mid = lo2;
        for (int i = lo2; i <= hi2; i++) {
            if (inorder[i] == rootVal) {
                mid = i;
                break;
            }
        }
        int leftSize = mid - lo2;

        TreeNode root = new TreeNode(rootVal);
        root.left = build(preorder, lo1 + 1, lo1 + leftSize,
                inorder, lo2, mid - 1);
        root.right = build(preorder, lo1 + leftSize + 1, hi1,
                inorder, mid + 1, hi2);
        return root;
    }

    /**
     * 二叉树的锯齿形层次遍历
     * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     * <p>
     * <p>
     * 示例 1：
     * 输入：root = [3,9,20,null,null,15,7]
     * 输出：[[3],[20,9],[15,7]]
     * <p>
     * 提示：
     * 树中节点数目在范围 [0, 2000] 内
     * -100 <= Node.val <= 100
     * 相关标签 树 广度优先搜索 二叉树
     * <p>
     * 思路1：迭代
     * 1、二叉树的层序遍历
     * 2、如果是偶数层，将遍历的结果翻转 （设 root 为第一层）
     * <p>
     * 思路2：递归
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        int r = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            LinkedList<TreeNode> nextLevel = new LinkedList<>();
            List<Integer> list = new ArrayList<>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node == null) {
                    continue;
                }
                list.add(node.val);
                nextLevel.offer(node.left);
                nextLevel.offer(node.right);
            }
            queue = nextLevel;
            if (r == 1) {
                Collections.reverse(list);
            }
            if (!list.isEmpty()) {
                res.add(list);
            }
            r = r == 0 ? 1 : 0;
        }
        return res;
    }

    public List<List<Integer>> zigzagLevelOrder1(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();

        Queue<TreeNode> level = new LinkedList<>();
        level.offer(root);

        int odd = 1;
        while (!level.isEmpty()) {
            List<Integer> l = new ArrayList<>();
            level = levelNode(level, l);
            if (odd % 2 == 0) {
                Collections.reverse(l);
            }
            res.add(l);
            odd++;
        }
        return res;
    }

    /**
     * 填充当前层的 val
     * 返回下一层的nodes
     */
    public Queue<TreeNode> levelNode(Queue<TreeNode> level, List<Integer> list) {
        Queue<TreeNode> next = new LinkedList<>();
        while (!level.isEmpty()) {
            TreeNode node = level.poll();
            list.add(node.val);
            if (node.left != null) {
                next.offer(node.left);
            }
            if (node.right != null) {
                next.offer(node.right);
            }
        }
        return next;
    }

    // 递归
    public List<List<Integer>> zigzagLevelOrder3(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        traverse(root, 0, res);
        return res;
    }

    private void traverse(TreeNode root, int depth, List<List<Integer>> res) {
        if (root == null) return;
        if (depth >= res.size()) {
            res.add(new ArrayList<>());
        }
        if (depth % 2 == 0) {
            res.get(depth).add(root.val);
        } else {
            res.get(depth).add(0, root.val);
        }
        traverse(root.left, depth + 1, res);
        traverse(root.right, depth + 1, res);
    }

    /**
     * 二叉树的中序遍历
     * 给定一个二叉树的根节点 root ，返回它的 中序 遍历。
     * <p>
     * 示例 1：
     * <p>
     * 输入：root = [1,null,2,3]
     * 输出：[1,3,2]
     * <p>
     * 提示：
     * <p>
     * 树中节点数目在范围 [0, 100] 内
     * -100 <= Node.val <= 100
     *  
     * <p>
     * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
     * <p>
     * 相关标签 栈 树 深度优先搜索 二叉树
     * <p>
     * 思路1：递归
     * 1、中序遍历：左，根，右
     * <p>
     * 思路2：迭代
     * 1、借用栈：先进后出
     * <p>
     * 思路3：颜色标记 [颜色标记法-一种通用且简明的树遍历方法 - 二叉树的中序遍历 - 力扣（LeetCode）](https://leetcode-cn.com/problems/binary-tree-inorder-traversal/solution/yan-se-biao-ji-fa-yi-chong-tong-yong-qie-jian-ming/)
     * 其核心思想如下：
     * 1、使用颜色标记节点的状态，新节点为白色，已访问的节点为灰色。
     * 2、如果遇到的节点为白色，则将其标记为灰色，然后将其右子节点、自身、左子节点依次入栈。
     * 3、如果遇到的节点为灰色，则将节点的值输出。
     */
    class ColorNode {
        public ColorNode(Integer color, TreeNode node) {
            this.color = color;
            this.node = node;
        }

        public Integer color;
        public TreeNode node;
    }

    public List<Integer> inorderTraversal2(TreeNode root) {
        int white = 0;
        int gray = 1;
        List<Integer> res = new ArrayList<>();
        Stack<ColorNode> stack = new Stack<>();
        stack.push(new ColorNode(white, root));
        while (!stack.isEmpty()) {
            ColorNode top = stack.pop();
            if (top.node == null) {
                continue;
            }
            if (top.color == white) {
                stack.push(new ColorNode(white, top.node.right));
                stack.push(new ColorNode(gray, top.node));
                stack.push(new ColorNode(white, top.node.left));
            } else {
                res.add(top.node.val);
            }
        }
        return res;
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        inorderTraversal(root, res);
        return res;
    }

    public void inorderTraversal(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        inorderTraversal(root.left, res);
        res.add(root.val);
        inorderTraversal(root.right, res);
    }


    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        midFor(root, list);
        return list;
    }

    private void midFor(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        midFor(root.left, list);

        list.add(root.val);

        midFor(root.right, list);
    }


    // -------------------------- 树和图 ------------------------------ end

    // -------------------------- 链表 ------------------------------ end
    // 链表
    // 链表问题相对容易掌握。 不要忘记 "双指针解法" ，它不仅适用于数组问题，而且还适用于链表问题。
    // 另一种大大简化链接列表问题的方法是 "Dummy node" 节点技巧 ，所谓 Dummy Node 其实就是带头节点的指针。
    // 我们推荐以下题目：两数相加，相交链表。

    /**
     * 相交链表
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
     * <p>
     * 图示两个链表在节点 c1 开始相交：
     * <p>
     * 题目数据 保证 整个链式结构中不存在环。
     * <p>
     * 注意，函数返回结果后，链表必须 保持其原始结构 。
     * <p>
     * 自定义评测：
     * <p>
     * 评测系统 的输入如下（你设计的程序 不适用 此输入）：
     * <p>
     * intersectVal - 相交的起始节点的值。如果不存在相交节点，这一值为 0
     * listA - 第一个链表
     * listB - 第二个链表
     * skipA - 在 listA 中（从头节点开始）跳到交叉节点的节点数
     * skipB - 在 listB 中（从头节点开始）跳到交叉节点的节点数
     * 评测系统将根据这些输入创建链式数据结构，并将两个头节点 headA 和 headB 传递给你的程序。如果程序能够正确返回相交节点，那么你的解决方案将被 视作正确答案 。
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * <p>
     * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
     * 输出：Intersected at '8'
     * 解释：相交节点的值为 8 （注意，如果两个链表相交则不能为 0）。
     * 从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,6,1,8,4,5]。
     * 在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
     * <p>
     * <p>
     * 提示：
     * <p>
     * listA 中节点数目为 m
     * listB 中节点数目为 n
     * 1 <= m, n <= 3 * 104
     * 1 <= Node.val <= 105
     * 0 <= skipA <= m
     * 0 <= skipB <= n
     * 如果 listA 和 listB 没有交点，intersectVal 为 0
     * 如果 listA 和 listB 有交点，intersectVal == listA[skipA] == listB[skipB]
     *
     * <p>
     * 进阶：你能否设计一个时间复杂度 O(m + n) 、仅用 O(1) 内存的解决方案？
     * <p>
     * 相关标签 哈希表 链表 双指针
     * <p>
     * 思路1： 哈希
     * 1、遍历HeadA，存入哈希
     * 2、遍历HeadB, 判断是否在哈希中存在，存在则返回当前节点
     * 3、返回null
     * <p>
     * --  ---
     * --
     * ---  --
     * <p>
     * 思路2：双指针
     * 1、长 + 短 = 短 + 长
     * ps: (开始时间相同、速度相同、距离相同，则到达终点的时间相同)
     * <p>
     * <p>
     * ps: 跑两遍，
     */
    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode a = headA;
        ListNode b = headB;
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }

    // 可行 8ms
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        HashSet<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }
        while (headB != null) {
            if (set.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }


    /**
     * 奇偶链表
     * 给定单链表的头节点 head ，将所有索引为奇数的节点和索引为偶数的节点分别组合在一起，然后返回重新排序的列表。
     * <p>
     * 第一个节点的索引被认为是 奇数 ， 第二个节点的索引为 偶数 ，以此类推。
     * 请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。
     * 你必须在 O(1) 的额外空间复杂度和 O(n) 的时间复杂度下解决这个问题。
     * <p>
     * 提示:
     * n ==  链表中的节点数
     * 0 <= n <= 104
     * -106 <= Node.val <= 106
     * <p>
     * 相关标签 链表
     * <p>
     * 思路：链表
     * 1、 两个一组往后遍历，奇数节点接在odd， 偶数节点接在even
     * ps: 若使用head往后移动，分奇偶接在两个dummy上，需要注意.next需要断开，避免环形链表
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode odd = head;
        ListNode even = head.next;

        ListNode o = odd;
        ListNode e = even;

        while (e != null && e.next != null) {
            o.next = e.next;
            o = o.next;
            e.next = o.next;
            e = e.next;
        }
        o.next = even;
        return head;
    }

    public ListNode oddEvenList1(ListNode head) {
        ListNode a = new ListNode();
        ListNode b = new ListNode();
        ListNode c = a;
        ListNode d = b;
        while (head != null) {
            c.next = head;
            c = c.next;
            head = head.next;
            c.next = null;
            if (head != null) {
                d.next = head;
                d = d.next;
                head = head.next;
                d.next = null;
            }
        }
        c.next = b.next;
        return a.next;
    }


    /**
     * 两数相加
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * <p>
     * 示例：
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     * <p>
     * 提示：
     * 每个链表中的节点数在范围 [1, 100] 内
     * 0 <= Node.val <= 9
     * 题目数据保证列表表示的数字不含前导零
     * <p>
     * 相关标签 递归 链表 数学
     * <p>
     * 思路1：循环
     * 思路2：递归 (更简洁)
     * 1、l1的节点，l2的节点，进位标识 t；
     * 2、l1不为空 t += l1.val、 l1=l1.next, l2不为空 t += l2.val、 l2=l2.next;
     * 3、t%10作为新节点的值， t/10作为新节点的 t;
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode cur = head;
        int x = 0;
        while (x > 0 || l1 != null || l2 != null) {
            if (l1 != null) {
                x += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                x += l2.val;
                l2 = l2.next;
            }

            ListNode next = new ListNode(x % 10);
            cur.next = next;
            cur = next;
            x = x / 10;
        }
        return head.next;
    }

    public ListNode addTwoNumbers3(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode cur = head;
        int x = 0;
        while (l1 != null && l2 != null) {
            ListNode next = new ListNode();
            int val = l1.val + l2.val;
            if (x > 0) {
                val += x;
            }
            next.val = val % 10;
            x = val / 10;

            cur.next = next;
            cur = next;

            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null) {
            ListNode next = new ListNode();
            int val = l1.val;
            if (x > 0) {
                val += x;
            }
            next.val = val % 10;
            x = val / 10;

            cur.next = next;
            cur = next;

            l1 = l1.next;
        }

        while (l2 != null) {
            ListNode next = new ListNode();
            int val = l2.val;
            if (x > 0) {
                val += x;
            }
            next.val = val % 10;
            x = val / 10;

            cur.next = next;
            cur = next;

            l2 = l2.next;
        }

        if (x > 0) {
            ListNode next = new ListNode();
            next.val = x;
            cur.next = next;
        }
        return head.next;
    }

    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        return this.addTwoNumbers2(l1, l2, 0);
    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2, int a) {
        if (l1 == null && l2 == null) {
            return a == 0 ? null : new ListNode(a);
        }
        if (l1 != null) {
            a += l1.val;
            l1 = l1.next;
        }
        if (l2 != null) {
            a += l2.val;
            l2 = l2.next;
        }
        return new ListNode(a % 10, addTwoNumbers2(l1, l2, a / 10));
    }

    // -------------------------- 链表 ------------------------------ end
    // -------------------------- 数组和字符串 ------------------------------

    // 数组和字符串
    // 数组和字符串问题在面试中出现频率很高，你极有可能在面试中遇到。
    //
    // 我们推荐以下题目：字母异位词分组，无重复字符的最长子串 和 最长回文子串。

    /**
     * 递增的三元子序列
     * 给你一个整数数组 nums ，判断这个数组中是否存在长度为 3 的递增子序列。
     * <p>
     * 如果存在这样的三元组下标 (i, j, k) 且满足 i < j < k ，使得 nums[i] < nums[j] < nums[k] ，返回 true ；否则，返回 false 。
     * <p>
     * 进阶：你能实现时间复杂度为 O(n) ，空间复杂度为 O(1) 的解决方案吗？
     * <p>
     * 示例 1：
     * 输入：nums = [1,2,3,4,5]
     * 输出：true
     * 解释：任何 i < j < k 的三元组都满足题意
     * <p>
     * 相关标签 贪心 数组
     * <p>
     * 思路1： 贪心
     * 1、遍历数组，记录两个值 a,b (a最小值， a<b)
     * 2、若x<=a, 则a=x, 若 a<x<=b, 则b=x
     * 3、若出现一个值大于b，则返回true
     */
    public boolean increasingTriplet(int[] nums) {
        int a = Integer.MAX_VALUE;
        int b = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= a) {
                a = nums[i];
            } else if (nums[i] <= b) {
                b = nums[i];
            } else {
                return true;
            }
        }
        return false;
    }

    // version1 贪心
    public boolean increasingTriplet1(int[] nums) {
        int m1 = Integer.MAX_VALUE;
        int m2 = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > m2) {
                return true;
            } else if (nums[i] > m1) {
                m2 = nums[i];
            } else {
                m1 = nums[i];
            }
        }
        return false;
    }

    /**
     * 最长回文子串
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     * <p>
     * 示例 1：
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     * <p>
     * 提示：
     * 1 <= s.length <= 1000
     * s 仅由数字和英文字母组成
     * <p>
     * 相关标签 字符串 动态规划
     * <p>
     * 思路1：
     * 1、遍历 字符串s, 每次都都向两边搜索 (定义max[3]{回文串的长度len，回文串的左下标i，回文串的右下标j})
     * a、搜索长度为奇数的最长回文串，记录 t1 {len1, i, j}
     * b、搜索长度为偶数的最长回文串，记为 t2 {len2, i, j}
     * c、依据t1[0] 和 t2[0]，令 max 等于 t1 or t2
     * 2、返回 s.substring(max[1], max[2] + 1);
     * <p>
     * 思路2：动态规划
     * 思路3：马拉车
     */
    // 中心扩散 version 2
    public String longestPalindrome(String s) {
        char[] cs = s.toCharArray();

        int[] max = new int[3];
        for (int k = 1; k < cs.length; k++) {
            int[] t1 = longestPalindrome(cs, k - 1, k);
            int[] t2 = longestPalindrome(cs, k - 1, k + 1);

            if (t1[0] > max[0]) {
                max = t1;
            }
            if (t2[0] > max[0]) {
                max = t2;
            }
        }
        return s.substring(max[1], max[2] + 1);
    }

    public int[] longestPalindrome(char[] cs, int i, int j) {
        int[] res = new int[3];
        while (i >= 0 && i < j && j < cs.length) {
            if (cs[i] != cs[j]) {
                break;
            }
            res[1] = i;
            res[2] = j;
            res[0] = j - i + 1;
            i--;
            j++;
        }
        return res;
    }

    // 动态规划
    public String longestPalindrome2(String s) {
        // 中心扩散的方法，其实做了很多重复计算。动态规划就是为了减少重复计算的问题。动态规划听起来很高大上。
        // 其实说白了就是空间换时间，将计算结果暂存起来，避免重复计算。作用和工程中用 redis 做缓存有异曲同工之妙。
        // 我们用一个 boolean dp[l][r] 表示字符串从 i 到 j 这段是否为回文。试想如果 dp[l][r]=true，
        // 我们要判断 dp[l-1][r+1] 是否为回文。只需要判断字符串在(l-1)和（r+1)两个位置是否为相同的字符，是不是减少了很多重复计算。
        // 进入正题，动态规划关键是找到初始状态和状态转移方程。
        // 初始状态，l=r 时，此时 dp[l][r]=true。
        // 状态转移方程，dp[l][r]=true 并且(l-1)和（r+1)两个位置为相同的字符，此时 dp[l-1][r+1]=true。
        if (s == null || s.length() < 2) {
            return s;
        }
        int strLen = s.length();
        int maxStart = 0;  //最长回文串的起点
        int maxEnd = 0;    //最长回文串的终点
        int maxLen = 1;  //最长回文串的长度
        boolean[][] dp = new boolean[strLen][strLen];
        for (int r = 1; r < strLen; r++) {
            for (int l = 0; l < r; l++) {
                if (s.charAt(l) == s.charAt(r) && (r - l <= 2 || dp[l + 1][r - 1])) {
                    dp[l][r] = true;
                    if (r - l + 1 > maxLen) {
                        maxLen = r - l + 1;
                        maxStart = l;
                        maxEnd = r;
                    }
                }
            }
        }
        return s.substring(maxStart, maxEnd + 1);
    }

    // 中心扩散 version 1
    public String longestPalindrome1(String s) {
        String res = "";

        for (int i = 0; i < s.length(); i++) {
            String s1 = isPalindrome(s, i, i);
            String s2 = isPalindrome(s, i, i + 1);

            res = res.length() > s1.length() ? res : s1;
            res = res.length() > s2.length() ? res : s2;
        }

        return res;
    }

    public String isPalindrome(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return s.substring(l + 1, r);
    }

    /**
     * 无重复字符的最长子串
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     * <p>
     * "abba"
     * <p>
     * 提示：
     * 0 <= s.length <= 5 * 104
     * s 由英文字母、数字、符号和空格组成
     * <p>
     * 相关标签 哈希表 字符串 滑动窗口
     * <p>
     * 思路1：哈希+滑动窗口
     * <p>
     * 思路2：数组哈希+滑动窗口
     * 1、int[128] : 记录字符最近出现的位置
     * 2、遍历s
     * a、判断是否需要缩小窗口的左边界
     * b、res = Max(res, 右边界-左边界+1)
     * c、int[cs[i]]=i+1
     * 3、返回res
     */
    public int lengthOfLongestSubstring(String s) {
        int[] map = new int[128];
        char[] cs = s.toCharArray();

        int res = 0;
        int minIndex = 0;
        for (int i = 0; i < cs.length; i++) {
            minIndex = Math.max(minIndex, map[cs[i]]);
            res = Math.max(res, i - minIndex + 1);
            map[cs[i]] = i + 1;
        }
        return res;
    }

    public int lengthOfLongestSubstring2(String s) {
        Map<Character, Integer> map = new HashMap<>(128);
        char[] cs = s.toCharArray();

        int res = 0;
        int minIndex = -1;
        for (int i = 0; i < cs.length; i++) {
            if (map.containsKey(cs[i])) {
                minIndex = Math.max(minIndex, map.get(cs[i]));
            }
            res = Math.max(res, i - minIndex);

            map.put(cs[i], i);
        }
        return res;
    }


    public int lengthOfLongestSubstring1(String s) {
        Map<Character, Integer> map = new HashMap<>(128);
        int left = 0;
        int right = 0;
        int max = 0;

        char[] cs = s.toCharArray();
        while (right < cs.length) {
            char cr = cs[right++];
            map.put(cr, map.getOrDefault(cr, 0) + 1);

            while (map.get(cr) > 1) {
                char cl = cs[left++];
                map.put(cl, map.get(cl) - 1);
            }

            max = Math.max(max, right - left);
        }
        return max;
    }

    /**
     * 字母异位词分组
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * <p>
     * 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。
     * <p>
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * <p>
     * 提示：
     * 1 <= strs.length <= 104
     * 0 <= strs[i].length <= 100
     * strs[i] 仅包含小写字母
     * <p>
     * 相关标签 哈希表 字符串 排序
     * <p>
     * 思路1：哈希+排序
     * 1、map<key, values> : 字母异位词有相同的key, 将具有相同的key的str加入values
     * 2、字母异位词的判断：将str转为char[], 对char[]排序后，再转为字符串即为key
     * 3、返回 new ArrayList<>(map.values());
     * ps: 字母异位词的判断：也可以通过计数每个字符出现的次数来作为key
     * ps2: 字母异位词的判断：可以通过26个素数 乘以 字符出现的次数，来做为key
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] cs = str.toCharArray();
            Arrays.sort(cs);
            String key = new String(cs);
            List<String> temp = map.getOrDefault(key, new ArrayList<>());
            temp.add(str);
            map.put(key, temp);
        }
        return new ArrayList<>(map.values());
    }

    public List<List<String>> groupAnagrams1(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            String key = cCnt(str.toCharArray());
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }

    public String cCnt(char[] cs) {
        int[] cnt = new int[26];
        for (char c : cs) {
            cnt[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (cnt[i] != 0) {
                sb.append((char) (i + 'a')).append(cnt[i]);
            }
        }
        return sb.toString();
    }


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
