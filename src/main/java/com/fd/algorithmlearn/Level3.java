package com.fd.algorithmlearn;

import com.fd.algorithmlearn.linked.ListNode;

import java.util.*;
import java.util.stream.Collectors;

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

    // -------------------------- 动态规划 ------------------------------

    /**
     * 戳气球
     * 有 n 个气球，编号为0 到 n - 1，每个气球上都标有一个数字，这些数字存在数组 nums 中。
     * 现在要求你戳破所有的气球。戳破第 i 个气球，你可以获得 nums[i - 1] * nums[i] * nums[i + 1] 枚硬币。 
     * 这里的 i - 1 和 i + 1 代表和 i 相邻的两个气球的序号。如果 i - 1或 i + 1 超出了数组的边界，那么就当它是一个数字为 1 的气球。
     * 求所能获得硬币的最大数量。
     * <p>
     * 输入：nums = [3,1,5,8]
     * 输出：167
     * 解释：
     * nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
     * coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
     * <p>
     * 1 3,1,5,8 1
     * 0 1 2 3 4 5
     * <p>
     * [3,1,5,8] --> [1,5,8] --> [3,8] --> [8] --> []
     * 1*3*1   +    3*5*8   +  1*3*8  + 1*8*1 = 167
     */
    public int maxCoins(int[] nums) {
        int len = nums.length;
        int nLen = len + 2;

        int[] ns = new int[nLen];
        ns[0] = 1;
        ns[nLen - 1] = 1;
        System.arraycopy(nums, 0, ns, 1, len);

        int[][] dp = new int[nLen][nLen];
        for (int x = 2; x < nLen; x++) {
            for (int i = 0; i < nLen - x; i++) {
                rangeBest(i, i + x, dp, ns);
            }
        }
        return dp[0][nLen - 1];
    }

    public void rangeBest(int i, int j, int[][] dp, int[] ns) {
        int max = 0;
        for (int k = i + 1; k < j; k++) {
            int left = dp[i][k];
            int right = dp[k][j];
            int t = left + right + ns[i] * ns[k] * ns[j];
            max = Math.max(max, t);

//            System.err.print("i="+i+" ");
//            System.err.print("j="+j+" ");
//            System.err.print("k="+k+" ");
//            System.err.print("left="+left+" ");
//            System.err.print("right="+right+" ");
//            System.err.print("k="+(ns[i] * ns[k] * ns[j])+" ");
//            System.err.print("t="+t+" ");
//            System.err.print("max="+max+", ");
        }
//        System.err.println();
        dp[i][j] = max;
    }

    /**
     * 单词拆分 II
     * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，在字符串中增加空格来构建一个句子，使得句子中所有的单词都在词典中。返回所有这些可能的句子。
     * <p>
     * 说明：
     * 分隔时可以重复使用字典中的单词。
     * 你可以假设字典中没有重复的单词。
     * <p>
     * 思路：回溯 （+参考 单词拆分）
     * 1、在判断是否能 通过增加空格来构建句子 的同时，记录下路径 （回溯三件套）
     * a、路径：（记为sen） 记录下每次匹配的单词
     * b、选择列表：wordDict 字典
     * c、结束条件：s.len == 0, 即sen中已经包含s中的所有字符
     * 2、返回记录的结果集
     *
     * @Date: 2021/12/8
     */
    public List<String> wordBreakII(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        String sen = "";

        wordBreakIIHelp(s, sen, wordDict, res);

        return res;
    }

    private void wordBreakIIHelp(String s, String sen, List<String> wordDict, List<String> res) {
        if (s.length() == 0) {
            res.add(sen);
            return;
        }
        for (String word : wordDict) {
            if (s.startsWith(word)) {
                String temp = sen;
                sen = sen.length() > 0 ? sen + " " : sen;
                sen = sen + word;
                wordBreakIIHelp(s.substring(word.length()), sen, wordDict, res);
                sen = temp;
            }
        }
    }

    /**
     * 单词拆分
     * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典，判定 s 是否可以由空格拆分为一个或多个在字典中出现的单词。
     * <p>
     * 说明：拆分时可以重复使用字典中的单词。
     * <p>
     * 思路：动态规划 （类似 跳跃游戏）
     * 1、dp[i]: 字符串s.sub(0, i),是否能够由wordDict中的单词组成
     * 2、依据wordDict构建字典words。 遍历wordDict，记录每个单词的长度，记为lens，（使用set去除重复）
     * 3、遍历[0~len],
     * a、若dp[i]==true, 则遍历lens，每次截取w=s.sub(i, i+len), 若words.contains(w), 则令dp[i+k] = true;
     * b、若dp[i]==false, 则跳过
     * 4、返回dp[s.len]
     *
     * @Param: [s, wordDict]
     * @return: boolean
     * @Author: zxq
     * @Date: 2021/12/7
     */
    // me use set
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> words = new HashSet<>(wordDict);
        Set<Integer> lens = new HashSet<>();
        for (String word : wordDict) {
            lens.add(word.length());
        }

        int len = s.length();
        boolean[] dp = new boolean[len + 1];
        dp[0] = true;
        for (int i = 0; i <= len; i++) {
            if (dp[i]) {
                for (Integer k : lens) {
                    if (i + k > len) {
                        continue;
                    }
                    String w = s.substring(i, i + k);
                    if (words.contains(w)) {
                        dp[i + k] = true;
                    }
                }
            }
        }
        return dp[len];
    }

    // me
    public boolean wordBreakMy(String s, List<String> wordDict) {
        Set<Integer> lens = new HashSet<>();
        for (String word : wordDict) {
            lens.add(word.length());
        }

        int len = s.length();
        boolean[] dp = new boolean[len + 1];
        dp[0] = true;
        for (int i = 0; i <= len; i++) {
            if (dp[i]) {
                for (Integer k : lens) {
                    if (i + k > len) {
                        break;
                    }
                    String w = s.substring(i, i + k);
                    if (wordDict.contains(w)) {
                        dp[i + k] = true;
                    }
                }
            }
        }
        return dp[len];
    }

    // 超时
    public boolean wordBreak1(String s, List<String> wordDict) {
        List<Integer> b = new ArrayList<>();
        b.add(0);
        for (int i = 1; i <= s.length(); i++) {
            for (int k = b.size() - 1; k >= 0; k--) {
                String w = s.substring(b.get(k), i);
                if (wordDict.contains(w)) {
                    b.add(i);
                }
            }
        }
        return b.get(b.size() - 1) == s.length();
    }

    /**
     * 完全平方数
     * 给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
     * <p>
     * 给你一个整数 n ，返回和为 n 的完全平方数的 最少数量 。
     * <p>
     * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
     * <p>
     * 思路：动态规划  (参考零钱兑换)
     * 1、dp[i]: 和为i 的完全平方数的 最少数量
     * 2、遍历[1~n], 再遍历小于等于i的平方数，找到最小的dp[x] （min）： 转移方程：min = Math.min(min, dp[i - j * j])
     * 3、令dp[i] = min + 1, 最后返回dp[n]
     */
    public int numSquares(int n) {
        if (isPerfectSquare(n)) {
            return 1;
        }
        if (checkAnswer4(n)) {
            return 4;
        }
        for (int i = 1; i * i <= n; i++) {
            int j = n - i * i;
            if (isPerfectSquare(j)) {
                return 2;
            }
        }
        return 3;
    }

    // 判断是否为完全平方数
    public boolean isPerfectSquare(int x) {
        int y = (int) Math.sqrt(x);
        return y * y == x;
    }

    // 判断是否能表示为 4^k*(8m+7)
    public boolean checkAnswer4(int x) {
        while (x % 4 == 0) {
            x /= 4;
        }
        return x % 8 == 7;
    }

    public int numSquares1(int n) {
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE - 1;

            for (int j = 1; j * j <= i; j++) {
                min = Math.min(min, dp[i - j * j]);
            }

            dp[i] = min + 1;
        }
        return dp[n];
    }

    /**
     * 最佳买卖股票时机含冷冻期
     * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
     * <p>
     * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     * <p>
     * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     * <p>
     * 1,2,3,0,2
     * 1  0
     * 2  1
     * 3  2
     * 0  2
     * 2  3
     * <p>
     * 思路：动态规划
     * 1、由题，设定三种状态
     * a、dp[i][0]  第i天结束时，持有股票的最大收益
     * b、dp[i][1]  第i天结束时，无股票 且处于冷冻期 的最大收益
     * c、dp[i][2]  第i天结束时，无股票 且不处于冷冻期 的最大收益
     * 2、针对三种状态的转移方程
     * a、dp[i][0] =  max(dp[i-1][0], dp[i-1][2]-price[i]) （昨天就已经持有 or 前天卖，今天买 ）
     * b、dp[i][1] =  dp[i-1][0]+price[i] （今天卖）
     * c、dp[i][2] =  max(dp[i-1][1], dp[i-1][2]) （昨天卖，今无操作）
     * <p>
     * 空间优化：
     */
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len == 0) {
            return 0;
        }

        int buy = -prices[0];
        int frozen = 0;
        int noFrozen = 0;
        for (int i = 1; i < len; i++) {
            buy = Math.max(buy, noFrozen - prices[i]);
            noFrozen = Math.max(frozen, noFrozen);
            frozen = buy + prices[i];
        }

        return Math.max(frozen, noFrozen);
    }

    public int maxProfit1(int[] prices) {
        int len = prices.length;
        if (len == 0) {
            return 0;
        }
        int[][] dp = new int[len][3];
        dp[0][0] = -prices[0];
        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2] - prices[i]);
            dp[i][1] = dp[i - 1][0] + prices[i];
            dp[i][2] = Math.max(dp[i - 1][1], dp[i - 1][2]);
        }

        return Math.max(dp[len - 1][1], dp[len - 1][2]);
    }

    /**
     * 乘积最大子数组
     * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
     * <p>
     * <p>
     * 思路：动态规划
     * 1、定义max[i]:以 nums[i] 为结尾的最大乘积
     * 2、定义min[i]:以 nums[i] 为结尾的最小乘积
     * 3、max[i] = Math.max(nums[i], Math.max(nums[i] * max[i - 1], nums[i] * min[i - 1]));
     * 4、min[i] = Math.min(nums[i], Math.min(nums[i] * max[i - 1], nums[i] * min[i - 1]));
     * 5、res = Math.max(res, max[i])
     * <p>
     * 优化：状态压缩
     */
    public int maxProduct(int[] nums) {
        int len = nums.length;
        int max = nums[0];
        int min = nums[0];
        int res = nums[0];
        for (int i = 1; i < len; i++) {
            if (nums[i] < 0) {
                int tmp = max;
                max = min;
                min = tmp;
            }
            max = Math.max(nums[i], nums[i] * max);
            min = Math.min(nums[i], nums[i] * min);
            res = Math.max(res, max);
        }
        return res;
    }

    public int maxProduct1(int[] nums) {
        int len = nums.length;
        int[] max = new int[len];
        int[] min = new int[len];
        max[0] = nums[0];
        min[0] = nums[0];
        int res = max[0];
        for (int i = 1; i < len; i++) {
            max[i] = Math.max(nums[i], Math.max(nums[i] * max[i - 1], nums[i] * min[i - 1]));
            min[i] = Math.min(nums[i], Math.min(nums[i] * max[i - 1], nums[i] * min[i - 1]));
            res = Math.max(res, max[i]);
        }
        return res;
    }

    // -------------------------- 动态规划 ------------------------------ end
    // -------------------------- 排序和搜索 ------------------------------

    /**
     * 寻找两个正序数组的中位数
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
     * <p>
     * 算法的时间复杂度应该为 O(log (m+n)) 。
     * <p>
     * 思路：
     * 1、令len = nums1.len+nums2.len, i指向nums1的下标，j指向nums2的下标
     * 2、2、遍历 [0~len/2], 令q等于当前值, p等于q的前一个值。若 i未越界 且 (j越界 或 nums1[i]<=nums2[j]), 则 i++, 反之j++
     * 3、若len为偶数，返回 (p+q)/2, 否则返回 q
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int len = m + n;
        int p = -1, q = -1;
        int i = 0, j = 0;
        for (int k = 0; k <= len / 2; k++) {
            p = q;
            if (i < m && (j >= n || nums1[i] <= nums2[j])) {
                q = nums1[i++];
            } else {
                q = nums2[j++];
            }
        }
        if ((len & 1) == 0) {
            return (p + q) * 1.0 / 2;
        } else {
            return q;
        }
    }

    /**
     * 有序矩阵中第K小的元素
     * 给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
     * 请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。
     * <p>
     * 思路：归并排序
     * 1、维护一个（矩阵行相关的）最小堆 pq
     * 2、初始化最小堆, 将每行的第一个元素和其位置放入pq (m[i][j], i, 0)
     * 3、遍历k-1遍, 每次pq.poll()取出最小元素, 若第i行没到行位，则将（m[i][j], i, j+1）放入堆中
     * eg: 若 m[0][1]>m[1][0]，则下一个最小元素应该是m[0][1]，所以需要将（m[i][j], i, j+1）放入最小堆中
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        for (int i = 0; i < n; i++) {
            pq.offer(new int[]{matrix[i][0], i, 0});
        }
        for (int i = 0; i < k - 1; i++) {
            int[] min = pq.poll();
            if (min[2] != n - 1) {
                pq.offer(new int[]{matrix[min[1]][min[2] + 1], min[1], min[2] + 1});
            }
        }
        return pq.poll()[0];
    }

    /**
     * 摆动排序 II
     * 给你一个整数数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
     * <p>
     * 你可以假设所有输入数组都可以得到满足题目要求的结果。
     * <p>
     * 输入：nums = [1,5,1,1,6,4]
     * 输出：[1,6,1,5,1,4]
     * 解释：[1,4,1,5,1,6] 同样是符合题目要求的结果，可以被判题程序接受。
     * <p>
     * 思路1：
     * 1、排序nums
     * 2、令pre = nums的前一半, post = nums的后一半， 且 pre.len>=post.len
     * 3、翻转pre和post
     * 4、交替将pre和post放入nums
     * <p>
     * 思路2：桶排序
     * 1、因为0 <= nums[i] <= 5000, 所以定义一个 bucket = new int[5001]
     * 2、若nums.len为奇数，则最后一个值为小，反之最后一个值为大 （例如：小大小大， 小大小大小）
     * 3、遍历nums 先放大值，再放小值 （先大后小是为了保证中位数隔开）
     *
     * @Date: 2021/11/26
     */
    public void wiggleSort(int[] nums) {
        // 0 <= nums[i] <= 5000
        int[] bucket = new int[5001];
        for (int num : nums) {
            bucket[num]++;
        }
        int len = nums.length;
        int big = (len & 1) == 1 ? len - 2 : len - 1;
        int small = (len & 1) == 1 ? len - 1 : len - 2;
        // 奇数，[小 大 小 大 小] 最后一个数为小数
        // 偶数，[小 大 小 大] 最后一个数为大数


        int index = 5000;
        for (int i = 1; i <= big; i += 2) { // 不变的是，大数插在偶数位
            while (bucket[index] == 0) index--;
            nums[i] = index;
            bucket[index]--;
        }
        for (int i = 0; i <= small; i += 2) {  // 不变的是，小数插在奇数位
            while (bucket[index] == 0) index--;
            nums[i] = index;
            bucket[index]--;
        }
    }

    public void wiggleSort2(int[] nums) {
        int len = nums.length;
        int[] copy = new int[len];
        System.arraycopy(nums, 0, copy, 0, len);
        Arrays.sort(copy);

        int N = len;
        //比如123456
        for (int i = 1; i < len; i += 2) {
            nums[i] = copy[--N]; //遍历完成后 x 6 x 5 x 4
        }
        for (int i = 0; i < len; i += 2) {
            nums[i] = copy[--N]; //遍历完成后 3 6 2 5 1 4
        }
    }

    public void wiggleSort1(int[] nums) {
        int len = nums.length;
        int mid = len / 2;
        if (len % 2 != 0) {
            ++mid;
        }

        Arrays.sort(nums);

        int[] pre = new int[mid];
        int[] post = new int[len - mid];
        System.arraycopy(nums, 0, pre, 0, mid);
        System.arraycopy(nums, mid, post, 0, len - mid);
        int k = 0;
        for (int i = pre.length - 1; i >= 0; i--) {
            nums[(k++) * 2] = pre[i];
        }
        k = 0;
        for (int i = post.length - 1; i >= 0; i--) {
            nums[(k++) * 2 + 1] = post[i];
        }
    }


    // -------------------------- 排序和搜索 ------------------------------ end

    // -------------------------- 回溯算法 ------------------------------

    /**
     * 正则表达式匹配
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     * <p>
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
     * <p>
     * 输入：s = "aab" p = "c*a*b"
     * 输出：true
     * <p>
     * 思路：
     * dp[][] = new int[4][6]
     * c * a * b
     * a
     * a
     * b
     * <p>
     * <p>
     * dp[1][0] = false
     * dp[0][1] = false
     * dp[0][2] = false
     */
    public boolean isMatch2(String s, String p) {
        int m = s.length();
        int n = p.length();

        boolean[][] f = new boolean[m + 1][n + 1];
        f[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    f[i][j] = f[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        f[i][j] = f[i][j] || f[i - 1][j];
                    }
                } else {
                    if (matches(s, p, i, j)) {
                        f[i][j] = f[i - 1][j - 1];
                    }
                }
            }
        }
        print(f);
        return f[m][n];
    }

    void print(boolean[][] f) {
        for (boolean[] booleans : f) {
            for (boolean aBoolean : booleans) {
                System.err.print(aBoolean ? "✔ " : "✘ ");
            }
            System.err.println();
        }
    }

    private boolean matches(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }

    /**
     * 通配符匹配
     * 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
     * <p>
     * '?' 可以匹配任何单个字符。
     * '*' 可以匹配任意字符串（包括空字符串）。
     * 两个字符串完全匹配才算匹配成功。
     * <p>
     * 思路1：动态规划
     * <p>
     * 思路2：贪心
     * 1、模式p可以看做： `*u1*u2*...*ux*` 的形式
     * 例如：p=*abcd*efgh*i*, p可以匹配所有一次出现子串abcd、efgh、i的字符串
     * 此时可以先暴力找到最早出现的abcd，然后从下一个位置开始暴力找到最早出现的efgh
     * 最后找出i，就可以判断s是否可以与p匹配。
     * 这样【贪心地】找到最早出现的子串是比较直管段，因为如果s中多次出现了某个子串，
     * 那么我们选择最早出现的位置可以使得后续子串能被找到的机会更大。
     * 在1的前提下，算法的本质是：如果字符串s中首先找到u1，在找到u2，u3...ux,那么s就可以与模式p匹配
     * 2、若 模式p的结尾字符不是星号
     * 则先不断的匹配s和p的结尾字符，直到p为空或p的结尾字符是星号为止。这个过程中，如果匹配失败，
     * 或者最后p为空但是s不为空，则需要返回false。
     * 例如：s= abcdef p= a*f 则先把f匹配掉，剩下s=abcde p=a*
     * 3、若 模式p的开头字符不是星号
     * 则可以与结尾不是星号的处理类似。也可以将sRecord和tsRecord初始置为-1,标识p的开头不是星号
     * 并在匹配失败时进行判断，如果它们仍未-1，说明没有【反悔】重新匹配的机会。
     *
     * @Description:
     * @Param: [s, p]
     * @return: boolean
     * @Author: zxq
     * @Date: 2021/11/22
     */
    public boolean isMatch(String s, String p) {
        int sRight = s.length(), pRight = p.length();
        while (sRight > 0 && pRight > 0 && p.charAt(pRight - 1) != '*') {
            if (charMatch(s.charAt(sRight - 1), p.charAt(pRight - 1))) {
                --sRight;
                --pRight;
            } else {
                return false;
            }
        }

        if (pRight == 0) {
            return sRight == 0;
        }
        int sIndex = 0, pIndex = 0;
        int sRecord = -1, pRecord = -1;

        while (sIndex < sRight && pIndex < pRight) {
            if (p.charAt(pIndex) == '*') {
                ++pIndex;
                sRecord = sIndex;
                pRecord = pIndex;
            } else if (charMatch(s.charAt(sIndex), p.charAt(pIndex))) {
                ++sIndex;
                ++pIndex;
            } else if (sRecord != -1 && sRecord + 1 < sRight) {
                ++sRecord;
                sIndex = sRecord;
                pIndex = pRecord;
            } else {
                return false;
            }
        }

        return allStars(p, pIndex, pRight);
    }

    private boolean allStars(String str, int left, int right) {
        for (int i = left; i < right; i++) {
            if (str.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }

    private boolean charMatch(char u, char v) {
        return u == v || v == '?';
    }

    public boolean isMatch1(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; i++) {
            if (p.charAt(i - 1) == '*') {
                dp[0][i] = true;
            } else {
                break;
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                } else if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }


    /**
     * 删除无效的括号
     * 给你一个由若干括号和字母组成的字符串 s ，删除最小数量的无效括号，使得输入的字符串有效。
     * <p>
     * 返回所有可能的结果。答案可以按 任意顺序 返回。
     *
     * @Description:
     * @Author: zxq
     * @Date: 2021/11/20
     */
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
        int lr = 0;
        int rr = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                lr++;
            } else if (s.charAt(i) == ')') {
                if (lr == 0) {
                    rr++;
                } else {
                    lr--;
                }
            }
        }
        helper(s, 0, lr, rr, res);
        return res;
    }

    private void helper(String str, int start, int lr, int rr, List<String> res) {
        if (lr == 0 && rr == 0) {
            if (isValidP(str)) {
                res.add(str);
            }
            return;
        }
        for (int i = start; i < str.length(); i++) {
            if (i != start && str.charAt(i) == str.charAt(i - 1)) {
                continue;
            }
            if (lr + rr > str.length() - i) {
                return;
            }
            String nStr = str.substring(0, i) + str.substring(i + 1);
            if (lr > 0 && str.charAt(i) == '(') {
                helper(nStr, i, lr - 1, rr, res);
            }
            if (rr > 0 && str.charAt(i) == ')') {
                helper(nStr, i, lr, rr - 1, res);
            }
        }

    }

    private boolean isValidP(String str) {
        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                cnt++;
            } else if (str.charAt(i) == ')') {
                cnt--;
                if (cnt < 0) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 单词搜索 II
     * 给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words，找出所有同时在二维网格和字典中出现的单词。
     * <p>
     * 单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。
     * <p>
     * 输入：board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
     * 输出：["eat","oath"]
     * <p>
     * 输入：board = [["a","b"],["c","d"]], words = ["abcb"]
     * 输出：[]
     * <p>
     * <p>
     * 思路1：借用 "单词搜索"（中级算法题集中的）
     * 1、循环遍历words, 借助exist函数，若存在则将word加入结果集res
     * <p>
     * 思路1：空间优化
     * 1、去掉valid, 使用一个临时char变量即可
     */
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            if (exist(board, words[i])) {
                res.add(words[i]);
            }
        }
        return res;
    }

    public boolean exist(char[][] board, String word) {
        char[] ws = word.toCharArray();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (existBacktrack(board, i, j, ws, 0)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean existBacktrack(char[][] board, int i, int j, char[] ws, int index) {
        // 单词完全被匹配
        if (index == ws.length) {
            return true;
        }
        // 不能超过边界
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return false;
        }
        // 无法继续匹配
        if (ws[index] != board[i][j]) {
            return false;
        }

        char temp = board[i][j];
        board[i][j] = 0;
        boolean a = existBacktrack(board, i + 1, j, ws, index + 1);
        boolean b = existBacktrack(board, i - 1, j, ws, index + 1);
        boolean c = existBacktrack(board, i, j + 1, ws, index + 1);
        boolean d = existBacktrack(board, i, j - 1, ws, index + 1);
        board[i][j] = temp;

        return a || b || c || d;
    }


    /**
     * 分割回文串
     * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
     * <p>
     * 回文串 是正着读和反着读都一样的字符串。
     * <p>
     * [回溯算法、优化（使用动态规划预处理数组） - 分割回文串 - 力扣（LeetCode）](https://leetcode-cn.com/problems/palindrome-partitioning/solution/hui-su-you-hua-jia-liao-dong-tai-gui-hua-by-liweiw/)
     * 思路：回溯
     * 1、路径：从根节点到叶子节点的路径
     * 2、选择列表：逐位截取 剩下的前缀
     * 3、结束条件：空字符串 or 截取的字符串不是回文串
     */
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        List<String> stack = new ArrayList<>();
        char[] cs = s.toCharArray();

        dfsPartition(cs, 0, cs.length, stack, res);
        return res;
    }

    private void dfsPartition(char[] cs, int index, int len, List<String> stack, List<List<String>> res) {
        if (index == len) {
            res.add(new ArrayList<>(stack));
            return;
        }

        for (int i = index; i < len; i++) {
            if (!isPalindrome(cs, index, i)) {
                continue;
            }
            stack.add(new String(cs, index, i - index + 1));
            dfsPartition(cs, i + 1, len, stack, res);
            stack.remove(stack.size() - 1);
        }
    }

    public boolean isPalindrome(char[] cs, int start, int end) {
        while (start < end) {
            if (cs[start] != cs[end]) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public List<List<String>> partition1(String s) {
        List<List<String>> res = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (int i = 1; i <= s.length(); i++) {
            String pre = s.substring(0, i);
            String remain = s.substring(i);
            dfsPartition1(pre, remain, stack, res);
        }
        return res;
    }

    public void dfsPartition1(String pre, String remain, Stack<String> stack, List<List<String>> res) {
        if (!isPalindrome(pre)) {
            return;
        }

        if (remain.length() == 0) {
            stack.add(pre);
            res.add(new ArrayList<>(stack));
            stack.pop();
        }

        for (int i = 1; i <= remain.length(); i++) {
            stack.push(pre);
            String a = remain.substring(0, i);
            String b = remain.substring(i);
            dfsPartition1(a, b, stack, res);
            stack.pop();
        }
    }

    public static boolean isPalindrome(String s) {
        char[] cs = s.toLowerCase().toCharArray();
        int i = 0;
        int j = cs.length - 1;

        while (i < j) {
            if (cs[i] != cs[j]) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    // -------------------------- 回溯算法 ------------------------------ end


    // -------------------------- 树和图 ------------------------------


    /**
     * 计算右侧小于当前元素的个数
     * 给你一个整数数组 nums ，按要求返回一个新数组 counts 。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
     * <p>
     *
     *
     * <p>
     * 输入：nums = [5,2,6,1]
     * 输出：[2,1,1,0]
     * <p>
     * [2,0,1]
     * [2,0,0]
     * <p>
     * <p>
     * [26,78,27,100,33,67,90,23,66,5,38,7,35,23,52,22,83,51,98,69,81,32,78,28,94,13,2,97,3,76,99,51,9,21,84,66,65,36,100,41]
     * <p>
     * 思路1：二分查找+有序集合
     * 1、从右往左遍历，
     * - a、二分查找nums[i]在有序列表sortList中的排名index
     * - b、index 就是 nums[i] 右侧小于当前元素的个数, 计入到res中
     * - c、将nums[i]插入到sortList的index处
     * 2、返回翻转后的res
     */
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        List<Integer> sortList = new ArrayList<>();

        for (int i = nums.length - 1; i >= 0; i--) {
            int index = bFind(sortList, nums[i]);
            res.add(index);
            sortList.add(index, nums[i]);
        }
        Collections.reverse(res);
        return res;
    }

    public int bFind(List<Integer> nums, int target) {
        if (nums.size() == 0) {
            return 0;
        }
        int left = 0, right = nums.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums.get(mid) < target) {
                left = mid + 1;
            } else if (nums.get(mid) > target) {
                right = mid - 1;
            } else if (nums.get(mid) == target) {
                // 别返回，锁定左侧边界
                right = mid - 1;
            }
        }
        return left;
    }

    // 暴力超时
    public List<Integer> countSmaller1(int[] nums) {

        int[] counts = new int[nums.length];

        for (int i = nums.length - 1; i >= 0; i--) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    counts[i]++;
                }
            }
        }

        return Arrays.stream(counts).boxed().collect(Collectors.toList());
    }


    /**
     * 矩阵中的最长递增路径
     * 给定一个 m x n 整数矩阵 matrix ，找出其中 最长递增路径 的长度。
     * 对于每个单元格，你可以往上，下，左，右四个方向移动。 你 不能 在 对角线 方向上移动或移动到 边界外（即不允许环绕）。
     * <p>
     * 思路：dfs
     * 1、遍历矩阵, 对每个节点进行dfs
     * - a、visited[][]防止重复访问, pathMax[][] 记录当前节点的最长递增路径
     * - b、向上下左右四个方向深搜
     * - c、越界 or 不是递增 or 已经记录了最长递增路径的值, 则返回
     * <p>
     * 空间优化：去掉visited[][], pathMax[][]==0可以达到同样的效果
     * <p>
     * <p>
     * [[7,7,5],
     * [2,4,6],
     * [8,2,0]]
     */
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] pathMax = new int[m][n];
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pathMax[i][j] == 0) {
                    int longest = dfsLongest(matrix, -1, i, j, pathMax);
                    max = Math.max(max, longest);
                }
            }
        }
        return max;
    }

    private int dfsLongest(int[][] matrix, int val, int i, int j, int[][] pathMax) {
        int m = matrix.length;
        int n = matrix[0].length;

        if (i < 0 || i >= m || j < 0 || j >= n) {
            return 0;
        }
        if (val >= matrix[i][j]) {
            return 0;
        }
        if (pathMax[i][j] > 0) {
            return pathMax[i][j];
        }

        int[][] d = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int maxP = 0;
        for (int[] next : d) {
            int longest = dfsLongest(matrix, matrix[i][j], i + next[0], j + next[1], pathMax) + 1;
            maxP = Math.max(maxP, longest);
        }
        pathMax[i][j] = maxP;

        return maxP;
    }

    public int longestIncreasingPath1(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[][] visited = new boolean[m][n];
        int[][] pathMax = new int[m][n];
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int longest = dfsLongest1(matrix, -1, i, j, visited, pathMax);
                max = Math.max(max, longest);
            }
        }
        return max;
    }

    private int dfsLongest1(int[][] matrix, int val, int i, int j, boolean[][] visited, int[][] pathMax) {
        int m = matrix.length;
        int n = matrix[0].length;

        if (i < 0 || i >= m || j < 0 || j >= n) {
            return 0;
        }
        if (visited[i][j] || val >= matrix[i][j]) {
            return 0;
        }
        if (pathMax[i][j] > 0) {
            return pathMax[i][j];
        }

        int[][] d = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        visited[i][j] = true;
        int maxP = 0;
        for (int[] next : d) {
            int longest = dfsLongest1(matrix, matrix[i][j], i + next[0], j + next[1], visited, pathMax) + 1;
            maxP = Math.max(maxP, longest);
        }
        pathMax[i][j] = maxP;
        visited[i][j] = false;

        return maxP;
    }

    /**
     * 课程表 II
     * 现在你总共有 numCourses 门课需要选，记为 0 到 numCourses - 1。给你一个数组 prerequisites ，
     * 其中 prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修 bi 。
     * <p>
     * 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示：[0,1] 。
     * 返回你为了学完所有课程所安排的学习顺序。可能会有多个正确的顺序，你只要返回 任意一种 就可以了。如果不可能完成所有课程，返回 一个空数组 。
     * <p>
     * 思路：拓扑排序
     * 1、依据 numCourses 和 prerequisites[][], 构建一个图
     * 2、使用dfs遍历图, 借助visited[]和onPath[]判断是否有环
     * - a、onPath[]： 记录一次 dfs 递归经过的节点
     * - b、visited[]： 记录遍历过的节点，防止走回头路
     * 3、若有环则直接返回一个空数组
     * 4、借助 postOrder 记录后序遍历的结果, 返回 翻转后的postOrder (即为拓扑排序的结果)
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);

        boolean[] visited = new boolean[numCourses];
        // onPath[0] 存检测结果
        boolean[] onPath = new boolean[numCourses + 1];
        List<Integer> postOrder = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            traverse(graph, i, visited, onPath, postOrder);
        }

        if (onPath[0]) {
            return new int[]{};
        }

        Collections.reverse(postOrder);
        // 类型转换 List 转 Array
        return postOrder.stream().mapToInt(Integer::valueOf).toArray();
    }

    private void traverse(List<Integer>[] graph, int s, boolean[] visited, boolean[] onPath, List<Integer> postOrder) {
        if (onPath[s + 1]) {
            onPath[0] = true;
        }
        if (visited[s] || onPath[0]) {
            return;
        }
        visited[s] = true;

        onPath[s + 1] = true;
        for (int i : graph[s]) {
            traverse(graph, i, visited, onPath, postOrder);
        }
        onPath[s + 1] = false;

        // 后序遍历的位置
        postOrder.add(s);
    }

    /**
     * 课程表
     * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
     * <p>
     * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，
     * 其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
     * <p>
     * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
     * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
     * <p>
     * 思路：有向图的环检测
     * 1、依据 numCourses 和 prerequisites[][], 构建一个图
     * 2、使用dfs遍历图, 借助visited[]和onPath[]判断是否有环
     * - a、onPath[]： 记录一次 dfs 递归经过的节点
     * - b、visited[]： 记录遍历过的节点，防止走回头路
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);

        boolean[] visited = new boolean[numCourses];
        // onPath[0] 存检测结果
        boolean[] onPath = new boolean[numCourses + 1];

        for (int i = 0; i < numCourses; i++) {
            dfsCanFinish(graph, i, visited, onPath);
        }

        return !onPath[0];
    }

    private void dfsCanFinish(List<Integer>[] graph, int s, boolean[] visited, boolean[] onPath) {
        if (onPath[s + 1]) {
            onPath[0] = true;
        }
        if (visited[s] || onPath[0]) {
            return;
        }
        visited[s] = true;

        onPath[s + 1] = true;
        for (int i : graph[s]) {
            dfsCanFinish(graph, i, visited, onPath);
        }
        onPath[s + 1] = false;
    }

    public List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
        LinkedList<Integer>[] graph = new LinkedList[numCourses];

        for (int i = 0; i < numCourses; i++) {
            graph[i] = new LinkedList<>();
        }

        for (int[] edge : prerequisites) {
            int from = edge[1];
            int to = edge[0];
            graph[from].add(to);
        }

        return graph;
    }


    /**
     * 朋友圈
     * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
     * <p>
     * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
     * <p>
     * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
     * <p>
     * 返回矩阵中 省份 的数量。
     * <p>
     * 思路1： 并查集
     * 1、实现并查集的三个主要api
     * - a、void union(p,q)连接p,q
     * - b、bool connected(p,q)判断p,q是否连通
     * - c、int count() 图中连通分量的个数
     * 2、若 isConnected[i][j]=1, 就连接i、j, 即union(i, j)
     * 3、返回 count()
     * <p>
     * <p>
     * [[1,0,0,1],
     * [0,1,1,0],
     * [0,1,1,1],
     * [1,0,1,1]]
     * a d
     * b c
     * c d
     * a, b, c, d
     * 思路2：dfs
     * 1、路径：当前省份包含的城市 (当前行i)
     * 2、选择列表：没有被归入过省份的城市 (下一行j)
     * 3、结束：遍历了所有城市 (行尾)
     */
    public int findCircleNum(int[][] isConnected) {

        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int cnt = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfsFindCircleNum(isConnected, i, visited);
                cnt++;
            }
        }

        return cnt;
    }

    private void dfsFindCircleNum(int[][] isConnected, int row, boolean[] visited) {
        int m = isConnected[0].length;
        for (int j = 0; j < m; j++) {
            if (!visited[j]) {
                if (isConnected[row][j] == 1) {
                    visited[j] = true;
                    dfsFindCircleNum(isConnected, j, visited);
                }
            }
        }
    }

    public int findCircleNum1(int[][] isConnected) {
        int m = isConnected.length;
        int n = isConnected[0].length;
        UF uf = new UF(m);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }

        return uf.count();
    }

    static class UF {
        private int cnt;
        private int[] parent;
        private int[] size;

        public UF(int n) {
            cnt = n;
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int x) {
            // 路径压缩
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) {
                return;
            }
            if (size[rootP] > size[rootQ]) {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            } else {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            }
            cnt--;
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public int count() {
            return cnt;
        }
    }


    /**
     * 二叉树中的最大路径和
     * <p>
     * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。
     * 该路径 至少包含一个 节点，且不一定经过根节点。
     * 路径和 是路径中各节点值的总和。
     * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
     * <p>
     * 思路：二叉樹的后序遍历
     * 0、base case 若root==null, 返回 `Int.MinValue/10` (除以10是为了防止溢出)
     * 1、递归查询左子树的最大路径和
     * 2、递归查询右子树的最大路径和
     * 3、进行如下两步
     * - a、比较 left+root, left+root, root, 返回三者中的最大值max
     * - b、令 maxVal = (maxVal, left, right, left+right+root)四者中的最大值
     * 4、返回 Math.max(max, maxVal)
     * <p>
     * 思考过程：
     * <p>
     * -----root
     * left     right
     * <p>
     * 随便找一个 (root, left, right) 三角形进行分析,
     * 1、每个节点只能走一次，所以三角形作为 子树 能向上 提供的最大值是(left+root, left+root, root)三者之一
     * 2、可以从树中任意节点出发，所以最大值还有可能在（left, right, left+root+right）这三者中出现
     * <p>
     * <p>
     * [-1,-2,10,-6,null,-3,-6]
     * -1
     * -2      10
     * -6 null      -3 -6
     * <p>
     * [-2,-1]
     * -2
     * -1
     */
    public int maxPathSum(TreeNode root) {
        TreeNode maxN = new TreeNode(Integer.MIN_VALUE);
        int max = maxPathSumHelp(root, maxN);

        return Math.max(max, maxN.val);
    }

    private int maxPathSumHelp(TreeNode root, TreeNode maxN) {
        if (root == null) {
            return Integer.MIN_VALUE / 10;
        }

        int left = maxPathSumHelp(root.left, maxN);
        int right = maxPathSumHelp(root.right, maxN);
        int val = root.val;

        int max = Math.max(val, left + val);
        max = Math.max(max, right + val);

        maxN.val = Math.max(maxN.val, left + val + right);
        maxN.val = Math.max(maxN.val, right);
        maxN.val = Math.max(maxN.val, left);

        return max;
    }

    /**
     * 二叉树的最近公共祖先
     * <p>
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，
     * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     * <p>
     * 思路：二叉树的后序遍历
     * 0、base case, 若root == null, 返回null; 若 root==p || root==q, 返回root
     * 1、递归的查询 left= (root.left, p, q)
     * 2、递归的查询 right= (root.right, p ,q)
     * 3、处理left和right, 分为三种情况
     * - a、left!=null && right!=null, 返回root (依据base case, 此时right和left分别是p、q其中的一个)
     * - b、left==null && right==null, 返回null
     * - c、若left==null, 则返回right； 若right==null, 则返回left (说明最近公共祖先 在子树上已经找到过了)
     * <p>
     * ps: 为何对于步骤a 能保证root是最近的公共祖先，因为是后序遍历相当于从下往上找
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        }
        if (left == null && right == null) {
            return null;
        }
        return left == null ? right : left;
    }


    /**
     * 被围绕的区域
     * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
     * <p>
     * 思路1：dfs
     * 1、遍历矩阵的四条边，若是O,则进行dfs将相连的O都变成#
     * - a、路径： 已经选择过的O
     * - b、选择列表： 上下左右
     * - c、结束条件： 出界or不是O
     * 2、遍历矩阵, 将剩下的O变为X
     * 3、遍历矩阵, 将#变回O
     */
    private void print(char[][] board) {
        System.err.println("------------------------------");
        for (char[] chars : board) {
            System.err.println(Arrays.toString(chars));
        }
    }

    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;

        for (int i = 0; i < n; i++) {
            solveBackTrack(0, i, board);
            solveBackTrack(m - 1, i, board);
        }

        for (int i = 0; i < m; i++) {
            solveBackTrack(i, 0, board);
            solveBackTrack(i, n - 1, board);
        }

        // print(board);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == '#') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void solveBackTrack(int i, int j, char[][] board) {
        int m = board.length;
        int n = board[0].length;

        if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] != 'O') {
            return;
        }

        board[i][j] = '#';

        int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] ints : d) {
            solveBackTrack(i + ints[0], j + ints[1], board);
        }
    }


    /**
     * 单词接龙
     * 字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列：
     * 序列中第一个单词是 beginWord 。
     * 序列中最后一个单词是 endWord 。
     * 每次转换只能改变一个字母。
     * 转换过程中的中间单词必须是字典 wordList 中的单词。
     * 给你两个单词 beginWord 和 endWord 和一个字典 wordList ，找到从 beginWord 到 endWord 的 最短转换序列 中的 单词数目 。如果不存在这样的转换序列，返回 0。
     * <p>
     * 思路：bfs模板
     * 1、创建 队列 q, Set visited, q用于记录每一层的节点，visited用于避免走回头路
     * 2、将起点加入q, visited, 记录step=1
     * 3、从队列里遍历当前层的节点 记为cur，依据当前层的节点进行如下操作
     * - a、若cur == endWord, 则返回step
     * - b、将cur 相邻的 且 没有访问过的 节点加入队列 （相邻：两个字符串只有一个字母不同）
     * - c、遍历完当前层节点后, step++, 若队列不为空, 则遍历下一层
     * 4、没有找到，则返回0
     * <p>
     * 思路2：双向bfs(加速权重 1%) ,优化2 改变diffOne逻辑 (加速权重 90%), 优化3 使用较小的集合进行扩散（加速权重 9%）
     * 0、endWord 不在 wordList 中, 则直接返回 0
     * 1、创建 起点Set start, 终点Set end, 字典Set dic
     * 2、将第一个单词装入 start， 最后一个单词装入end， 初始化 step=1
     * 3、遍历当前层的节点 记为cur
     * - a、若 end.contains(cur), 则返回step
     * - b、（diffOne） 将cur拆成char[], 并对每位字符轮换a~z（轮换一遍之后需要复原）, 构建新的 newCur,
     * 在dic中寻找newCur, 若存在则判断end.contains(newCur), 是则返回 step+1, 反之加入下一层加点
     * - c、step++。  从dic中去除掉start(已经访问过), 令 start等于节点较少的集合
     * 4、没有找到，则返回0
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) {
            return 0;
        }

        Set<String> start = new HashSet<>();
        Set<String> end = new HashSet<>();
        Set<String> dic = new HashSet<>(wordList);

        start.add(beginWord);
        end.add(endWord);
        int step = 1;

        while (!start.isEmpty() && !end.isEmpty()) {
            Set<String> tmp = new HashSet<>();

            for (String cur : start) {
                if (end.contains(cur)) {
                    return step;
                }

                char[] cs = cur.toCharArray();
                // 优化2
                for (int i = 0; i < cs.length; i++) {
                    char temp = cs[i];
                    // 变化
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == temp) {
                            continue;
                        }
                        cs[i] = c;
                        String nCur = new String(cs);
                        if (dic.contains(nCur)) {
                            if (end.contains(nCur)) {
                                return step + 1;
                            } else {
                                tmp.add(nCur);
                            }
                        }
                    }
                    // 复原
                    cs[i] = temp;
                }
            }
            step++;
            dic.removeAll(start);
            start = end;
            end = tmp;

            // 优化3
            if (start.size() > end.size()) {
                tmp = start;
                start = end;
                end = tmp;
            }
        }
        return 0;
    }

    public int ladderLength1(String beginWord, String endWord, List<String> wordList) {
        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        q.offer(beginWord);
        visited.add(beginWord);
        int step = 1;

        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                String cur = q.poll();
                if (endWord.equals(cur)) {
                    return step;
                }
                for (String x : wordList) {
                    if (!visited.contains(x) && diffOne(x, cur)) {
                        q.offer(x);
                        visited.add(x);
                    }
                }
            }
            step++;
        }
        return 0;
    }

    private boolean diffOne(String x, String cur) {
        if (x == null || cur == null) {
            return false;
        }
        char[] xc = x.toCharArray();
        char[] cc = cur.toCharArray();

        int cnt = 0;
        for (int i = 0; i < cc.length; i++) {
            if (xc[i] != cc[i]) {
                cnt++;
            }
            if (cnt > 1) {
                return false;
            }
        }
        return cnt == 1;
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    // -------------------------- 树和图 ------------------------------

    /**
     * 复制带随机指针的链表
     * <p>
     * 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。
     * 构造这个链表的 深拷贝。 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。复制链表中的指针都不应指向原链表中的节点 。
     * 例如，如果原链表中有 X 和 Y 两个节点，其中 X.random --> Y 。那么在复制链表中对应的两个节点 x 和 y ，同样有 x.random --> y 。
     * 返回复制链表的头节点。
     * 用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：
     * val：一个表示 Node.val 的整数。
     * random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
     * 你的代码 只 接受原链表的头节点 head 作为传入参数。
     * <p>
     * 思路：
     * 1、设旧节点为 oldN, 新节点为newN
     * 2、第一遍遍历旧节点，依据旧节点构建新节点， 并构建 map1(newN, oldN.random) map2(oldN, newN)的映射
     * 3、第二遍遍历新节点，依据新节点查询map1，若存在map1(newN, oldN.random)的映射，则查询map2, 令newN.random = map2.get(oldN)
     */
    public Node copyRandomList(Node head) {
        Node o = head;

        Node newN = new Node(-1);
        Node n = newN;
        Map<Node, Node> map1 = new HashMap<>();
        Map<Node, Node> map2 = new HashMap<>();


        while (o != null) {
            Node n1 = new Node(o.val);

            n.next = n1;
            map1.put(n1, o.random);
            map2.put(o, n1);

            n = n.next;
            o = o.next;
        }

        n = newN.next;
        while (n != null) {
            if (map1.containsKey(n)) {
                n.random = map2.get(map1.get(n));
            }
            n = n.next;
        }


        return newN.next;
    }


    /**
     * 排序链表
     * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     * <p>
     * 进阶：
     * 你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
     * <p>
     * 思路1：
     * 1、借助小顶堆，先将链表依次放入小顶堆
     * 2、再依次从堆顶取出，构成需返回的链表
     * <p>
     * 思路2：
     * 1、转成int[], 在利用java的sort的方法进行排序
     * 2、再遍历int[]，构成需返回的链表
     * <p>
     * 思路3：归并排序
     * 1、自顶向下分割链表，直到单个有序
     * 2、自底向上，不断的合并两个有序链表，最后返回整个有序链表
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        return sortListHelp(head, null);
    }

    private ListNode sortListHelp(ListNode head, ListNode tail) {
        if (head.next == tail) {
            head.next = null;
            return head;
        }

        ListNode fast = head;
        ListNode slow = head;
        while (fast != tail && fast.next != tail) {
            fast = fast.next.next;
            slow = slow.next;
        }
        ListNode a = sortListHelp(head, slow);
        ListNode b = sortListHelp(slow, tail);
        return mergeTwo(a, b);
    }

    private ListNode mergeTwo(ListNode a, ListNode b) {
        ListNode head = new ListNode();
        ListNode p = head;

        ListNode p1 = a;
        ListNode p2 = b;

        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }
        if (p1 != null) {
            p.next = p1;
        }
        if (p2 != null) {
            p.next = p2;
        }

        return head.next;
    }

    public ListNode sortList3(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode p = head;
        int len = 0;

        while (p != null) {
            p = p.next;
            len++;
        }

        int[] nums = new int[len];
        p = head;
        int i = 0;
        while (p != null) {
            nums[i++] = p.val;
            p = p.next;
        }

        Arrays.sort(nums);

        p = head;
        for (int num : nums) {
            p.next = new ListNode(num);
            p = p.next;
        }

        return head.next;
    }

    public ListNode sortList1(ListNode head) {
        List<Integer> list = new ArrayList<>();

        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        ListNode p = new ListNode();
        ListNode q = p;

        list.sort(Integer::compareTo);

        for (Integer v : list) {
            q.next = new ListNode(v);
            q = q.next;
        }

        return p.next;
    }

    public ListNode sortList2(ListNode head) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
        ListNode p = head;
        while (p != null) {
            queue.offer(p);
            p = p.next;
        }

        if (!queue.isEmpty()) {
            head = queue.poll();
            p = head;
        }
        while (!queue.isEmpty()) {
            p.next = queue.poll();
            p = p.next;
        }
        if (p != null) {
            p.next = null;
        }
        return head;
    }


    /**
     * 合并K个排序链表
     * 给你一个链表数组，每个链表都已经按升序排列。
     * <p>
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     * <p>
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     * <p>
     * 思路：堆
     * 1、遍历列表，构建堆
     * 2、遍历堆，构建res
     * <p>
     * 思路2：
     * 1、根据lists的每个链表的头结点构建一个最小堆
     * 2、取堆顶的节点node，接到结果集中，若node不是尾节点，则将node.next加入堆
     * 3、重复步骤2，直至堆空为止
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-hard/xwylvd/?discussion=YWr4cB
     */
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>(lists.length, (a, b) -> Integer.compare(a.val, b.val));
        ListNode res = new ListNode();
        ListNode p = res;

        for (ListNode head : lists) {
            if (head != null) {
                queue.offer(head);
            }
        }

        while (!queue.isEmpty()) {
            ListNode node = queue.poll();

            if (node.next != null) {
                queue.offer(node.next);
            }
            p.next = node;
            p = p.next;
        }

        return res.next;
    }

    public ListNode mergeKLists1(ListNode[] lists) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
        ListNode res = new ListNode();
        ListNode p = res;

        for (ListNode head : lists) {
            while (head != null) {
                queue.add(head);
                head = head.next;
            }
        }

        while (!queue.isEmpty()) {
            p.next = queue.poll();
            p = p.next;
        }
        p.next = null;

        return res.next;
    }


    /**
     * 最小覆盖子串
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * <p>
     * 思路：
     * 滑动窗口算法的思路是这样：
     * 1、我们在字符串 S 中使用双指针中的左右指针技巧，初始化 left = right = 0，把索引左闭右开区间 [left, right) 称为一个「窗口」。
     * 2、我们先不断地增加 right 指针扩大窗口 [left, right)，直到窗口中的字符串符合要求（包含了 T 中的所有字符）。
     * 3、此时，我们停止增加 right，转而不断增加 left 指针缩小窗口 [left, right)，直到窗口中的字符串不再符合要求（不包含 T 中的所有字符了）。
     * 同时，每次增加 left，我们都要更新一轮结果。
     * 4、重复第 2 和第 3 步，直到 right 到达字符串 S 的尽头。
     */
    public String minWindow1(String s, String t) {
        int[] need = new int[128];
        int[] window = new int[128];

        char[] sc = s.toCharArray();
        char[] tc = t.toCharArray();

        int valid = 0;
        for (char c : tc) {
            need[c]++;
            valid++;
        }

        int p = 0;
        int q = 0;
        int start = 0;
        int len = Integer.MAX_VALUE;
        while (q < sc.length) {
            char c = sc[q];
            q++;

            if (need[c] > 0) {
                window[c]++;
                if (need[c] >= window[c]) {
                    valid--;
                }
            }

            // System.err.println("window: [" + p + ", " + q + "]" + " "+valid);

            while (valid == 0) {
                if (q - p < len) {
                    start = p;
                    len = q - p;
                }

                char d = sc[p];
                p++;

                if (need[d] > 0) {
                    if (need[d] >= window[d]) {
                        valid++;
                    }
                    window[d]--;
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    public String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        char[] sc = s.toCharArray();
        char[] tc = t.toCharArray();

        for (char c : tc) {
            need.merge(c, 1, Integer::sum);
        }

        int p = 0;
        int q = 0;
        int valid = 0;
        int start = 0;
        int len = Integer.MAX_VALUE;
        while (q < sc.length) {
            char c = sc[q];
            q++;

            if (need.containsKey(c)) {
                window.merge(c, 1, Integer::sum);
                if (need.get(c).equals(window.get(c))) {
                    valid++;
                }
            }

            // System.err.println("window: [" + p + ", " + q + "]");

            while (valid == need.size()) {
                if (q - p < len) {
                    start = p;
                    len = q - p;
                }

                char d = sc[p];
                p++;

                if (need.containsKey(d)) {
                    if (need.get(d).equals(window.get(d))) {
                        valid--;
                    }
                    window.merge(d, -1, Integer::sum);
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    /**
     * 滑动窗口最大值
     * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     * <p>
     * 返回滑动窗口中的最大值。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-hard/xw4q0r/
     * <p>
     * [单调队列结构解决滑动窗口问题 :: labuladong的算法小抄](https://labuladong.gitee.io/algo/2/18/44/)
     * <p>
     * 思路：
     * 1、构建一个单调队列
     * 2、先放入k-1个元素，之后每次放入一个新元素，记录当前窗口的最大元素，剔除一个最先进入的元素
     * <p>
     * 思路2：
     * 1、 k==1，返回nums
     * 2、 记最大值的下标为index，最大值为max，滑动窗口左右指针p，q，结果集ans=int[nums.len-k+1]
     * 3、 遍历nums
     * a、if p<index, 若 num[q]>=max, 则max=num[q],index=q
     * b、elseif num[q]>=max-1, 则max=num[q],index=q
     * c、elseif num[p]>=max-1, 则max=num[p],index=p
     * d、else 令max=Int.MIN, 遍历p到q,找到最大值nums[i], 令max=nums[i], index=i
     * e、ans[p]=max
     * 4、返回ans
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        MonotonicQueue queue = new MonotonicQueue();

        int[] res = new int[nums.length - k + 1];
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (i < k - 1) {
                queue.push(nums[i]);
            } else {
                queue.push(nums[i]);
                res[j++] = queue.max();
                queue.pop(nums[i - k + 1]);
            }
        }
        return res;
    }

    static class MonotonicQueue {
        LinkedList<Integer> q = new LinkedList<>();

        public void push(int n) {
            // 将小于 n 的元素全部删除
            while (!q.isEmpty() && q.getLast() < n) {
                q.pollLast();
            }
            // 将小于 n 的元素全部删除
            q.addLast(n);
        }

        public int max() {
            return q.getFirst();
        }

        public void pop(int n) {
            if (n == q.getFirst()) {
                q.pollFirst();
            }
        }
    }

    public int[] maxSlidingWindow1(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }

        int index = -1, n = nums.length, max = Integer.MIN_VALUE;
        int[] ans = new int[n - k + 1];
        for (int p = 0, q = k - 1; q < n; ++p, ++q) {
            if (p <= index) {
                if (nums[q] >= max) {
                    max = nums[q];
                    index = q;
                }
            } else if (nums[q] >= max - 1) {
                max = nums[q];
                index = q;
            } else if (nums[p] >= max - 1) {
                max = nums[p];
                index = p;
            } else {
                max = Integer.MIN_VALUE;
                for (int i = p; i <= q; ++i) {
                    if (nums[i] >= max) {
                        max = nums[i];
                        index = i;
                    }
                }
            }
            ans[p] = max;
        }
        return ans;
    }

    /**
     * 基本计算器 II
     * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
     * 整数除法仅保留整数部分。
     * <p>
     * 思路1：
     * 1、中序表达式转后序表达式
     * a. 遇到操作数。直接加入后序表达式
     * b. 遇到界限符。遇到“（”直接入栈，遇到“）”则依次弹出 栈内运算符 并加入后序表达式，直到弹出”（“为止。”（“不加入后序表达式
     * c. 遇到运算符。依次弹出 栈中优先级高于或者等于当前运算符的所有运算符，并加入后序表达式，若碰到“（”或者栈空则停止，之后再把当前运算符入栈
     * d. 按上述方法处理完所有字符后，将栈中运算符依次弹出，并加入后缀表达式。
     * 2、依据后序表达式计算结果
     * <p>
     * 思路2：
     * 1、替换掉空格, 标记每个数字前的操作符为ops
     * 2、遍历字符串，记每个数字为sum，恰当的将sum放到栈中，最后将栈中所有的sum求和
     * a、+  push(sum)
     * b、- push(-sum)
     * c、* push(pop*sum)
     * d、/ push(pop/sum)
     */
    public int calculate1(String s) {
        s = s.replaceAll(" ", "");

        Deque<Integer> stack = new LinkedList<>();
        char ops = '+';
        int sum = 0;
        char[] cs = s.toCharArray();

        for (char c : cs) {
            if (digit(c)) {
                sum = sum * 10 + (c - '0');
            } else {
                calcHelp(stack, ops, sum);
                ops = c;
                sum = 0;
            }
        }
        calcHelp(stack, ops, sum);

        int ans = 0;
        while (!stack.isEmpty()) {
            ans += stack.pop();
        }

        return ans;
    }

    private void calcHelp(Deque<Integer> stack, char ops, int sum) {
        if (ops == '+') {
            stack.push(sum);
        } else if (ops == '-') {
            stack.push(-sum);
        } else if (ops == '*') {
            stack.push(stack.pop() * sum);
        } else if (ops == '/') {
            stack.push(stack.pop() / sum);
        }
    }

    private boolean digit(char c) {
        return '0' <= c && c <= '9';
    }

    public int calculate(String s) {
        s = s.replaceAll("\\s*", "");

        String[] afterStr = midToAfter(s);

        return evalRPN(afterStr);
    }

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            if (noDigit(token)) {
                Integer b = stack.pop();
                Integer a = stack.pop();
                stack.push(calc(a, b, token));
                continue;
            }
            stack.push(Integer.parseInt(token));
        }
        return stack.peek();
    }

    public String[] midToAfter(String s) {
        List<String> afterStr = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Stack<String> oStack = new Stack<>();

        char[] chars = s.toCharArray();

        for (char c : chars) {
            if (noDigit(c)) {
                afterStr.add(sb.toString());
                sb.setLength(0);

                String operator = String.valueOf(c);
                while (!oStack.isEmpty() && morePower(oStack.peek(), operator)) {
                    afterStr.add(oStack.pop());
                }
                oStack.push(operator);
                continue;
            }
            sb.append(c);
        }
        if (sb.length() > 0) {
            afterStr.add(sb.toString());
        }
        while (!oStack.isEmpty()) {
            afterStr.add(oStack.pop());
        }
//        return afterStr.toArray(String[]::new); //jdk1.8 不支持
        return afterStr.stream().toArray(String[]::new);
    }

    // 判断 a运算符的优先级是否 >= b运算符的优先级 (*/ > +-)
    private boolean morePower(String a, String b) {
        if (a.equals("+") || a.equals("-")) {
            if (b.equals("*") || b.equals("/")) {
                return false;
            }
        }
        return true;
    }

    private boolean noDigit(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean noDigit(String s) {
        return "+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s);
    }

    private int calc(int a, int b, String c) {
        switch (c) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                return 0;
        }
    }

    /**
     * 寻找重复数
     * 定一个包含 n + 1 个整数的数组 nums ，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
     * <p>
     * 假设 nums 只有 一个重复的整数 ，找出 这个重复的数 。
     * <p>
     * 你设计的解决方案必须不修改数组 nums 且只用常量级 O(1) 的额外空间。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-hard/xwz4lj/
     * <p>
     * 思路： 成环法的应用
     * ps: 理解为何 k 是环长度的整数倍, 以及如何找到环的起点（重复数）
     * <p>
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
