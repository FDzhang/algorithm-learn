package com.fd.algorithmlearn;

import com.fd.algorithmlearn.linked.ListNode;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

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

        System.out.println(lengthOfLongestSubstring1("tmmzuxt"));
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
