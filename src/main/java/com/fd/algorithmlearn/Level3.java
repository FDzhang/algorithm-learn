package com.fd.algorithmlearn;

import java.util.*;

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
     * 寻找重复数
     * 定一个包含 n + 1 个整数的数组 nums ，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
     * <p>
     * 假设 nums 只有 一个重复的整数 ，找出 这个重复的数 。
     * <p>
     * 你设计的解决方案必须不修改数组 nums 且只用常量级 O(1) 的额外空间。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-hard/xwz4lj/
     *
     * 思路： 成环法的应用
     * ps: 理解为何 k 是环长度的整数倍, 以及如何找到环的起点（重复数）
     *
     * [双指针技巧总结 :: labuladong的算法小抄](https://labuladong.gitee.io/algo/2/19/50/)
     * [287.寻找重复数 - 寻找重复数 - 力扣（LeetCode）](https://leetcode-cn.com/problems/find-the-duplicate-number/solution/287xun-zhao-zhong-fu-shu-by-kirsche/)
     */
    public int findDuplicate(int[] nums) {
        int slow = 0;
        int fast = 0;

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
     * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * <p>
     * 思路1：
     * 1、先排序，后计数
     * <p>
     * <p>
     * 可参考链接： [\[LeetCode\] 128. 最长连续序列 - 威行天下 - 博客园](https://www.cnblogs.com/powercai/p/11181681.html)
     */
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);
        int cnt = 1;
        int res = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                continue;
            }
            if (nums[i] - 1 == nums[i - 1]) {
                cnt++;
            } else {
                cnt = 1;
            }
            res = Math.max(res, cnt);
        }
        return res;
    }


    /**
     * 缺失的第一个正数
     * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
     * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-hard/xwkftg/
     * <p>
     * 思路：from 讨论区
     * 方法一：快速排序+正整数逐个记录校验法，效率一般：
     * 方法四：置换法（每次置换有一个是被放到了正确的位置）
     * <p>
     * 我们可以把给定的数组当作一个hash表来用，怎么用呢？把nums[i]和下标i形成一组映射关系，也就是nums[0]=1,nums[1]=2...
     * 搞清楚这个之前，得搞清楚题意，题意是要找出连续的正数中缺失的最小正数，那么我们就可以得出我们所要找的这个最小正数的最大值和最小值，
     * 最小值毫无疑问是1，最大值明显和数组长度挂钩，也就是当数组中的所有值都是符合该映射，则缺失的正数值就是nums.size()+1.
     * 通过交换实现映射关系，当然细节情况需要处理，那就是不能让数组下标访问越界，所以需要交换的元素一定小于等于nums.size()。
     * 最后通过从左往右检查谁不和下标形成映射关系，谁就是缺失的正数。
     * <p>
     * 细节1：如果不用while直接用if肯定会由于直接大跨度的交换导致线性的重复和遗漏，所以需要while不断的判断新转移过来的 元素。
     * 细节2：如果用判断条件换成nums[i]!=i+1会在有重复数据的时候扑街(由于如果出现相同元素，然而它检查的竟不是同一个位置，这导致死循环)，
     * 而直接用nums[nums[i]-1]是否等于nums[i]它碰到同样的元素永远只会检查一个位置，所以那个位置正确了，则它就不会再移动了。
     */
    public int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                swapAB(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return nums.length + 1;
    }

    private void swapAB(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public int firstMissingPositive1(int[] nums) {
        Arrays.sort(nums);

        int min = 1;
        for (int num : nums) {
            if (num <= 0) {
                continue;
            }
            if (num > min) {
                return min;
            }
            if (num == min) {
                min++;
            }
        }
        return min;
    }


    /**
     * 生命游戏
     * 根据 百度百科 ，生命游戏，简称为生命，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
     * <p>
     * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态：1 即为活细胞（live），或 0 即为死细胞（dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
     * <p>
     * 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
     * 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
     * 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
     * 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
     * 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。给你 m x n 网格面板 board 的当前状态，返回下一个状态。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-hard/xwk53e/
     * <p>
     * 思路：
     * 1、new一个新的矩阵res
     * 2、遍历board，根据board[i][j]和其周围的8个细胞的状态，决定res[i][j]处，细胞的状态
     * <p>
     * 优化1（代码长度）：用 两个int数组 遍历周围8个格子
     * 优化2（空间优化）: from 讨论区
     * 原地操作怎么实现呢？这是根据这道生命游戏的特点来的，由于生命游戏只存在0和1，那么我们可以人为再制造两个状态来表示它变化之前的状态，
     * 比如用2表示0变成1后的状态，在它被用于计算又多少个1时被当作0处理，而最后的修正环节它就被当作1处理，同理1变为0也是类似的操作。
     */
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;

        int[][] res = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = lifeOrDead(board, i, j);
            }
        }
        System.arraycopy(res, 0, board, 0, m);
    }

    private int lifeOrDead(int[][] board, int i, int j) {
        int m = board.length;
        int n = board[0].length;
        int cnt = 0;

        int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};

        for (int k = 0; k < 8; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && x < m && y >= 0 && y < n && board[x][y] == 1) {
                cnt++;
            }
        }

        if (board[i][j] == 1) {
            if (cnt == 2 || cnt == 3) {
                return 1;
            }
        } else {
            if (cnt == 3) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * 盛最多水的容器
     * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * 说明：你不能倾斜容器。
     * <p>
     * 思路: 双指针
     * 由于答案容器的面积与两个变量有关，我们从一个变量的最大值开始，
     * 不断枚举所有可能(使得其中一个变量连续性的变小，而另一个变量可能变大也可能变小，但绝不是必定变小)
     */
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;

        int res = 0;
        while (left < right) {
            int m = Math.min(height[left], height[right]);
            res = Math.max(res, m * (right - left));

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return res;
    }


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
