package com.fd.algorithmlearn;

import java.util.*;

/**
 * 初级算法
 * LeetCode 官方推出的经典面试题目清单 —— 「初级算法 - 帮助入门」
 * <p>
 * https://leetcode-cn.com/leetbook/detail/top-interview-questions-easy/
 * <p>
 * 整理
 *
 * @author zhangxinqiang
 * @create 2021/12/31 14:16
 */
public class Level1 {


    // -------------------------- 字符串 ------------------------------
    // 字符串
    // 字符串问题在面试中出现频率很高，你极有可能在面试中遇到。
    // 我们推荐以下题目：反转字符串，字符串中第一个唯一字符，字符串转整数（atoi）和 实现 strStr() 。

    /**
     * 反转字符串
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * <p>
     * 示例 1：
     * 输入：s = ["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     * <p>
     * 思路1：双指针
     * 1、双指针分别从首尾向中间靠拢，并相互交换
     */
    public void reverseString(char[] s) {
        for (int i = 0; i < s.length / 2; i++) {
            char temp = s[i];
            s[i] = s[s.length - 1 - i];
            s[s.length - 1 - i] = temp;
        }
    }

    // -------------------------- 字符串 ------------------------------ end


    // -------------------------- 数组 ------------------------------
    // 数组问题在面试中出现频率很高，你极有可能在面试中遇到。
    // 我们推荐以下题目：只出现一次的数字，旋转数组，两个数组的交集 II 和 两数之和。

    /**
     * 旋转图像
     * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
     * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
     * <p>
     * 思路1：先上下交换，再对角线交换
     * <p>
     * 思路2：直接交换
     * 1、一圈一圈旋转即可，因为对称，旋转前半行即可
     */
    public void rotate(int[][] matrix) {
        for (int i = 0; i < matrix.length / 2; i++) {
            int[] temp = matrix[i];
            matrix[i] = matrix[matrix.length - 1 - i];
            matrix[matrix.length - 1 - i] = temp;
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix[0].length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    public void rotate1(int[][] matrix) {
        int length = matrix.length;
        // 因为是对称的，只需要计算循环前半行即可
        for (int i = 0; i < length / 2; i++) {
            for (int j = i; j < length - i - 1; j++) {
                int temp = matrix[i][j];
                int m = length - j - 1;
                int n = length - i - 1;
                matrix[i][j] = matrix[m][i];
                matrix[m][i] = matrix[n][m];
                matrix[n][m] = matrix[j][n];
                matrix[j][n] = temp;
            }
        }
    }

    /**
     * 有效的数独
     * 请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
     * <p>
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
     * <p>
     * 注意：
     * 一个有效的数独（部分已被填充）不一定是可解的。
     * 只需要根据以上规则，验证已经填入的数字是否有效即可。
     * 空白格用 '.' 表示。
     * <p>
     * 思路1：哈希
     * 1、使用Set的特性判断是否重复放入
     * 2、检查行，检查列，检查宫（3*3）
     * <p>
     * 思路2：数组 空间换时间
     * 1、声明3个9*9的 bool数组, 分别用于检查行，列，宫
     * 2、双重循环，判断行，列，宫都符合规则
     * a、行[i][board[i][j] - '1'],
     * b、列[j][board[i][j] - '1'],
     * c、宫[int bid = 3 * (i / 3) + (j / 3)][board[i][j] - '1']
     */
    public boolean isValidSudoku(char[][] board) {
        Set<Character> num = new HashSet<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '.') {
                    if (!num.add(board[i][j])) {
                        return false;
                    }
                }
            }
            num.clear();
        }

        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] != '.') {
                    if (!num.add(board[j][i])) {
                        return false;
                    }
                }
            }
            num.clear();
        }

        for (int k = 0; k < 9; k += 3) {
            for (int i = 0; i < 9; i++) {
                if (i % 3 == 0) {
                    num.clear();
                }
                for (int j = 0; j < 3; j++) {
                    char x = board[i][j + k];
                    if (x != '.') {
                        if (!num.add(x)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // 空间换时间
    public boolean isValidSudoku1(char[][] board) {
        //每一行num是否存在 存在为true
        boolean[][] row = new boolean[9][9];
        //每一列num是否存在 存在为true
        boolean[][] col = new boolean[9][9];
        //每一宫num是否存在 存在为true
        boolean[][] bucket = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //获取每个宫的位置
                int bid = 3 * (i / 3) + (j / 3);
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    if (row[i][num] || col[j][num] || bucket[bid][num])
                        return false;
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                }
            }
        }
        return true;
    }

    /**
     * 两数之和
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     * 你可以按任意顺序返回答案。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     * <p>
     * 思路：哈希
     * 1、遍历nums，令 b = target-nums[i]
     * a、判断map中是否存在b，存在则返回
     * b、不存在，则将 (nums[i],i) 放入map
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int b = target - nums[i];
            if (map.containsKey(b)) {
                return new int[]{i, map.get(b)};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[]{-1, -1};
    }


    /**
     * 移动零
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * <p>
     * 示例:
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * <p>
     * 思路：双指针
     * 1、指针 i, j。初始化i指向第一个0,j(j>i)指向第一个不为0的数
     * 2、交换nus[i],nums[j], j向后移动找到不是0的元素，i向后移动找0
     * <p>
     * 思路2：快慢指针
     * 1、初始化两个指针slow， fast
     * 2、遍历nums，每当遇到不是0的数，令nums[slow]=nums[fast], slow++;
     * 3、遍历完nums后，若slow<nums.length, 则将[slow,nums.length)置0
     */

    // 思路2
    public void moveZeroes(int[] nums) {
        int fast = 0, slow = 0;
        for (; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                nums[slow++] = nums[fast];
            }
        }
        for (; slow < nums.length; slow++) {
            nums[slow] = 0;
        }
    }

    public void moveZeroes1(int[] nums) {
        int i = 0;
        int j = 0;
        while (i < nums.length && j < nums.length) {
            while (i < nums.length && nums[i] != 0) {
                i++;
            }
            j = i + 1;
            while (j < nums.length && nums[j] == 0) {
                j++;
            }
            if (i < nums.length && j < nums.length) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
    }


    /**
     * 加一
     * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     * <p>
     * 示例 1：
     * 输入：digits = [1,2,3]
     * 输出：[1,2,4]
     * 解释：输入数组表示数字 123。
     * <p>
     * 思路：
     * 1、遍历digits，从后往前加
     * 2、处理两种情况：digits[i]=9, 则进位。不是9则加1，且退出循环
     * 3、处理 类似 【9,99,999】加一位数会变多的情况
     */
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                digits[i] = 0;
            } else {
                digits[i] += 1;
                return digits;
            }
        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }

    /**
     * 两个数组的交集 II
     * 给你两个整数数组 nums1 和 nums2 ，请你以数组形式返回两数组的交集。返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。可以不考虑输出结果的顺序。
     * <p>
     * 示例 1：
     * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出：[2,2]
     * <p>
     * 思路：排序+双指针
     * 1、先排序，在用双指针i,j遍历数组
     * 2、nums1[i], nums2[j] : 两者不同时，值小的指针往后移。相同时，则相交，加入结果集，两个指针同时往后移
     * <p>
     * 思路2：哈希表
     * 1、遍历nums1, 使用map记录，【数字，数字出现的次数】
     * 2、遍历nums2, 使用map判断nums1中是否存在该数字，存在则加入结果集，并更新map记录
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length < nums2.length) {
            return intersect(nums2, nums1);
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int x : nums1) {
            map.merge(x, 1, Integer::sum);
        }

        List<Integer> list = new ArrayList<>();
        for (int x : nums2) {
            if (map.getOrDefault(x, 0) > 0) {
                list.add(x);
                map.merge(x, -1, Integer::sum);
            }
        }

        int[] res = new int[list.size()];
        for (int k = 0; k < list.size(); k++) {
            res[k] = list.get(k);
        }
        return res;
    }


    public int[] intersect1(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> list = new ArrayList<>();
        for (int i = 0, j = 0; i < nums1.length && j < nums2.length; ) {
            if (nums1[i] > nums2[j]) {
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                list.add(nums1[i]);
                i++;
                j++;
            }
        }

        int[] res = new int[list.size()];
        for (int k = 0; k < list.size(); k++) {
            res[k] = list.get(k);
        }
        return res;
    }

    /**
     * 只出现一次的数字
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 说明：
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     * <p>
     * 思路：异或运算
     * 1、异或运算：a^a=0, a^0=a, a^b^a=a^a^b=b
     * 2、遍历数组两两进行异或，会留下只出现过一次的数字
     * <p>
     * 思路2：Set
     * 1、使用set.add()判断，是否是第二次添加
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int num : nums) {
            res ^= num;
        }
        return res;
    }

    public int singleNumber1(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                set.remove(num);
            }
        }
        return (int) set.toArray()[0];
    }

    /**
     * 存在重复元素
     * 给定一个整数数组，判断是否存在重复元素。
     * 如果存在一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
     * <p>
     * 思路1：排序
     * 1、排序
     * 2、遍历, 判断相邻的两个数字是否相等即可
     * <p>
     * 思路2：哈希
     * 1、创建一个set记录已经出现过的值
     * 2、遍历，遇到第一个重复的值，或者没有重复
     * <p>
     * 思路3：借助数组 实现类似手动哈希
     * 1、创建一个同长的数组
     * 2、遍历, 借助余数 num[i]%num.length 定位位置, 快速判断是否存在重复
     * ps: （注意num[0]=0的特殊情况）
     */
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDuplicate1(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        return false;
    }


    /**
     * 旋转数组
     * 给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * <p>
     * 思路：数学
     * 1、(A^-1 B^-1)^-1 = BA
     * <p>
     * [逆矩阵_百度百科](https://baike.baidu.com/item/%E9%80%86%E7%9F%A9%E9%98%B5/10481136?fr=aladdin)
     */
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        k = k % len;

        reverse(nums, 0, len - k);
        reverse(nums, len - k, len);
        reverse(nums, 0, len);
    }

    public void reverse(int[] nums, int start, int end) {
        for (int i = start, j = end - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    /**
     * 买卖股票的最佳时机 II
     * 给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * <p>
     * (第三遍)
     * 思路：贪心
     * 1、没有任何买卖限制，完全可以站在今天看昨天，昨天比今天低，就假设昨天买了，今天卖了
     * 2、所有正利润的和，为最大利润
     */
    public int maxProfit(int[] prices) {
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            int x = prices[i] - prices[i - 1];
            if (x > 0) {
                sum += x;
            }
        }
        return sum;
    }

    /**
     * 删除排序数组中的重复项
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * <p>
     * <p>
     * 思路：双指针
     * 1、遍历nums, len指向值未重复的下标，i一直往后
     * 2、当遇到不同的值时，将len++, 然后令nums[len]=nums[i]
     * <p>
     * ps：数组有序
     */
    public int removeDuplicates(int[] nums) {
        int len = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[len] != nums[i]) {
                nums[++len] = nums[i];
            }
        }
        return ++len;
    }


    // -------------------------- 其它 ------------------------------end

}
