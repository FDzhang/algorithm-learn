package com.fd.algorithmlearn;

import com.fd.algorithmlearn.linked.ListNode;

import java.util.*;

/**
 * https://leetcode-cn.com/leetbook/detail/top-interview-questions-easy/
 * 初级算法
 * LeetCode 官方推出的经典面试题目清单 —— 「初级算法 - 帮助入门」
 *
 * @author zhangxinqiang
 * @create 2021/5/14 14:16
 */
public class Temp {
    /**
     * 删除排序数组中的重复项
     */
    public int removeDuplicates(int[] nums) {
        int len = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[len] != nums[i]) {
                nums[++len] = nums[i];
            }
        }
        len++;
        return len;
    }

    /**
     * 买卖股票的最佳时机 II
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
     * 旋转数组
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
     * 存在重复元素
     */
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 只出现一次的数字
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int num : nums) {
            res ^= num;
        }
        return res;
    }

    /**
     * 两个数组的交集 II
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> res = new ArrayList<>();
        for (int i = 0, j = 0; i < nums1.length && j < nums2.length; ) {
            if (nums1[i] > nums2[j]) {
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                res.add(nums1[i]);
                i++;
                j++;
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 加一
     */
    public int[] plusOne(int[] digits) {
        int i = digits.length - 1;
        for (; i >= 0; i--) {
            if (digits[i] == 9) {
                digits[i] = 0;
            } else {
                digits[i] += 1;
                break;
            }
        }
        if (i < 0) {
            int[] res = new int[digits.length + 1];
            res[0] = 1;
            return res;
        }
        return digits;
    }

    /**
     * 移动零
     */
    public void moveZeroes(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        //声明快慢指针
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

    /**
     * 两数之和
     */
    public int[] twoSum(int[] nums, int target) {
        Arrays.sort(nums);
        int i = 0;
        int j = nums.length - 1;
        while (i < j) {
            if (nums[i] + nums[j] < target) {
                i++;
            } else if (nums[i] + nums[j] > target) {
                j--;
            } else {
                return new int[]{i, j};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 有效的数独
     */
    public static boolean isValidSudoku(char[][] board) {
        Set<Character> num = new HashSet<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '.') {
                    if (num.contains(board[i][j])) {
                        return false;
                    }
                    num.add(board[i][j]);
                }
            }
            num.clear();
        }

        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] != '.') {
                    if (num.contains(board[j][i])) {
                        return false;
                    }
                    num.add(board[j][i]);
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
                        if (num.contains(x)) {
                            return false;
                        }
                        num.add(x);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 旋转图像
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnhhkv/
     *
     * 略
     */

    /**
     * 反转字符串
     */
    public void reverseString(char[] s) {
        for (int i = 0; i < s.length / 2; i++) {
            char temp = s[i];
            s[i] = s[s.length - 1 - i];
            s[s.length - 1 - i] = temp;
        }
    }

    /**
     * 反转字符串
     */
    public int reverse(int x) {
        long res = 0;
        while (x != 0) {
            res = res * 10 + x % 10;
            x /= 10;
        }
        return res == (int) res ? (int) res : 0;
    }

    /**
     * 字符串中的第一个唯一字符
     */
    public static int firstUniqChar(String s) {
        char[] chars = s.toCharArray();
        int[] zm = new int[26];
        for (char c : chars) {
            zm[c - 'a']++;
        }
        for (int i = 0; i < chars.length; i++) {
            if (zm[chars[i] - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 有效的字母异位词
     */
    public boolean isAnagram(String s, String t) {
        int[] zmc = new int[26];
        int[] zmt = new int[26];
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();

        for (char c : cs) {
            zmc[c - 'a']++;
        }
        for (char c : ct) {
            zmt[c - 'a']++;
        }
        for (int i = 0; i < 26; i++) {

            if (zmc[i] != zmt[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证回文串
     */
    public static boolean isPalindrome(String s) {
        char[] cs = s.toLowerCase().toCharArray();
        int i = 0;
        int j = cs.length - 1;

        while (i < j) {

            while (i < j && !check(cs[i])) {
                i++;
            }
            while (i < j && !check(cs[j])) {
                j--;
            }
            if (i > j) {
                break;
            }
            if (cs[i] != cs[j]) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public static boolean check(char c) {
        return (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }

    /**
     * 字符串转换整数 (atoi)
     * <p>
     * 思路
     * 1 忽略空白字符
     * 2 第一个字符是否 是 '-' or '+' or 数字
     * 3 接下来的 是否是 数字
     * 4 数字 是否是 int 在范围内
     */
    public static int myAtoi(String s) {
        if (s == null || s.trim().length() == 0) {
            return 0;
        }
        char[] cs = s.trim().toCharArray();

        int res = 0;
        int fh = 1;
        if (checkNum(cs[0])) {
            res += (cs[0] - '0') * fh;
        } else if (cs[0] == '-') {
            fh = -1;
        } else if (cs[0] != '+') {
            return 0;
        }

        for (int i = 1; i < cs.length; i++) {
            if (checkNum(cs[i])) {
                long newRes = (long) res * 10 + (cs[i] - '0') * fh;
                if (newRes != (int) newRes) {
                    return fh > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }
                res = (int) newRes;
            } else {
                break;
            }
        }
        return res;
    }

    public static boolean checkNum(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 实现 strStr()
     */
    public static int strStr(String haystack, String needle) {
        int i = -1;
        if (haystack != null) {
            i = haystack.indexOf(needle);
        }
        return i;
    }

    /**
     * 外观数列
     */
    public static String countAndSay(int n) {
        String str = "1";
        if (n == 1) {
            return str;
        }
        for (int i = 1; i < n; i++) {
            str = say(str);
        }
        return str;
    }

    private static String say(String s) {
        char[] cs = s.toCharArray();
        StringBuilder sb = new StringBuilder();

        int count = 1;
        for (int i = 1; i < cs.length; i++) {
            if (cs[i - 1] != cs[i]) {
                sb.append(count).append(cs[i - 1]);
                count = 0;
            }
            count++;
        }
        sb.append(count).append(cs[cs.length - 1]);
        return sb.toString();
    }

    /**
     * 最长公共前缀
     */
    public static String longestCommonPrefix(String[] strs) {
        // 找出最小长度的的字符串
        int minLen = Integer.MAX_VALUE;
        String minStr = "";
        for (String str : strs) {
            if (minLen > str.length()) {
                minLen = str.length();
                minStr = str;
            }
        }

        for (String str : strs) {
            while (minLen > 0) {
                // 是否是共同前缀
                boolean start = str.startsWith(minStr.substring(0, minLen));
                if (start) {
                    break;
                } else {
                    minLen--;
                }
            }
        }
        return minStr.substring(0, minLen);
    }

    /**
     * 删除链表中的节点
     */
    public static void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * 删除链表的倒数第N个节点
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;

        // 1 2 3 4 5 null
        int i = 0;
        while (fast != null && i < n) {
            fast = fast.next;
            i++;
        }
        if (fast == null) {
            head = head.next;
            return head;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }

    /**
     * 反转链表
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode pre = null;
        ListNode cur = head;
        ListNode next = null;

        while (cur.next != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        cur.next = pre;

        return cur;
    }

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */

    /**
     * 合并两个有序链表
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode head, cur;
        if (l1.val > l2.val) {
            head = l2;
            cur = l2;
            l2 = l2.next;
        } else {
            head = l1;
            cur = l1;
            l1 = l1.next;
        }

        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                cur.next = l2;
                l2 = l2.next;
            } else {
                cur.next = l1;
                l1 = l1.next;
            }
            cur = cur.next;
        }
        if (l1 != null) {
            cur.next = l1;
        }
        if (l2 != null) {
            cur.next = l2;
        }
        return head;
    }


    /**
     * 回文链表
     */
    public boolean isPalindrome(ListNode head) {
        ListNode slow, fast, left;
        slow = fast = left = head;
        // 快慢指针，找到中点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 链表长度为奇数，slow再往前一步
        if (fast != null) {
            slow = slow.next;
        }
        // 翻转后半部分链表
        ListNode right = reverse(slow);

        while (right != null && left != null) {
            if (left.val != right.val) {
                return false;
            }
            left = left.next;
            right = right.next;
        }
        return true;
    }

    /**
     * 翻转链表，返回新链表的头结点
     */
    public ListNode reverse(ListNode head) {
        ListNode pre = null, cur = head, next;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 环形链表
     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     */

    /**
     * 二叉树的最大深度
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = maxDepth(root.left);

        int right = maxDepth(root.right);

        return Math.max(left, right) + 1;
    }

    /**
     * 验证二叉搜索树
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     * <p>
     * 假设一个二叉搜索树具有如下特征：
     * <p>
     * 节点的左子树只包含小于当前节点的数。
     * 节点的右子树只包含大于当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * <p>
     * 思路：
     * 1. root节点的值 要大于左节点，要小于右节点
     * 2. root节点的值 是左子树的最大值，是右子树的最小值
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    /* 限定以 root 为根的子树节点必须满足 max.val > root.val > min.val */
    public boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        // base case
        if (root == null) {
            return true;
        }
        // 若 root.val 不符合 max 和 min 的限制，说明不是合法 BST
        if (min != null && root.val <= min.val) {
            return false;
        }
        if (max != null && root.val >= max.val) {
            return false;
        }
        // 限定左子树的最大值是 root.val，右子树的最小值是 root.val
        return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
    }

    /**
     * 二叉树的层序遍历
     * 1 创建 当前层的 val list
     * 2 获取 下一层的 nodes
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();

        Queue<TreeNode> level = new LinkedList<>();
        level.offer(root);

        while (!level.isEmpty()) {
            List<Integer> l = new ArrayList<>();
            level = levelNode(level, l);
            res.add(l);
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
     * 将有序数组转换为二叉搜索树
     * <p>
     * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
     * 高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xninbt/
     * <p>
     * eg: [-10,-3,0,5,9] 5
     * <p>
     * 思路：
     * 1 将 mid(1/2位置)上的值放到 root
     * 2 将 left ~ mid-1 放到左子树， 将 mid+1 ~ right 放到右子树
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        if (right < left) {
            return null;
        }
        int index = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[index]);
        root.left = sortedArrayToBST(nums, left, index - 1);
        root.right = sortedArrayToBST(nums, index + 1, right);
        return root;
    }

    /**
     * 对称二叉树
     * 给定一个二叉树，检查它是否是镜像对称的。
     * <p>
     * <p>
     * 思路：
     * 方法一：
     * 1 需要从子节点开始比较，两个子节点的值必须相同，
     * 2 并且左子节点的右子节点（如果有）必须等于右子节点的左子节点，左子节点的左子节点必须等于右子节点的右子节点
     * <p>
     * 方法二：
     * 1 翻转二叉树
     * 2 判断前后两颗二叉树是否一致
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetric(root.left, root.right);
    }

    public boolean isSymmetric(TreeNode left, TreeNode right) {
        // 叶子节点
        if (left == null && right == null) {
            return true;
        }
        // 节点不对称 or 值不同
        if (left == null || right == null || left.val != right.val) {
            return false;
        }
        // 左.右 == 右.左 && zuo.zuo == you.you
        return isSymmetric(left.right, right.left) && isSymmetric(left.left, right.right);
    }

//    public List<Integer> treeToArray(TreeNode root, List<Integer> arr) {
//        if (root == null) {
//            arr.add(-1);
//            return arr;
//        }
//        arr.add(root.val);
//        treeToArray(root.left, arr);
//        treeToArray(root.right, arr);
//        return arr;
//    }
//
//    public TreeNode reverse(TreeNode root) {
//        if (root == null) {
//            return null;
//        }
//        TreeNode temp = root.left;
//        root.left = root.right;
//        root.right = temp;
//
//        reverse(root.left);
//        reverse(root.right);
//
//        return root;
//    }

    /**
     * 合并两个有序数组
     * <p>
     * 给你两个有序整数数组nums1 和 nums2，请你将 nums2 合并到nums1中，使 nums1 成为一个有序数组。
     * 初始化nums1 和 nums2 的元素数量分别为m 和 n 。你可以假设nums1 的空间大小等于m + n，这样它就有足够的空间保存来自 nums2 的元素。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnumcr/
     * <p>
     * 思路：
     * 1、 把 nums1 ,nums2 和并到 nums3 中
     * 2、 将 nums3 覆盖到 nums1 中
     * <p>
     * 思路2：
     * 1、从后往前写
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, writeIdx = m + n - 1;
        while (i >= 0 && j >= 0) {
            nums1[writeIdx--] = nums1[i] > nums2[j] ? nums1[i--] : nums2[j--];
        }
        while (j >= 0) {
            nums1[writeIdx--] = nums2[j--];
        }
    }
//    public static void merge(int[] nums1, int m, int[] nums2, int n) {
//        int[] nums3 = new int[m + n];
//
//        int i = 0;
//        int j = 0;
//        while (i < m && j < n) {
//            if (nums1[i] < nums2[j]) {
//                nums3[i + j] = nums1[i];
//                i++;
//            } else {
//                nums3[i + j] = nums2[j];
//                j++;
//            }
//        }
//        while (i < m) {
//            nums3[i + j] = nums1[i];
//            i++;
//        }
//        while (j < n) {
//            nums3[i + j] = nums2[j];
//            j++;
//        }
//
//        if (nums3.length >= 0) {
//            System.arraycopy(nums3, 0, nums1, 0, nums3.length);
//        }
//    }

    /* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

    /**
     * 第一个错误的版本
     * 你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
     * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
     * 你可以通过调用bool isBadVersion(version)接口来判断版本号 version 是否在单元测试中出错。实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnto1s/
     * <p>
     * 思路： 二分
     * 第一个错误版本，在最后一个false的下一个
     */
    public int firstBadVersion(int n) {
        int i = 1;
        int j = n;
        while (j > i) {
            int mid = i + (j - i) / 2;
            if (isBadVersion(mid)) {
                j = mid;
            } else {
                i = mid + 1;
            }
        }
        return i;
    }

    boolean isBadVersion(int version) {
        return version >= 4;
    }

    /**
     * 爬楼梯
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 注意：给定 n 是一个正整数。
     * <p>
     * 思路：
     * 1、爬n阶有几种方法 = 爬 n-1 有几种方法 + 爬 n-2 阶有几种方法
     */
    public static int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    /**
     * 买卖股票的最佳时机
     * 给定一个数组 prices ，它的第i 个元素prices[i] 表示一支给定股票第 i 天的价格。
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn8fsh/
     * <p>
     * 思路：
     * 1、当天能获得的最大利润 = 今天之前的股价最低的那天买入，今天卖出
     * 2、记录之前的最低股价，记录最大利润
     */
    public static int maxProfit1(int[] prices) {
        int min = prices[0];
        int max = 0;

        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - min;

            if (profit > max) {
                max = profit;
            }
            if (prices[i] < min) {
                min = prices[i];
            }
        }
        return max;
    }

    /**
     * 最大子序和
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * <p>
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn3cg3/
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
     * <p>
     * -2 1 -2 4 3 5 6 1 5
     * <p>
     * 思路：
     * dp[n+1] : 记录走到第n个位置为止(包括nums[n])的最大值
     * 若 前一步的最大值+本身 大于 本身，则 取最大值+本身，反之 取 本身
     * 所以 dp[i+1] = Math.max(dp[i] + nums[i], nums[i]);
     * <p>
     * 补： cur 代替 dp[n]
     */
    public static int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int num : nums) {
            cur = Math.max(cur + num, num);
            max = Math.max(cur, max);
        }
        return max;
    }

//    public static int maxSubArray(int[] nums) {
//        int max = Integer.MIN_VALUE;
//        int[] dp = new int[nums.length + 1];
//        for (int i = 0; i < nums.length; i++) {
//            dp[i + 1] = Math.max(dp[i] + nums[i], nums[i]);
//            max = Math.max(max, dp[i + 1]);
//        }
//        return max;
//    }

    /**
     * 打家劫舍
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnq4km/
     * <p>
     * 输入：[1,2,3,1]
     * 输出：4
     * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     *      偷窃到的最高金额 = 1 + 3 = 4 。
     * <p>
     * 输入：[2,7,9,3,1]
     * 输出：12
     * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
     * <p>
     * 思路：
     * dp[n+1] : 记录到第n个房间位置最多能偷多少钱
     * dp[i+1] = dp[i-1] + num[i] or dp[i]
     */
    public static int rob(int[] nums) {
        int[] dp = new int[nums.length + 1];
        int max = nums[0];
        dp[1] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i + 1] = Math.max(dp[i], dp[i - 1] + nums[i]);
            max = Math.max(dp[i + 1], max);
        }
        return max;
    }

    /**
     * Fizz Buzz
     * 写一个程序，输出从 1 到 n 数字的字符串表示。
     * 1. 如果n是3的倍数，输出“Fizz”；
     * 2. 如果n是5的倍数，输出“Buzz”；
     * 3. 如果n同时是3和5的倍数，输出 “FizzBuzz”。
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xngt85/
     */
    public static List<String> fizzBuzz(int n) {
        List<String> strList = new ArrayList<>();
        boolean a;
        boolean b;
        for (int i = 1; i <= n; i++) {
            a = i % 3 == 0;
            b = i % 5 == 0;
            if (a && b) {
                strList.add("FizzBuzz");
            } else if (a) {
                strList.add("Fizz");
            } else if (b) {
                strList.add("Buzz");
            } else {
                // ""+i 和 String.valueOf(i) 有一定的差距, String.valueOf(i)更快
                strList.add(String.valueOf(i));
            }
        }
        return strList;
    }

    /**
     * 计数质数
     * 统计所有小于非负整数 n 的质数的数量。
     * 输入：n = 10
     * 输出：4
     * 解释：小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
     * <p>
     * 思路：
     * 埃拉托斯特尼筛法，简称埃氏筛或爱氏筛
     */
    public static int countPrimes(int n) {
        if (n < 3) {
            return 0;
        }

        boolean[] isNotPrimes = new boolean[n];
        isNotPrimes[0] = true;
        isNotPrimes[1] = true;
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!isNotPrimes[i]) {
                count++;
                aiFilter(n, isNotPrimes, i);
            }
        }

        return count;
    }

    private static void aiFilter(int n, boolean[] isNotPrimes, int i) {
        if (i <= Math.sqrt(n)) {
            for (int j = i * i; j < n; j += i) {
                isNotPrimes[j] = true;
            }
        }
    }

    public int countPrimes1(int n) {
        boolean[] isPrime = new boolean[n];
        for (int i = 2; i < n; i++) {
            isPrime[i] = true;
        }
        for (int i = 2; i * i < n; i++) {
            if (!isPrime[i]) continue;
            for (int j = i * i; j < n; j += i) {
                isPrime[j] = false;
            }
        }
        int ans = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) ans++;
        }
        return ans;
    }

    /**
     * 3的幂
     * 给定一个整数，写一个函数来判断它是否是 3 的幂次方。如果是，返回 true ；否则，返回 false 。
     * <p>
     * 整数 n 是 3 的幂次方需满足：存在整数 x 使得 n == 3x
     * <p>
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnsdi2/
     */
    public static boolean isPowerOfThree(int n) {
        if (n < 1) {
            return false;
        }
        while (n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }

    public static boolean isPowerOfThree1(int n) {
        return (Math.log10(n) / Math.log10(3)) % 1 == 0;
    }

    public static boolean isPowerOfThree2(int n) {
        return (n > 0 && 1162261467 % n == 0);
    }


    /**
     * 罗马数字转整数
     * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * <p>
     * 例如，罗马数字2 写做 II ，即为两个并列的1。12 写做 XII ，即为 X + II 。27 写做  XXVII,即为 XX + V + II 。
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如4 不写做 IIII，而是 IV。数字1 在数字5 的左边，
     * 所表示的数等于大数5 减小数1 得到的数值4 。同样地，数字9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     * I 可以放在 V (5)和 X (10)的左边，来表示4 和9。
     * X 可以放在 L (50)和 C (100)的左边，来表示40 和 90。 
     * C 可以放在 D (500)和 M (1000)的左边，来表示 400 和 900。
     * 给定一个罗马数字，将其转换成整数。输入确保在1 到3999 的范围内。
     */
    public static int romanToInt(String s) {
        char[] chars = s.toCharArray();
        HashMap<Character, Integer> roman = new HashMap<>();
        roman.put('I', 1);
        roman.put('V', 5);
        roman.put('X', 10);
        roman.put('L', 50);
        roman.put('C', 100);
        roman.put('D', 500);
        roman.put('M', 1000);

        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (i == chars.length - 1) {
                sum += roman.get(c);
                continue;
            }
            char c1 = chars[i + 1];

            if (c == 'I' && (c1 == 'V' || c1 == 'X')) {
                sum = sum + roman.get(c1) - roman.get(c);
                ++i;
            } else if (c == 'X' && (c1 == 'L' || c1 == 'C')) {
                sum = sum + roman.get(c1) - roman.get(c);
                ++i;
            } else if (c == 'C' && (c1 == 'D' || c1 == 'M')) {
                sum = sum + roman.get(c1) - roman.get(c);
                ++i;
            } else {
                sum = sum + roman.get(c);
            }
        }
        return sum;
    }

    /**
     * MCMXCIV
     * M = 1000, CM = 900, XC = 90, IV = 4.
     * <p>
     * 1000 < 100 ?     res=1000
     * 100  < 1000 ?    res = 900
     * 1000 < 10 ?      res = 1900
     * 10 < 100 ?       res = 1890
     * 100 < 1 ？       res = 1990
     * 1 < 5 ?         res = 1989
     * 5               res = 1994
     */
    public int romanToInt1(String s) {
        char[] chars = s.toCharArray();
        int res = 0;

        int preNum = getValue(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            int curNum = getValue(chars[i]);
            if (preNum < curNum) {
                res -= preNum;
            } else {
                res += preNum;
            }
            preNum = curNum;
        }
        return res + preNum;
    }

    public int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

    /**
     * 位1的个数
     * 编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中数字位数为 '1' 的个数（也被称为汉明重量）。
     */
    // you need to treat n as an unsigned value
    public int hammingWeight(int i) {
        i = (i & 0x55555555) + ((i >> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
        i = (i & 0x0F0F0F0F) + ((i >> 4) & 0x0F0F0F0F);
        i = (i * (0x01010101) >> 24);
        return i;
    }

    // you need to treat n as an unsigned value
    public int hammingWeight1(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;
            n = n >>> 1;
        }
        return count;
    }

    public int hammingWeight2(int i) {
        return Integer.bitCount(i);
    }

    public static void main(String[] args) {
        int[] x = {2, 7, 9, 3, 1};

//        hammingWeight(8);


//        System.out.println(romanToInt("III"));
//        System.out.println(romanToInt("IV"));
//        System.out.println(romanToInt("IX"));
//        System.out.println(romanToInt("LVIII"));
//        System.out.println(romanToInt("MCMXCIV"));

//        System.out.println(isPowerOfThree(27));
//        System.out.println(isPowerOfThree(0));
//        System.out.println(isPowerOfThree(9));
//        System.out.println(isPowerOfThree(45));


//        System.out.println(countPrimes(499979));
//
//        System.out.println(fizzBuzz(15));

//        System.out.println(rob(x));
//        System.out.println(maxProfit1(x));

//        System.out.println(climbStairs(4));


//        int[] nums1 = {1, 2, 3, 0, 0, 0};
//        int[] nums2 = {2, 5, 6};
//
////        merge(nums1,nums1.length-nums2.length,nums2,nums2.length);
//
//        System.out.println(Arrays.toString(nums1));
//
//        ListNode head = new ListNode();
//        ListNode n1 = new ListNode();
//
//        head.val = 1;
//        head.next = n1;
//
//        n1.val = 2;
//
//        ListNode listNode = removeNthFromEnd(head, 2);
//        while (listNode != null) {
//            System.out.println(listNode.val);
//            listNode = listNode.next;
//        }


//        String[] strs = {"dog", "racecar", "car"};
//        System.out.println(longestCommonPrefix(strs));

//
//        System.out.println(say("1"));
//        System.out.println(say("11"));
//        System.out.println(say("21"));
//        System.out.println(say("1211"));
//        System.out.println(say("111221"));   // 31 22 11
//
//        System.out.println(countAndSay(1));
//        System.out.println(countAndSay(2));
//        System.out.println(countAndSay(3));
//        System.out.println(countAndSay(4));
//        System.out.println(countAndSay(5));

//        System.out.println(strStr("hello", "ll"));
//        System.out.println(strStr("aaaaa", "bba"));
//        System.out.println(strStr("", ""));

//        System.out.println((int)(-2147483648L));
//        System.out.println((-2147483648 -8)/10 );
//        int i = myAtoi("2147483648");
//        System.out.println(i);

//        boolean isPalindrome = isPalindrome(".,");
//        System.out.println(isPalindrome);


//        int loveleetcode = firstUniqChar("loveleetcode");
//        System.out.println(loveleetcode);
//        System.out.println(-4 % 10);
//        System.out.println(1 << 1);
//        System.out.println(1 << 2);
//        System.out.println(1 << 3);
//        System.out.println(0 << 1);
//        System.out.println(0 << 2);
//        System.out.println(0 << 3);
//
//        System.out.println(8 & 9);
//
//        int[] xx = {1, 2, 3,
//                4, 5, 3,
//                7, 8, 9};
//        int[] wz = new int[9];
//
//        int shift = 0;
//        for (int i : xx) {
//            shift = 1 << i;
//            System.out.println("shift: " + shift);
//
//            System.out.println("i&shift : " + (i & shift));
//
//            System.out.println("i|shift : " + (i | shift));
//
//            shift |= i;
//        }


//        char[][] xxx = {{'5', '3', '.', '.', '7', '.', '.', '.', '.'}
//                , {'6', '.', '.', '1', '9', '5', '.', '.', '.'}
//                , {'.', '9', '8', '.', '.', '.', '.', '6', '.'}
//                , {'8', '.', '.', '.', '6', '.', '.', '.', '3'}
//                , {'4', '.', '.', '8', '.', '3', '.', '.', '1'}
//                , {'7', '.', '.', '.', '2', '.', '.', '.', '6'}
//                , {'.', '6', '.', '.', '.', '.', '2', '8', '.'}
//                , {'.', '.', '.', '4', '1', '9', '.', '.', '5'}
//                , {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
//
//
//        char[][] yyy = {{'.', '.', '.', '.', '.', '.', '5', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'9', '3', '.', '.', '2', '.', '4', '.', '.'},
//                {'.', '.', '7', '.', '.', '.', '3', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '3', '4', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '3', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '5', '2', '.', '.'}};
//
//        System.out.println(isValidSudoku(yyy));


    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

/***
 * 打乱数组
 * 给你一个整数数组 nums ，设计算法来打乱一个没有重复元素的数组。
 *
 * 实现 Solution class:
 *
 * Solution(int[] nums) 使用整数数组 nums 初始化对象
 * int[] reset() 重设数组到它的初始状态并返回
 * int[] shuffle() 返回数组随机打乱后的结果
 *
 * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn6gq1/
 *
 * 输入
 * ["Solution", "shuffle", "reset", "shuffle"]
 * [[[1, 2, 3]], [], [], []]
 * 输出
 * [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
 *
 * 解释
 * Solution solution = new Solution([1, 2, 3]);
 * solution.shuffle();    // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。例如，返回 [3, 1, 2]
 * solution.reset();      // 重设数组到它的初始状态 [1, 2, 3] 。返回 [1, 2, 3]
 * solution.shuffle();    // 随机返回数组 [1, 2, 3] 打乱后的结果。例如，返回 [1, 3, 2]
 *
 */
class Solution {
    private int[] origin;
    private int[] shu;

    public Solution(int[] nums) {
        origin = nums;
        shu = Arrays.copyOf(origin, nums.length);
    }

    /**
     * Resets the array to its original configuration and return it.
     */
    public int[] reset() {
        return this.origin;
    }

    /**
     * Returns a random shuffling of the array.
     */
    public int[] shuffle() {
        Random random = new Random();
        int length = shu.length;
        for (int i = length - 1; i > 0; i--) {
            int pre = random.nextInt(i + 1);
            int temp = shu[pre];
            shu[pre] = shu[i];
            shu[i] = temp;
        }
        return shu;
    }


//    /**
//     * Returns a random shuffling of the array.
//     */
//    public int[] shuffle() {
//        int limit = nums.length;
//        Random random = new Random();
//        Set<Integer> result = new LinkedHashSet<>(limit);
//        while (result.size() < limit) {
//            result.add(randomEle(random, limit));
//        }
//        int[] res = new int[limit];
//        int i = 0;
//        for (int v : result) {
//            res[i++] = v;
//        }
//        return res;
//    }
//
//    public Integer randomEle(Random random, int limit) {
//        if (nums.length < limit) {
//            limit = nums.length;
//        }
//        return nums[random.nextInt(limit)];
//    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */

/**
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * <p>
 * push(x) —— 将元素 x 推入栈中。
 * pop() —— 删除栈顶的元素。
 * top() —— 获取栈顶元素。
 * getMin() —— 检索栈中的最小元素。
 * <p>
 * LinkList
 * : push 加入尾部
 * : pop 删除尾部
 * : top 尾部元素
 * ：getMin 记录最小元素
 * <p>
 * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnkq37/
 * <p>
 * 思路：
 * 1、借助java自带的小顶堆，记录最小值
 * 2、栈使用链表实现
 */
//class MinStack {
//    private LinkedList<Integer> list = new LinkedList<>();
//    private PriorityQueue<Integer> small = new PriorityQueue<>();
//
//    /**
//     * initialize your data structure here.
//     */
//    public MinStack() {
//
//    }
//
//    public void push(int val) {
//        list.offer(val);
//        small.offer(val);
//    }
//
//    public void pop() {
//        Integer last = list.removeLast();
//        small.remove(last);
//    }
//
//    public int top() {
//        return list.getLast();
//    }
//
//    public int getMin() {
//        return small.peek();
//    }
//}
class MinStack {
    private Stack<int[]> stack = new Stack<>();

    /**
     * initialize your data structure here.
     */
    public MinStack() {

    }

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new int[]{val, val});
        } else {
            stack.push(new int[]{val, Math.min(val, stack.peek()[1])});
        }
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek()[0];
    }

    public int getMin() {
        return stack.peek()[1];
    }
}
/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
