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
     *
     * eg: [-10,-3,0,5,9] 5
     *
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
        root.left = sortedArrayToBST(nums, left, index-1);
        root.right = sortedArrayToBST(nums, index + 1, right);
        return root;
    }

    public static void main(String[] args) {


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
