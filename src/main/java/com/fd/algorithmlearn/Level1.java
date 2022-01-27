package com.fd.algorithmlearn;

import com.fd.algorithmlearn.linked.ListNode;

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


    // -------------------------- 树 ------------------------------
    // 树
    // 树比链表稍微复杂，因为链表是线性数据结构，而树不是。 树的问题可以由 广度优先搜索 或 深度优先搜索 解决。
    // 在本章节中，我们提供了一个对于练习 广度优先遍历 很好的题目。
    // 我们推荐以下题目：二叉树的最大深度，验证二叉搜索树，二叉树的层次遍历 和 将有序数组转换为二叉搜索树。

    /**
     * 将有序数组转换为二叉搜索树
     * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
     * <p>
     * 高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
     * <p>
     * 思路：分治
     * 1、不停的取指定范围内的1/2的val作为当前节点
     * 2、当前节点.left  等于 左子范围的1/2处
     * 3、当前节点.right 等于 右子范围的1/2处
     * ps:范围[lo,hi], mid=(lo+hi)/2, 则左子范围=[lo,mid-1], 右子范围=[mid+1,hi]
     *
     * 思路2：bfs
     * 1、借助队列记录当前节点在左子范围，右子范围
     *
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBSTHelp(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBSTHelp(int[] nums, int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        int mid = lo + (hi - lo) / 2;
        TreeNode root = new TreeNode(nums[mid]);

        root.left = sortedArrayToBSTHelp(nums, lo, mid - 1);
        root.right = sortedArrayToBSTHelp(nums, mid + 1, hi);
        return root;
    }

    /**
     * 二叉树的层序遍历
     * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
     * <p>
     * 思路：队列 (先进先出)
     * 1、根节点入队，（第0层）
     * 2、当前层逐个出队，若节点的左右节点不为空，则左右节点入队，以此类推
     * <p>
     * 思路2：dfs
     * 1、借助level记录层数，先左后右进行递归
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.remove();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            res.add(level);
        }
        return res;
    }

    /**
     * 二叉树的层序遍历
     */
    public List<List<Integer>> levelOrder1(TreeNode root) {
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
     * 对称二叉树
     * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
     * <p>
     * 输入：root = [1,2,2,3,4,4,3]
     * 输出：true
     * <p>
     * 思路：(自顶向下)
     * 1、左右对称
     * a、左右都为空，则对称 （base case）
     * b、左右只有其中一个为空，则不对称
     * c、左右的val不相等，则不对称
     * d、下一层也需要左右对称：左的左和右的右对称 且 左的右和右的左对称，则对称
     */
    public boolean isSymmetric(TreeNode root) {
        return isSymmetricHelp(root.left, root.right);
    }

    private boolean isSymmetricHelp(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        return isSymmetricHelp(left.left, right.right) && isSymmetricHelp(left.right, right.left);
    }


    /**
     * 验证二叉搜索树
     * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
     * <p>
     * 有效 二叉搜索树定义如下：
     * <p>
     * 节点的左子树只包含 小于 当前节点的数。
     * 节点的右子树只包含 大于 当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * <p>
     * 思路：递归 (排除错误的情况，就是正确的情况)
     * 0、定义理解：当前节点符合二叉搜索树定义 且 左右子树符合二叉搜索树定义，则正确
     * 1、若当前节点是 左子节点，则需要小于最大值，反之则不是二叉搜索树
     * 2、若当前节点是 右子节点，则需要大于最小值，反之则不是二叉搜索树
     * 3、对于下一层而言，当前节点 是左子树的最大值，右子树的最小值
     * ps:  max!=null则为左子节点，min!=null则为右子节点
     * <p>
     * 思路2：中序遍历后有序
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    public boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) {
            return true;
        }

        if (max != null && root.val >= max.val) {
            return false;
        }
        if (min != null && root.val <= min.val) {
            return false;
        }
        return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
    }

    /**
     * 二叉树的最大深度
     * 给定一个二叉树，找出其最大深度。
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * 说明: 叶子节点是指没有子节点的节点。
     * <p>
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，
     * 返回它的最大深度 3 。
     * <p>
     * 思路：递归
     * 1、函数定义：返回max(左子树的深度，右子树的深度)+1
     * 2、定义base case：root=null,则返回0
     * 思路2：bfs
     * 思路3：dfs
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        return Math.max(left, right) + 1;
    }


    // -------------------------- 树 ------------------------------ end
    // -------------------------- 链表 ------------------------------
    // 链表
    // 链表问题相对容易掌握。 不要忘记 "双指针解法" ，它不仅适用于数组问题，而且还适用于链表问题。
    // 另一种大大简化链接列表问题的方法是 "Dummy node" 节点技巧 ，所谓 Dummy Node 其实就是带头节点的指针。
    // 我们推荐以下题目：反转链表，合并两个有序链表和链接环。
    // 更有额外的挑战，你可以尝试运用 递归 来解决这些问题：反转链表，回文链表和合并两个有序链表。

    /**
     * 环形链表
     * 给你一个链表的头节点 head ，判断链表中是否有环。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
     * 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。如果 pos 是 -1，
     * 则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     * 如果链表中存在环，则返回 true 。 否则，返回 false 。
     * <p>
     * 思路：快慢指针
     * 1、定义指针slow，fast
     * 2、循环，每次slow=slow.next, fast=fast.next.next
     * 3、若存在环，则slow和fast会相遇，若不存在环，则fast会先走到链尾
     * <p>
     * 思路2：使用集合
     * 思路3：逐个删除
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
     * 回文链表
     * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
     * <p>
     * 示例 1：
     * 输入：head = [1,2,2,1]
     * 输出：true
     * <p>
     * 1 2 2 1
     * s   f
     * 1 2 2 1 2
     * s   f
     * 思路1: 快慢指针
     * 1、快慢指针找到中点
     * 2、翻转后半部分链表, 定义left指向head，right指向翻转的后半部分链表
     * 3、left,right同时前进，不同则不是回文链表
     * <p>
     * 思路2：递归
     * 1、参考链表逆序打印 (可以用快慢指针的技巧加速 （2n->1.5n）)
     */
    private ListNode temp;

    public boolean isPalindrome1(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        temp = head;
        return check(slow);
    }

    private boolean check(ListNode head) {
        if (head == null) {
            return true;
        }
        boolean res = check(head.next) && (temp.val == head.val);
        temp = temp.next;
        return res;
    }

    // 非递归
    public boolean isPalindrome(ListNode head) {
        ListNode slow, fast, left;
        slow = fast = left = head;
        // 快慢指针，找到中点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
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

    // 翻转链表，返回新链表的头结点
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
     * 合并两个有序链表
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     * <p>
     * 思路1：
     * 1、循环遍历l1,l2进行拼接
     * 2、先确定要返回的head，再遍历两个链表进行拼接，再将剩下的链表直接接到新链表尾部
     * <p>
     * 思路2：递归
     * 1、参考循环拼接，确定base case，和拼接逻辑即可
     */
    // 递归
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val > l2.val) {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        } else {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }
    }

    // 循环
    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
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
     * 反转链表
     * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
     * <p>
     * 思路：指针
     * 1、使用pre，cur，next三个指针，pre在cur之前，next在cur之后
     * 2、遍历链表借助pre,cur,next逐个进行翻转
     * a、next=cur.next,cur.next=pre,pre=cur,cur=next
     * <p>
     * 思路2：递归
     * 1、使用pre，cur，next三个指针，pre在cur之前，next在cur之后
     * 2、递归进行翻转即可，当cur.next=null时，代表到达尾部，返回当前的cur
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

    // 递归
    public ListNode reverseList1(ListNode head) {
        if (head == null) {
            return null;
        }
        return reverseListHelp(null, head, head.next);
    }

    public ListNode reverseListHelp(ListNode pre, ListNode cur, ListNode next) {
        if (cur.next == null) {
            cur.next = pre;
            return cur;
        }

        cur.next = pre;
        return reverseListHelp(cur, next, next.next);
    }


    /**
     * 删除链表的倒数第N个节点
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     * <p>
     * 思路：前后指针
     * 1、先将指针fast向后移动n格 （slow指向头结点，slow和fast距离为n）
     * 2、再同时移动指针slow，fast。当fast到达链尾时，slow就是倒数第n+1个节点
     * 3、删除slow后面的节点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;

        // 1 2 3 4 5 null
        int i = 0;
        while (fast != null && i < n) {
            fast = fast.next;
            i++;
        }
        if (fast == null) {
            return head.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }

    /**
     * 删除链表中的节点
     * 请编写一个函数，用于 删除单链表中某个特定节点 。在设计函数时需要注意，你无法访问链表的头节点 head ，只能直接访问 要被删除的节点 。
     * <p>
     * 题目数据保证需要删除的节点 不是末尾节点 。
     * <p>
     * 思路：
     * 1、A->B->C，一般删除B需要给节点A，现在给的是节点B本身
     * 2、所以用C的数据覆盖B即可
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    // -------------------------- 链表 ------------------------------ end
    // -------------------------- 字符串 ------------------------------
    // 字符串
    // 字符串问题在面试中出现频率很高，你极有可能在面试中遇到。
    // 我们推荐以下题目：反转字符串，字符串中第一个唯一字符，字符串转整数（atoi）和 实现 strStr() 。

    /**
     * 最长公共前缀
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * 如果不存在公共前缀，返回空字符串 ""。
     * <p>
     * 示例 1：
     * 输入：strs = ["flower","flow","flight"]
     * 输出："fl"
     * <p>
     * 思路：
     * 1、找出长度最小的字符串 (也可以任取一个字符串)
     * 2、用长度最小的字符串 去与 每个字符匹配， 不成功则长度减一，继续匹配，直至成功或长度为0则停止
     */
    public String longestCommonPrefix(String[] strs) {
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

    // 写法2
    public static String longestCommonPrefix1(String[] strs) {
        String pre = strs[0];
        for (String str : strs) {
            while (!str.startsWith(pre)) {
                pre = pre.substring(0, pre.length() - 1);
                if ("".equals(pre)) {
                    return pre;
                }
            }
        }
        return pre;
    }


    /**
     * 外观数列
     * 给定一个正整数 n ，输出外观数列的第 n 项。
     * <p>
     * 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
     * <p>
     * 你可以将其视作是由递归公式定义的数字字符串序列：
     * <p>
     * countAndSay(1) = "1"
     * countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
     * 前五项如下：
     * <p>
     * 1.     1
     * 2.     11
     * 3.     21
     * 4.     1211
     * 5.     111221
     * 第一项是数字 1
     * 描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
     * 描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
     * 描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
     * 描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
     * 要 描述 一个数字字符串，首先要将字符串分割为 最小 数量的组，每个组都由连续的最多 相同字符 组成。然后对于每个组，
     * 先描述字符的数量，然后描述字符，形成一个描述组。要将描述转换为数字字符串，先将每组中的字符数量用数字替换，再将所有描述组连接起来。
     * <p>
     * 思路：
     * 1、初始化外观数列的第一项 "1"
     * 2、以第一项为基础 循环处理外观数列n次,规则如题所述
     * a、遍历外观字符串s, 记录连续字符的次数cnt和字符本身c，拼接cnt、c，到结果集
     */
    public String countAndSay(int n) {
        String str = "1";
        if (n == 1) {
            return str;
        }
        for (int i = 1; i < n; i++) {
            str = say(str);
        }
        return str;
    }

    private String say(String s) {
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
     * 实现 strStr()
     * 实现 strStr() 函数。
     * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。
     * 如果不存在，则返回  -1 。
     * 说明：
     * 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
     * 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与 C 语言的 strstr() 以及 Java 的 indexOf() 定义相符。
     * <p>
     * 示例 1：
     * 输入：haystack = "hello", needle = "ll"
     * 输出：2
     * <p>
     * 思路1：(工作中使用)
     * 1、借用语言自带的api (eg: java indexOf)
     * <p>
     * 思路2：双指针
     * 1、指针i,j分别指向haystack，needle
     * 2、遍历haystack，
     * a、若h[i]==n[j],逐个比较needle的其它字符，
     * b、若匹配不成功，则将i,j位置复原，再将i++, 匹配成功则返回i-j
     * 3、匹配不到返回-1,
     * <p>
     * 思路3：kmp from [如何更好地理解和掌握 KMP 算法? - 知乎](https://www.zhihu.com/question/21923021/answer/281346746)
     * 1、理解 部分匹配表(Partial Match Table)的数组 的含义 -> 获取next数组
     * 2、遍历 haystack 并借助next数组，进行匹配
     * ps： 时间复杂度O（m+n）
     */
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        char[] hc = haystack.toCharArray();
        char[] nc = needle.toCharArray();

        int[] next = new int[nc.length];
        getNext(nc, next);

        int i = 0;
        int j = 0;
        while (i < hc.length && j < nc.length) {
            if (j == -1 || hc[i] == nc[j]) {
                ++i;
                ++j;
            } else {
                j = next[j];
            }
        }
        return j == nc.length ? i - j : -1;
    }

    public void getNext(char[] cs, int[] next) {
        int i = 0;
        int j = -1;
        next[0] = -1;

        while (i < cs.length - 1) {
            if (j == -1 || cs[i] == cs[j]) {
                ++i;
                ++j;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
    }


    public int strStr2(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        char[] hc = haystack.toCharArray();
        char[] nc = needle.toCharArray();

        int i = 0;
        int j = 0;
        while (i < hc.length && j < nc.length) {
            int temp = i;
            while (i < hc.length && j < nc.length && hc[i] == nc[j]) {
                i++;
                j++;
            }
            if (j == nc.length) {
                return temp;
            }
            i = temp + 1;
            j = 0;
        }

        return -1;
    }

    public int strStr1(String haystack, String needle) {
        int i = -1;
        if (haystack != null) {
            i = haystack.indexOf(needle);
        }
        return i;
    }

    /**
     * 字符串转换整数 (atoi)
     * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
     * <p>
     * 函数 myAtoi(string s) 的算法如下：
     * <p>
     * 读入字符串并丢弃无用的前导空格
     * 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
     * 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
     * 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
     * 如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231 − 1 的整数应该被固定为 231 − 1 。
     * 返回整数作为最终结果。
     * <p>
     * 思路：
     * 1、预处理去掉前导空格转为char[]数组cs，然后处理第一个字符
     * 2、若为数字，res+=数字; 若为负号，则记录符号；若为正号，则继续。
     * 3、遍历cs,若不是数字返回res, 反之res*10+=数字（注意越界判断）
     * ps:越界判断：令 long tmp = res; 若tmp!=(int)tmp,则越界
     * ps:越界判断2：令 int tmp = res*10 + x; 若(tmp-x)/10!=res,则越界 (无法处理数字：2147483648)
     */
    public int myAtoi(String s) {
        if (s == null || s.trim().length() == 0) {
            return 0;
        }
        char[] cs = s.trim().toCharArray();

        int res = 0;
        int fh = 1;
        if (checkNum(cs[0])) {
            res += (cs[0] - '0');
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

    public boolean checkNum(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 验证回文串
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     * <p>
     * 说明：本题中，我们将空字符串定义为有效的回文串。
     * <p>
     * 思路1：双指针
     * 1、将字符串s转成小写的char[]数组cs
     * 2、指针i，j分别指向cs的头尾。
     * 3、处理i,j 若cs[i]不是字母或数字, 则i++; j同理
     * 4、若cs[i]!=cs[j],则返回false
     */
    public boolean isPalindrome(String s) {
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


    public boolean check(char c) {
        return (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }

    /**
     * 有效的字母异位词
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
     * <p>
     * 思路1：数组计数
     * 1、创建int[26] record
     * 2、遍历字符串s，对应字符的计数+1，遍历字符串t，对应字符的计数-1
     * 3、遍历record，若record[i]!=0, 则返回false
     * <p>
     * 思路2：排序
     * 1、将字符串转成char[]数组
     * 2、将两个char[]数组排序
     * 3、若排序后的两个char[]数组相等，则返回true
     */
    public boolean isAnagram(String s, String t) {
        int[] record = new int[26];
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();

        for (char c : cs) {
            record[c - 'a']++;
        }
        for (char c : ct) {
            record[c - 'a']--;
        }
        for (int i : record) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnagram1(String s, String t) {
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();

        Arrays.sort(cs);
        Arrays.sort(ct);

        return Arrays.equals(cs, ct);
    }

    /**
     * 字符串中的第一个唯一字符
     * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
     * <p>
     * 示例：
     * <p>
     * s = "leetcode"
     * 返回 0
     * <p>
     * s = "loveleetcode"
     * 返回 2
     * <p>
     * 思路：数组 计数
     * 1、创建一个 int[26] 数组 zm
     * 2、遍历字符串s, 令zm[c-'a']++ (即记录字母出现的次数)
     * 3、再次遍历字符串s，若zm[cs[i]-'a']==1, 则返回位置i
     * 4、不存在，返回-1
     */
    public static int firstUniqChar(String s) {
        char[] cs = s.toCharArray();
        int[] zm = new int[26];
        for (char c : cs) {
            zm[c - 'a']++;
        }
        for (int i = 0; i < cs.length; i++) {
            if (zm[cs[i] - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 整数反转
     * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
     * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
     * 假设环境不允许存储 64 位整数（有符号或无符号）。
     * <p>
     * 输入：x = 123
     * 输出：321
     * <p>
     * 思路1：
     * 1、从后到前，逐位反转 (不断的 %10 和 /10)
     * 2、判断溢出：令b=a*10+c，若 (b-c)/10!=a，则溢出
     */
    public int reverse(int x) {
        int res = 0;
        while (x != 0) {
            int m = x % 10;
            int newRes = res * 10 + m;
            if ((newRes - m) / 10 != res) {
                return 0;
            }
            res = newRes;
            x = x / 10;
        }
        return res;
    }

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
