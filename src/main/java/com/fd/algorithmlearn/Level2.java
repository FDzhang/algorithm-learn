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

        System.out.println(lengthOfLongestSubstring1("tmmzuxt"));
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
