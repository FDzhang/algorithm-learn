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
