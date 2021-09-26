package com.fd.algorithmlearn;

import com.fd.algorithmlearn.linked.ListNode;
import com.fd.algorithmlearn.tree.Node;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
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
//        int[] x = {1, -1, -1, 0};
//        System.out.println(threeSum(x));
//        HashMap<Integer, String> map = new HashMap<>();
//        map.put(1, "123");
        System.err.println('A' - 64);
        System.err.println('Z' - 64);
//        System.err.println(map.get(null));
//        System.out.println(lengthOfLongestSubstring1("tmmzuxt"));
    }

    /**
     * x 的平方根
     * <p>
     * 给你一个非负整数 x ，计算并返回 x 的 平方根 。
     * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
     * 注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xwrzwc/
     * <p>
     * 思路：
     * 1、mid = (lo + hi)/2
     * 2、
     * midmid = x , return mid
     * midmid > x , hi = mid-1;
     * mid*mid < x , lo = mid+1; (注意 结果只保留整数部分)
     */
    public int mySqrt(int x) {
        int lo = 0;
        int hi = x;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            // 防溢出
            long y = (long) mid * mid;
            if (y == x) {
                return mid;
            } else if (y > x) {
                hi = mid - 1;
            } else {
                // 结果只保留 整数部分
                y = (long) (mid + 1) * (mid + 1);
                if (y > x) {
                    return mid;
                }

                lo = mid + 1;
            }
        }
        return -1;
    }


    /**
     * Pow(x, n)
     * 实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，xn）。
     * <p>
     * 2 8
     * 2*2 4
     * 4*4 2
     * 16*16 1
     * 1*16*16
     * <p>
     * 2 9
     * 2*2 4 2
     * <p>
     * <p>
     * 思路： 折半计算
     * 1、2^8 == (2*2)^4
     * 2、n为奇数时， res需要多乘一个x
     * 3、n>=0,返回res; n<0返回 1/res
     * <p>
     * 思路2：递归
     * 1、幂 - 百度百科
     */
    public double myPow(double x, int n) {
        double res = 1.0;

        int m = n;
        while (m != 0) {
            if (m % 2 != 0) {
                res *= x;
            }
            x *= x;
            m /= 2;
        }
        return n >= 0 ? res : 1 / res;
    }

    public double myPow2(double x, int n) {
        double res = myPowH(x, n);
        return n >= 0 ? res : 1 / res;
    }

    public double myPowH(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if ((n & 1) == 1) {
            return x * myPowH(x * x, n / 2);
        }
        return myPowH(x * x, n / 2);
    }

    /**
     * Excel表列序号
     * 给你一个字符串 columnTitle ，表示 Excel 表格中的列名称。返回该列名称对应的列序号。
     * <p>
     * 思路：进制转换
     * 1、1~26 映射 A~Z
     * 2、字符串从右往左读, 每往左一位 多乘一个 26
     */
    public int titleToNumber(String columnTitle) {

        char[] cs = columnTitle.toCharArray();
        int res = 0;
        int cnt = 1;

        for (int i = cs.length - 1; i >= 0; i--) {
            res += (cs[i] - 64) * cnt;
            cnt *= 26;
        }

        return res;
    }

    /**
     * 阶乘后的零
     * 给定一个整数 n ，返回 n! 结果中尾随零的数量。
     * <p>
     * 进阶：你可以设计并实现对数时间复杂度的算法来解决此问题吗？
     * <p>
     * 思路：（来源 讨论中的分析）
     * 1、计算5的个数
     */
    public int trailingZeroes(int n) {
        int cnt = 0;
        while (n >= 5) {
            cnt += n / 5;
            n /= 5;
        }
        return cnt;
    }


    /**
     * 快乐数
     * 编写一个算法来判断一个数 n 是不是快乐数。
     * 「快乐数」定义为：
     * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
     * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
     * 如果 可以变为  1，那么这个数就是快乐数。
     * 如果 n 是快乐数就返回 true ；不是，则返回 false 。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xw99u7/
     * <p>
     * 思路：
     * 1、使用set去重， 如果出现重复， 就会进入死循环， 返回false
     * 2、可以变为  1， 返回true
     */
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        int sum = 0;
        int temp = n;
        while (temp != 1) {
            while (temp > 0) {
                int mod = temp % 10;
                temp /= 10;
                sum += (mod * mod);
            }
            if (!set.add(sum)) {
                return false;
            }
            temp = sum;
            sum = 0;
        }
        return true;
    }


    /**
     * 常数时间插入、删除和获取随机元素
     * 实现RandomizedSet 类：
     * <p>
     * RandomizedSet() 初始化 RandomizedSet 对象
     * bool insert(int val) 当元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 。
     * bool remove(int val) 当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
     * int getRandom() 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
     * 你必须实现类的所有函数，并满足每个函数的 平均 时间复杂度为 O(1) 。
     * <p>
     * 思路：
     * 1、Map : 值，索引， List: 值； Random : 随机数
     * 2、添加：存在，则返回； 不存在，list.add、map.put
     * 3、删除：不存在，则返回，
     * 存在，
     * map.get(值) -> 索引、
     * list.get(len-1) -> last
     * <p>
     * list.set(索引, last)、
     * map.set(last, 索引)、
     * <p>
     * list.remove(len-1)、
     * map.remove(值)
     * 4、随机获取； list.get(random.next(len))
     */
    class RandomizedSet {

        private HashMap<Integer, Integer> map;
        private ArrayList<Integer> list;
        private Random random;

        /**
         * Initialize your data structure here.
         */
        public RandomizedSet() {
            map = new HashMap<>();
            list = new ArrayList<>();
            random = new Random();
        }

        /**
         * Inserts a value to the set. Returns true if the set did not already contain the specified element.
         */
        public boolean insert(int val) {
            if (map.containsKey(val)) {
                return false;
            }

            list.add(val);
            map.put(val, list.size() - 1);

            return true;
        }

        /**
         * Removes a value from the set. Returns true if the set contained the specified element.
         */
        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }
            // 获取
            Integer index = map.get(val);
            Integer last = list.get(list.size() - 1);
            // 更新
            list.set(index, last);
            map.put(last, index);
            // 失效
            list.remove(list.size() - 1);
            map.remove(val);
            return true;
        }

        /**
         * Get a random element from the set.
         */
        public int getRandom() {
            return list.get(random.nextInt(list.size()));
        }
    }


    /**
     * 二叉树的序列化与反序列化
     * 序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
     * 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xwxa3m/
     * <p>
     * 思路：（前序 or 后序 or 层序； 中序不可行）
     * 1、二叉树的前序遍历
     */
    private final String NULL = "#";
    private final String SEP = ",";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }

    private void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }
        sb.append(root.val).append(SEP);

        serialize(root.left, sb);
        serialize(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        LinkedList<String> nodes = new LinkedList<>();
        Collections.addAll(nodes, data.split(SEP));

        return deserialize(nodes);
    }

    private TreeNode deserialize(LinkedList<String> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        String val = nodes.removeFirst();
        if (NULL.equals(val)) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(val));

        root.left = deserialize(nodes);
        root.right = deserialize(nodes);

        return root;
    }


    /**
     * 最长上升子序列
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xwhvq3/
     * <p>
     * 思路：
     * 1、dp[i]: 到当前为止的 最长上升子序列的长度
     * 2、状态转移： 若 nums[j] < nums[i], 则 dp[i] = Max(dp[i], dp[j]+1)
     */
    public int lengthOfLIS(int[] nums) {
        int max = 0;
        int[] dp = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if (dp[i] > max) {
                max = dp[i];
            }
        }

        return max;
    }

    /**
     * 零钱兑换
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
     * 你可以认为每种硬币的数量是无限的。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvf0kh/
     * <p>
     * 思路：
     * 1、dp[i]: 到达当前值的最小硬币数
     * 2、初始化： dp[c[i]] = 1
     * 3、循环： 若dp[i]！=0, 则 dp[i+c[i]] = dp[i+c[i]]==0? dp[i]+1: Min(dp[i]+1, dp[i+c[i]])
     * 4、结束条件： i >= amount
     */
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        int[] dp = new int[amount + 1];

        for (int i = 1; i < amount + 1; i++) {
            int min = Integer.MAX_VALUE - 1;

            for (int j = 0; j < coins.length && i >= coins[j]; j++) {
                min = Math.min(min, dp[i - coins[j]]);
            }

            dp[i] = min + 1;
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    public int coinChange1(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        Arrays.sort(coins);
        if (coins[0] > amount) {
            return -1;
        }

        int[] dp = new int[amount + 1];
        for (int coin : coins) {
            if (coin <= amount) {
                dp[coin] = 1;
            }
        }

        for (int i = 0; i <= amount; i++) {
            if (dp[i] != 0) {
                for (int coin : coins) {
                    if (i + coin <= amount && i + coin > 0) {
                        dp[i + coin] = dp[i + coin] == 0 ? dp[i] + 1 : Math.min(dp[i] + 1, dp[i + coin]);
                    }
                }
            }
        }
        return dp[amount] == 0 ? -1 : dp[amount];
    }

    /**
     * 不同路径
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     * 问总共有多少条不同的路径？
     * <p>
     * <p>
     * 3 3
     * <p>
     * 1 1 1
     * 1 2 3
     * 1 3 6
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvjigd/
     * <p>
     * 思路：
     * 1、到达前网格的不同路径数（dp[i][j]） = 到达左边网格的不同路径数(dp[i][j-1]) + 到达上边网格的不同路径数(dp[i-1][j])
     * 2、返回dp[m-1][n-1]
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                    continue;
                }

                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }

    public int uniquePaths1(int m, int n) {
        int[] dp = new int[n];

        Arrays.fill(dp, 1);

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j - 1] + dp[j];
            }
        }
        return dp[n - 1];
    }

    /**
     * 跳跃游戏
     * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个下标。
     * <p>
     * 输入：nums = [2,3,1,1,4]
     * 输出：true
     * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
     * 思路： （动态规划）
     * 1、dp[i] ：从i开始最多可以往后跳几步
     * 2、dp[i]=max(dp[i-1] - 1, nums[i])
     * 3、结束条件： dp[i] + i + 1 >= nums.len 或者 dp[i]==0
     * <p>
     * 思路2：
     * 1、从后往前走，标记结尾为end
     * 2、如果nums[i]能走到end, 则将end置为i, 最后判断end是否为0
     * <p>
     * 思路3：（贪心）
     * 1、从前往后走，标记 能到达的最远距离 为 maxFar, maxFar初始化为nums[0]
     * 2、如果 maxFar >= i, 表示位置 i 能达到, 则更新 maxFar = max( maxFar, nums[i] + i)
     * 3、返回 maxFar 是否 大于等于 (nums.length - 1);
     */
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        if (nums[0] == 0) {
            return false;
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] - 1, nums[i]);
            if (dp[i] + i + 1 >= nums.length) {
                return true;
            } else if (dp[i] == 0) {
                return false;
            }
        }
        return false;
    }

    public boolean canJump1(int[] nums) {
        int len = nums.length;
        int end = len - 1;
        for (int i = len - 2; i >= 0; i--) {
            if (nums[i] >= end - i) {
                end = i;
            }
        }
        return end == 0;
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


    /**
     * 搜索二维矩阵 II
     * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
     * 每行的元素从左到右升序排列。
     * 每列的元素从上到下升序排列。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvc64r/
     * <p>
     * 思路：
     * 1、从右上角开始搜索 （从左上角的话 向右和向下都是变大，而从右上角 向左是变小， 向下是变大，天然二分）
     * 2、m[i][j] > target 向左， m[i][j] < target 向下
     * 3、结束条件： m[i][j] == target 或者 到了最左边和最下面 为止
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int i = 0;
        int j = matrix[0].length - 1;
        while (i < matrix.length && j >= 0) {
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
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvyz1t/
     * <p>
     * nums = [4,5,6,7,0,1,2], target = 0
     * <p>
     * lo= 0  hi=6  mid=3
     * nums[mid]>nums[right] -> target < nums[mid] && target < nums[left]  -> lo = mid+1 = 4
     */
    public int search(int[] nums, int target) {
        int res = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                res = i;
                break;
            }
        }
        return res;
    }

    public int search1(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < nums[hi]) {
                if (target > nums[mid] && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            } else {
                if (target < nums[mid] && target >= nums[lo]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
        }
        return -1;
    }


    /**
     * 在排序数组中查找元素的第一个和最后一个位置
     * <p>
     * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xv4bbv/
     * <p>
     * 思路：
     * 1、利用二分查找，找到目标
     * 2、双指针 分别向左 向右, 寻找开始和结束的位置
     */
    public int[] searchRange(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        int index = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                index = mid;
                break;
            } else if (nums[mid] < target) {
                lo = mid + 1;
            } else if (nums[mid] > target) {
                hi = mid - 1;
            }
        }

        int start = index;
        int end = index;
        if (index >= 0) {
            while (start - 1 >= 0 && nums[start] == nums[start - 1]) {
                start--;
            }
            while (end + 1 < nums.length && nums[end] == nums[end + 1]) {
                end++;
            }
        }
        return new int[]{start, end};
    }

    /**
     * 合并区间
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
     * <p>
     * 示例 1：
     * <p>
     * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出：[[1,6],[8,10],[15,18]]
     * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     * <p>
     * 思路：
     * 1、依据 intervals[row][0] 排序
     * 2、依据 intervals[row][1] 和 intervals[row+1][0] 判断是否合并
     */
    public int[][] merge(int[][] intervals) {
        List<int[]> res = new ArrayList<>();
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        for (int i = 0; i < intervals.length; i++) {
            int[] m = new int[]{intervals[i][0], intervals[i][1]};

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
        return res.toArray(new int[res.size()][2]);
    }

    /**
     * 寻找峰值
     * 峰值元素是指其值大于左右相邻值的元素。
     * 给你一个输入数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
     * 你可以假设 nums[-1] = nums[n] = -∞ 。
     * <p>
     * <p>
     * 思路：
     * 1、nums长度为1, 峰值在首位, 峰值在尾部, 三种特殊情况
     * 2、从 1 ~ (nums.len-2) 循环查找
     */
    public int findPeakElement(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        if (nums[0] > nums[1]) {
            return 0;
        }
        if (nums[nums.length - 1] > nums[nums.length - 2]) {
            return nums.length - 1;
        }

        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i] > nums[i - 1] && nums[i] > nums[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 数组中的第K个最大元素
     * <p>
     * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
     * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     * <p>
     * 思路：
     * 1、排序后，倒数第k个元素
     * ps!!!: 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     */
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);

        return nums[nums.length - k];
    }


    /**
     * 前 K 个高频元素
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     * <p>
     * 思路：
     * 1、计数: 数字 x 对应的频率 xF
     * 2、将 {x, xF} 放入最大堆
     * 3、从最大堆中取出前k个元素
     */
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> xxF = new HashMap<>();
        for (int num : nums) {
            if (!xxF.containsKey(num)) {
                xxF.put(num, 0);
            }
            xxF.put(num, xxF.get(num) + 1);
        }

        PriorityQueue<int[]> priority = new PriorityQueue<>((a, b) -> Integer.compare(b[1], a[1]));
        for (Map.Entry<Integer, Integer> item : xxF.entrySet()) {
            priority.offer(new int[]{item.getKey(), item.getValue()});
        }

        int[] res = new int[k];
        for (int i = 0; i < res.length; i++) {
            if (!priority.isEmpty()) {
                res[i] = priority.poll()[0];
            }
        }
        return res;
    }

    public int[] topKFrequent1(int[] nums, int k) {
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

    /**
     * 颜色分类
     * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvg25c/
     * <p>
     * <p>
     * 思路1：
     * 1、java自带排序函数
     * <p>
     * 思路2：
     * 1、使用一个 int[] cnt = new int[3] 进行计数
     * <p>
     * 思路3：
     * 1、双指针
     * 2、左指针指0, 右指针指2, 遍历循环, 指针向中间靠拢 （注意换2的时候不要漏算）
     */
    public void sortColors(int[] nums) {
        Arrays.sort(nums);
    }

    /**
     * 单词搜索
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvkwe2/
     * <p>
     * 思路：回溯
     * 1、路径：走过的路径
     * 2、选择列表： 相邻的且没被选过的字符
     * 3、结束条件： 单词完全被匹配 | 无法继续匹配
     */
    public boolean exist(char[][] board, String word) {

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
        // 单词完全被匹配
        if (index == ws.length) {
            return true;
        }
        // 不能超过边界
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return false;
        }
        // 已选的不能再选
        if (valid[i][j]) {
            return false;
        }
        // 无法继续匹配
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
     * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     * <p>
     * 思路：回溯
     * 1、路径：已经选择过的数字
     * 2、选择列表： 可以选择的数字
     * 3、结束条件： 无
     * <p>
     * 123
     * 1
     * 1 2
     * 1 2 3
     * 1 3
     * 2
     * 2 3
     * 3
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

    /**
     * 全排列
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
     * <p>
     * 思路：（回溯）
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

    /**
     * 括号生成
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * 有效括号组合需满足：左括号必须以正确的顺序闭合。
     * <p>
     * 思路：回溯 （路径， 选择列表， 结束条件）
     * 1、路径：已经选择过的括号
     * 2、选择列表： 剩下的括号 （判定条件：1、右括号的数量不能大于左括号的数量；2、左右括号的数量不能超过n）
     * 3、结束条件： 括号长度 等于 n*2
     * <p>
     * <p>
     * 思路2： 回溯
     */
    // 写法1------------------------------
    public List<String> generateParenthesis(int n) {
        char[] cs = new char[]{'(', ')'};
        StringBuilder sb = new StringBuilder();

        List<String> res = new ArrayList<>();
        genBacktrack(sb, cs, n, res);
        return res;
    }

    private void genBacktrack(StringBuilder sb, char[] cs, int n, List<String> res) {
        if (sb.length() == n * 2) {
            if (valid(sb, n)) {
                res.add(sb.toString());
            }
            return;
        }

        for (char c : cs) {
            if (!valid(sb, n)) {
                continue;
            }

            sb.append(c);
            genBacktrack(sb, cs, n, res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public boolean valid(StringBuilder sb, int n) {
        if (sb.length() == 0) {
            return true;
        }
        // 第一个不能是 右括号
        if (sb.charAt(0) == ')') {
            return false;
        }
        char[] chars = sb.toString().toCharArray();
        int ml = 0;
        int mr = 0;
        for (char c : chars) {
            if (c == '(') {
                ml++;
            } else if (c == ')') {
                mr++;
            }
        }
        // 左，右括号的数量 都不能大于 n
        if (ml > n) {
            return false;
        } else if (mr > n) {
            return false;
        }
        // 右括号的数量不能大于左括号
        return mr <= ml;
    }
    // 写法1------------------------------

    // 写法2----------------------------------
    public List<String> generateParenthesis1(int n) {
        List<String> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }

        StringBuilder path = new StringBuilder();
        dfs(path, n, n, res);
        return res;
    }

    private void dfs(StringBuilder path, int left, int right, List<String> res) {
        if (left == 0 && right == 0) {
            res.add(path.toString());
            return;
        }

        // 有效括号的判定
        if (left > right) {
            return;
        }

        if (left > 0) {
            path.append("(");
            dfs(path, left - 1, right, res);
            path.deleteCharAt(path.length() - 1);
        }

        if (right > 0) {
            path.append(")");
            dfs(path, left, right - 1, res);
            path.deleteCharAt(path.length() - 1);
        }
    }
    // 写法2----------------------------------

    /**
     * 电话号码的字母组合
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xv8ka1/
     * <p>
     * 思路：(回溯：路径， 选择列表，结束条件)
     * 1、路径：已经选过的字母
     * 2、选择列表：当前数字对应可以选择的字母
     * 3、结束条件：字母数组长度 等于 号码长度
     */
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }
        List<String> res = new ArrayList<>();
        Map<Character, char[]> keys = new HashMap<>();
        keys.put('2', new char[]{'a', 'b', 'c'});
        keys.put('3', new char[]{'d', 'e', 'f'});
        keys.put('4', new char[]{'g', 'h', 'i'});
        keys.put('5', new char[]{'j', 'k', 'l'});
        keys.put('6', new char[]{'m', 'n', 'o'});
        keys.put('7', new char[]{'p', 'q', 'r', 's'});
        keys.put('8', new char[]{'t', 'u', 'v'});
        keys.put('9', new char[]{'w', 'x', 'y', 'z'});

        char[] digitChars = digits.toCharArray();
        StringBuilder path = new StringBuilder();
        letterBackTrack(path, digitChars, 0, keys, res);
        return res;
    }

    private void letterBackTrack(StringBuilder path, char[] digitChars, int index, Map<Character, char[]> keys, List<String> res) {
        if (path.length() == digitChars.length) {
            res.add(path.toString());
            return;
        }

        char[] chars = keys.get(digitChars[index]);
        for (char c : chars) {
            path.append(c);
            letterBackTrack(path, digitChars, index + 1, keys, res);
            path.deleteCharAt(index);
        }
    }

    private String letterBuild(List<Character> path) {
        if (path == null || path.isEmpty()) {
            return "";
        }
        return path.stream().map(String::valueOf).collect(Collectors.joining());
    }

    /**
     * 二叉搜索树中第K小的元素
     */
    private int rank = 0;
    private int val;

    public int kthSmallest(TreeNode root, int k) {
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
     * <p>
     * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     * 初始状态下，所有 next 指针都被设置为 NULL。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvijdh/
     * <p>
     * 思路：
     * 1、二叉树的层序遍历
     * <p>
     * 思路2：
     * 1、链接每个节点的 左右子节点
     * 2、若当前节点的next不为空，则将 当前节点（node）的右子节点的next 指向 node.next 的左子节点
     */
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Node> level = new LinkedList<>();
        level.offer(root);

        while (!level.isEmpty()) {
            level = levelNode1(level);
        }
        return root;
    }

    /**
     * 处理当前层
     * 返回下一层的nodes
     */
    public Queue<Node> levelNode1(Queue<Node> level) {
        Queue<Node> next = new LinkedList<>();

        Node pre = null;
        while (!level.isEmpty()) {
            Node node = level.poll();
            if (pre == null) {
                pre = node;
            } else {
                pre.next = node;
                pre = node;
            }

            if (node.left != null) {
                next.offer(node.left);
            }
            if (node.right != null) {
                next.offer(node.right);
            }
        }
        return next;
    }

    public Node connect2(Node root) {
        if (null == root || root.left == null) return root;

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
     * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * Output: [3,9,20,null,null,15,7]
     *
     * <p>
     * 思路：
     * 1、辅助函数  build(pre, lo1, hi1, in, lo2, hi2);  pre: 前序遍历数组， in: 后序遍历的数组
     * 2、根据 pre的第一个值构造根节点 root， 根据root的val，在in的lo2~hi2中，找到rootVal的位置
     * 3、递归调用构建左右子树, 分别传递左右子树的 前序、中序遍历范围，
     * <p>
     * 优化点：在递归中，每次都需要部分遍历 inorder 数组，来找到 rootVal 的位置 mid
     * 思路： (空间换时间，O(1)时间复杂度获取 mid)
     * 构建一个hashmap，放入 key=inorder[i], val=i。这样 mid = map.get(rootVal)。
     *
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvix0d/
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
        root.left = build(preorder, lo1 + 1, lo1 + leftSize,
                inorder, lo2, mid - 1, map);
        root.right = build(preorder, lo1 + leftSize + 1, hi1,
                inorder, mid + 1, hi2, map);
        return root;
    }

    /**
     * 二叉树的锯齿形层次遍历
     * 给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     * <p>
     * 例如：
     * 给定二叉树 [3,9,20,null,null,15,7],
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvle7s/
     * <p>
     * 思路：
     * 1、二叉树的层序遍历
     * 2、将遍历的结果翻转 （偶数层）
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
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

    /**
     * 二叉树的中序遍历
     * 给定一个二叉树的根节点 root ，返回它的 中序 遍历。
     * <p>
     * 思路：
     * 1、中序遍历 ： 左、根、右
     */
    public List<Integer> inorderTraversal(TreeNode root) {
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


    /**
     * 相交链表
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null 。
     * <p>
     * 图示两个链表在节点 c1 开始相交：
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xv02ut/
     * <p>
     * 思路：
     * 1、长 + 短 = 短 + 长
     * --   --
     * --   --
     * ---  ---
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
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

    /**
     * 奇偶链表
     * <p>
     * 给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。
     * <p>
     * 请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvdwtj/
     * <p>
     * 思路：
     * 1、 两个一组往后遍历，奇数节点接在odd， 偶数节点接在even
     *
     * <p>
     * <p>
     * eg: 1->2->3->4->5->NULL
     * eg: 1->2->3->4->NULL
     * <p>
     * 1 3 5 2 4
     * <p>
     * odd 1 3 5
     * even 2 4
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

    /**
     * 两数相加
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * <p>
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * <p>
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvw73v/
     * <p>
     * 思路：
     * 1、遍历 l1、l2，两者相加 >= 10, 进位
     * 2、若 l1 比 l2 长， 继续遍历l1； 若 l2 长， 继续遍历 l2
     * <p>
     * 思路2：（递归） 参考leetcode的1ms的答案
     * 1、l1的节点，l2的节点，进位标识 t；
     * 2、l1不为空 t += l1.val、 l1=l1.next, l2不为空 t += l2.val、 l2=l2.next;
     * 3、t%10作为新节点的值， t/10作为新节点的 t;
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode res = new ListNode();
        ListNode pre = res;
        // 进位标识
        int t = 0;
        while (l1 != null && l2 != null) {
            int val = l1.val + l2.val + t;

            t = 0;
            if (val >= 10) {
                t = 1;
                val -= 10;
            }
            ListNode next = new ListNode();
            next.val = val;
            pre.next = next;
            pre = pre.next;

            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            int val = l1.val + t;

            t = 0;
            if (val >= 10) {
                t = 1;
                val -= 10;
            }
            ListNode next = new ListNode();
            next.val = val;
            pre.next = next;
            pre = pre.next;

            l1 = l1.next;
        }
        while (l2 != null) {
            int val = l2.val + t;

            t = 0;
            if (val >= 10) {
                t = 1;
                val -= 10;
            }
            ListNode next = new ListNode();
            next.val = val;
            pre.next = next;
            pre = pre.next;

            l2 = l2.next;
        }
        if (t != 0) {
            ListNode next = new ListNode();
            next.val = t;
            pre.next = next;
            pre = pre.next;
        }
        return res.next;
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

    /**
     * 递增的三元子序列
     * 给你一个整数数组 nums ，判断这个数组中是否存在长度为 3 的递增子序列。
     * <p>
     * 如果存在这样的三元组下标 (i, j, k) 且满足 i < j < k ，使得 nums[i] < nums[j] < nums[k] ，返回 true ；否则，返回 false 。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-medium/xvvuqg/
     * <p>
     * 思路： 贪心
     * 1、遍历数组，记录两个值 m1,m2 (m1最小值， m2>m1)
     * 2、将每个值与 两个个值比较，记录到对应位置
     * <p>
     * （为什么不用管先后顺序？ ）
     * <p>
     * 2,1,5,0,4,6
     * 1、x=2,  m1 = 2, m2 =maxValue
     * 2、x=1,  m1 = 1, m2 =maxValue
     * 3、x=5,  m1 = 1, m2 = 5
     * 4、x=0,  m1 = 0, m2 = 5
     * 5、x=4,  m1 = 0, m2 = 4
     * 6、x=6,  m1 = 0, m2 = 4, x > m2 结束；
     * <p>
     * 假设没有4， 则会以 m1=0, m2=5, x=6 结束（没有满足i<j<K）；实际存在 1, 5, 6 这个组合满足条件；
     * 也就是说： 无论在哪个位置 x > m2, 都会存在一个 m1 它的位置在 m2 之前，因为先有 m1, 再有m2
     */
    public boolean increasingTriplet(int[] nums) {

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
     * <p>
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     * <p>
     * 思路：
     * 1、遍历 字符串s, 每次都都向两边判断
     * 2、判断最长奇数情况的回文串
     * 3、判断最长偶数情况的回文串
     */
    public String longestPalindrome(String s) {
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
     * <p>
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 示例 1:
     * <p>
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * <p>
     * 思路： 滑动窗口
     * 1、 创建一个队列和一个计数max，从左往右遍历字符串，不同则入队, 令max = max和队列长度中的最大值
     * 2、 相同，则从出队 直到队列中不包含相同元素为止
     * <p>
     * 思路2： 滑动窗口
     * 1、 维持两个指针和最大长度max，右指针一直向右，判断左指针是否需要收缩
     * 2、 需要 则收缩左指针
     * 3、 更新 最大长度max
     * <p>
     * 思路3： 动态规划
     * 确定状态，更新状态。
     * 用dp[i],表示以i结尾的字符串的最大长度。
     * 如果出现重复，从重复的位置重新计算长度。
     */

    public static int lengthOfLongestSubstring(String s) {
        Queue<Character> queue = new LinkedBlockingQueue<>();
        char[] cs = s.toCharArray();
        int max = 0;
        for (char c : cs) {
            if (!queue.contains(c)) {
                queue.offer(c);
                max = Math.max(max, queue.size());
            } else {
                while (!queue.isEmpty() && queue.contains(c)) {
                    queue.poll();
                }
                queue.offer(c);
            }
        }
        return max;
    }

    public static int lengthOfLongestSubstring1(String s) {
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
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] cs = str.toCharArray();
            Arrays.sort(cs);
            String sortStr = new String(cs);
//            String sortStr = Arrays.toString(cs);

            if (!map.containsKey(sortStr)) {
                map.put(sortStr, new ArrayList<>());
            }

            map.get(sortStr).add(str);
        }
        return new ArrayList<>(map.values());
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

}
