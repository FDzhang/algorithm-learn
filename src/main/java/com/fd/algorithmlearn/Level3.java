package com.fd.algorithmlearn;

import com.fd.algorithmlearn.linked.ListNode;

import java.util.*;

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
        return afterStr.toArray(String[]::new);
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
