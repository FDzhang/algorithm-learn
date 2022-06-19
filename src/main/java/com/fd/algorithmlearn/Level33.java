package com.fd.algorithmlearn;

import java.util.*;

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
     * 寻找重复数
     * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
     * <p>
     * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
     * <p>
     * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [1,3,4,2,2]
     * 输出：2
     * 示例 2：
     * <p>
     * 输入：nums = [3,1,3,4,2]
     * 输出：3
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= n <= 105
     * nums.length == n + 1
     * 1 <= nums[i] <= n
     * nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次
     *  
     * <p>
     * 进阶：
     * 如何证明 nums 中至少存在一个重复的数字?
     * 你可以设计一个线性级时间复杂度 O(n) 的解决方案吗？
     * 相关标签 位运算 数组 双指针 二分查找
     * <p>
     * 思路1：位运算
     * 1、申请一个位set
     * 2、遍历nums，
     * a、判断当前num对应的位置是否为true，是则返回num
     * b、不是则将当前num位置设置为true
     * <p>
     * 思路2：成环法的应用
     * ps: 理解为何 k 是环长度的整数倍, 以及如何找到环的起点（重复数）
     * <p>
     * [287.寻找重复数 - 寻找重复数 - 力扣（LeetCode）](https://leetcode-cn.com/problems/find-the-duplicate-number/solution/287xun-zhao-zhong-fu-shu-by-kirsche/)
     */
    public int findDuplicate(int[] nums) {
        BitSet set = new BitSet(nums.length);
        for (int num : nums) {
            if (set.get(num)) {
                return num;
            } else {
                set.set(num);
            }
        }
        return -1;
    }

    public int findDuplicate2(int[] nums) {
        int fast = 0;
        int slow = 0;

        slow = nums[slow];
        fast = nums[nums[fast]];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }


    /**
     * 最长连续序列
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     * <p>
     * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * <p>
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [100,4,200,1,3,2]
     * 输出：4
     * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
     * 示例 2：
     * <p>
     * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
     * 输出：9
     *  1, 2, 3, 4, 77
     * <p>
     * 提示：
     * <p>
     * 0 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     * 相关标签 并查集 数组 哈希表
     * <p>
     * 思路：数组排序，去重
     * 1、sort 数组nums
     * 2、遍历nums，连续则 更新最大的连续长度max，此外重复元素 不中断，不增加 当前最大连续长度cur
     */
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int max = 1;
        int cur = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                continue;
            }
            if (nums[i - 1] == nums[i] - 1) {
                cur++;
            } else {
                cur = 1;
            }
            max = Math.max(max, cur);
        }
        return max;
    }

    public int longestConsecutive2(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int max = 0;
        for (Integer num : set) {
            if (!set.contains(num - 1)) {
                int cur = 1;
                while (set.contains(num + cur)) {
                    cur++;
                }
                max = Math.max(max, cur);
            }
        }
        return max;
    }


    /**
     * 缺失的第一个正数
     * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
     * <p>
     * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [1,2,0]
     * 输出：3
     * 示例 2：
     * <p>
     * 输入：nums = [3,4,-1,1]
     * 输出：2
     * 示例 3：
     * <p>
     * 输入：nums = [7,8,9,11,12]
     * 输出：1
     *  
     * <p>
     * 提示：
     * <p>
     * 1 <= nums.length <= 5 * 105
     * -231 <= nums[i] <= 231 - 1
     * 相关标签 数组 哈希表
     * <p>
     * 思路：数组 （时间 o(n) 空间o(n)）
     * 1、声明一个num.length+1数boolean的数组b
     * 2、遍历nums,若nums[i]在(0,b.length),则令b[i]=true
     * 3、遍历b，若为false则返回当前下标，每找到则返回b.length
     * ps: 理解：
     * 假设nums的长度为3, 且值为 {x, y, z}，找缺失的第一个正数
     * 1、nums={0,1,2}, 则ret=3
     * 2、nums={1,2,3}, 则ret=4
     * 结论：ret 一定为 {1,2,3,4} 中的某个值。
     * 缺失的第一个正数和nums的长度相关。ret的取值范围为 [1,nun.length+1]
     */
    public int firstMissingPositive(int[] nums) {
        boolean[] b = new boolean[nums.length + 1];
        for (int num : nums) {
            if (num > 0 && num < b.length) {
                b[num] = true;
            }
        }
        for (int i = 1; i < b.length; i++) {
            if (!b[i]) {
                return i;
            }
        }
        return b.length;
    }

    /**
     * 生命游戏
     * 根据 百度百科 ， 生命游戏 ，简称为 生命 ，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
     * <p>
     * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。
     * 每个细胞都具有一个初始状态： 1 即为 活细胞 （live），或 0 即为 死细胞 （dead）。
     * 每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
     * <p>
     * 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
     * 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
     * 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
     * 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
     * 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。给你 m x n 网格面板 board 的当前状态，返回下一个状态。
     * <p>
     *  
     * <p>
     * 示例 1：
     * 输入：board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
     * 输出：[[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
     * 示例 2：
     * 输入：board = [[1,1],[1,0]]
     * 输出：[[1,1],[1,1]]
     *  
     * <p>
     * 提示：
     * m == board.length
     * n == board[i].length
     * 1 <= m, n <= 25
     * board[i][j] 为 0 或 1
     * <p>
     * 进阶：
     * 你可以使用原地算法解决本题吗？请注意，面板上所有格子需要同时被更新：你不能先更新某些格子，然后使用它们的更新后的值再更新其他格子。
     * 本题中，我们使用二维数组来表示面板。原则上，面板是无限的，但当活细胞侵占了面板边界时会造成问题。你将如何解决这些问题？
     * 相关标签 数组 矩阵 模拟
     * <p>
     * 思路：状态标记+数组
     * 1、遍历矩阵，依据周围细胞存活的数量，按规则更新。（初始状态： 1-活，0-死）
     * a、若按规则，当前位置 由 活细胞 变为 死细胞，则先更新为 2 （不能直接变为死细胞，因为要作为活细胞影响周围）
     * b、若按规则，当前位置 由 死细胞 变为 活细胞，则先更新为 -1 （同理）
     * 2、再次遍历矩阵进行更新，将2变为0（按规则将由活变死的细胞置为死亡），将-1变为1（同理）。
     */
    public void gameOfLife(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int cnt = gameOfLife(board, i, j);
                if (board[i][j] == 1 && (cnt < 2 || cnt > 3)) {
                    board[i][j] = 2;
                } else if (board[i][j] == 0 && cnt == 3) {
                    board[i][j] = -1;
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                } else if (board[i][j] == -1) {
                    board[i][j] = 1;
                }
            }
        }
    }

    public int gameOfLife(int[][] board, int i, int j) {
        int[][] d = {{1, 0}, {1, -1}, {1, 1}, {0, 1}, {0, -1}, {-1, 0}, {-1, -1}, {-1, 1}};
        int life = 0;
        for (int[] x : d) {
            int a = x[0] + i;
            int b = x[1] + j;
            if (a >= 0 && a < board.length && b >= 0 && b < board[0].length && board[a][b] > 0) {
                life++;
            }
        }
        return life;
    }

    /**
     * 盛最多水的容器
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
     * <p>
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * <p>
     * 返回容器可以储存的最大水量。
     * <p>
     * 说明：你不能倾斜容器。
     * 示例 1：
     * 输入：[1,8,6,2,5,4,8,3,7]
     * 输出：49
     * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
     * 示例 2：
     * 输入：height = [1,1]
     * 输出：1
     *  
     * <p>
     * 提示：
     * <p>
     * n == height.length
     * 2 <= n <= 105
     * 0 <= height[i] <= 104
     * 相关标签 贪心 数组 双指针
     * <p>
     * 思路：双指针
     * 1、指针i，j分别指向height的头和尾
     * 2、一开始 宽度（j-i）是最大的，指针向中间靠齐，宽度相当于一直在减小，那么需要尽可能的让高度变大。
     * 3、所以将height[i],height[j]中值较小的指针向中间移动。
     * 4、返回遍历过程中的最大值。
     * 优化：由于需要“高度”尽可能变大，所以若height[i+1]<=height[i]，则不需要计算，让i继续左移即可，j同理。
     */
    public int maxArea(int[] height) {
        int i = 0;
        int j = height.length - 1;
        int res = 0;
        while (i < j) {
            int area = (j - i) * Math.min(height[i], height[j]);
            res = Math.max(area, res);

            if (height[i] > height[j]) {
                j--;
            } else {
                i++;
            }
        }
        return res;
    }

    // 优化：由于需要“高度”尽可能变大，所以若height[i+1]<=height[i]，则不需要计算，让i继续左移即可，j同理。
    public int maxArea2(int[] height) {
        int i = 0;
        int j = height.length - 1;
        int res = 0;
        while (i < j) {
            int min = Math.min(height[i], height[j]);
            res = Math.max(res, (j - i) * min);

            while (height[i] <= min && i < j) {
                i++;
            }
            while (height[j] <= min && i < j) {
                j--;
            }
        }
        return res;
    }

    // 超出时间限制
    public int maxArea1(int[] height) {
        int res = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int area = (j - i) * Math.min(height[i], height[j]);
                res = Math.max(area, res);
            }
        }
        return res;
    }

    /**
     * 四数相加 II
     * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
     * <p>
     * 0 <= i, j, k, l < n
     * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
     * 示例 1：
     * 输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
     * 输出：2
     * 解释：
     * 两个元组如下：
     * 1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
     * 2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
     * 示例 2：
     * <p>
     * 输入：nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
     * 输出：1
     *  
     * <p>
     *   提示：
     * n == nums1.length
     * n == nums2.length
     * n == nums3.length
     * n == nums4.length
     * 1 <= n <= 200
     * -228 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 228
     * 相关标签 数组 哈希表
     * <p>
     * 思路1：哈希表
     * 1、初始化map：key数字，value数字出现的次数
     * 2、遍历nums1，nums2 将两者相加的结果作为map的key，value为出现的次数
     * 3、遍历nums3，nums4，若两者相加的结果再乘以-1，在map中出现过，则res加上此数出现的次数
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : nums1) {
            for (int j : nums2) {
                map.merge(i + j, 1, Integer::sum);
            }
        }

        int res = 0;
        for (int i : nums3) {
            for (int j : nums4) {
                int t = -(i + j);
                res += map.getOrDefault(t, 0);
            }
        }
        return res;
    }

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
