package com.fd.algorithmlearn;

import com.fd.algorithmlearn.linked.ListNode;

import java.util.*;

/**
 * @author zhangxinqiang
 * @create 2021/5/14 14:16
 */
public class Temp {
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


    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        return false;
    }

    public int singleNumber(int[] nums) {
        int res = 0;
        for (int num : nums) {
            res ^= num;
        }
        return res;
    }

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

    public void reverseString(char[] s) {
        for (int i = 0; i < s.length / 2; i++) {
            char temp = s[i];
            s[i] = s[s.length - 1 - i];
            s[s.length - 1 - i] = temp;
        }
    }

    public int reverse(int x) {
        long res = 0;
        while (x != 0) {
            res = res * 10 + x % 10;
            x /= 10;
        }
        return res == (int) res ? (int) res : 0;
    }

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
     * 1 忽略空白字符
     * 2 第一个字符是否 是 '-' or '+' or 数字
     * 3 接下来的 是否是 数字
     * 4 数字 是否是 int 在范围内
     *
     * @param s
     * @return
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

    public static int strStr(String haystack, String needle) {
        int i = -1;
        if (haystack != null) {
            i = haystack.indexOf(needle);
        }
        return i;
    }

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


    public static void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

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

    public static void main(String[] args) {
        ListNode head = new ListNode();
        ListNode n1 = new ListNode();

        head.val = 1;
        head.next = n1;

        n1.val = 2;

        ListNode listNode = removeNthFromEnd(head, 2);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }


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
