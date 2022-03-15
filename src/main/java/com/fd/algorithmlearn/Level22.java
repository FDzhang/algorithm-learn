package com.fd.algorithmlearn;

import com.fd.algorithmlearn.entity.Node;
import com.fd.algorithmlearn.entity.TreeNode;
import com.fd.algorithmlearn.linked.ListNode;

import java.util.*;

/**
 * 中级算法
 * LeetCode 官方推出的经典面试题目清单 —— 「中级算法 - 巩固训练」
 * <p>
 * https://leetcode-cn.com/leetbook/detail/top-interview-questions-medium/
 *
 * @author zhangxinqiang
 * @create 2022/2/24 11:18
 */
public class Level22 {

    // -------------------------- 树和图 ------------------------------
    // 树和图
    // 树是图的简单表示形式，所以我们常用的两种图的遍历方法同样适用于树。
    //
    // 我们推荐以下题目：中序遍历二叉树，每个节点的右向指针 和 岛屿的个数。
    //
    // 请注意，很多树的问题会以 N 叉树 的形式出现，所以请确保你了解什么是 N 叉树 。
    //
    // 说明：岛屿的个数这道题并不是一个树的问题，它可以用图的形式呈现，因此我们将它归类为图的问题。
    //

    /**
     * 岛屿数量
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * <p>
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     * <p>
     * 此外，你可以假设该网格的四条边均被水包围。
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入：grid = [
     * ["1","1","1","1","0"],
     * ["1","1","0","1","0"],
     * ["1","1","0","0","0"],
     * ["0","0","0","0","0"]
     * ]
     * 输出：1
     * 示例 2：
     * <p>
     * 输入：grid = [
     * ["1","1","0","0","0"],
     * ["1","1","0","0","0"],
     * ["0","0","1","0","0"],
     * ["0","0","0","1","1"]
     * ]
     * 输出：3
     * <p>
     * 提示：
     * <p>
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 300
     * grid[i][j] 的值为 '0' 或 '1'
     * 相关标签 深度优先搜索 广度优先搜索 并查集 数组 矩阵
     * <p>
     * 思路：dfs
     * 1、路径：走过的点 （grid）
     * 2、选择列表：上下左右
     * 3、结束条件：遇到了 '0' or 越界
     * ps：若不希望改变grid[][]，声明一个tag[][],进行标记即可
     *
     * 思路2：bfs
     * 思路3：并查集
     */
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != '0') {
                    numIslandsDfs(grid, i, j);
                    res++;
                }
            }
        }
        return res;
    }

    private void numIslandsDfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        numIslandsDfs(grid, i + 1, j);
        numIslandsDfs(grid, i, j + 1);
        numIslandsDfs(grid, i - 1, j);
        numIslandsDfs(grid, i, j - 1);
    }

    public int numIslands1(char[][] grid) {
        boolean[][] tags = new boolean[grid.length][grid[0].length];

        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && !tags[i][j]) {
                    landsBackTrack(grid, i, j, tags);
                    res++;
                }
            }
        }

        return res;
    }

    private void landsBackTrack(char[][] grid, int p, int q, boolean[][] tags) {
        if (p >= grid.length || p < 0 || q >= grid[0].length || q < 0) {
            return;
        }
        if (tags[p][q] || grid[p][q] == '0') {
            return;
        }

        tags[p][q] = true;
        landsBackTrack(grid, p + 1, q, tags);
        landsBackTrack(grid, p - 1, q, tags);
        landsBackTrack(grid, p, q + 1, tags);
        landsBackTrack(grid, p, q - 1, tags);
    }

    /**
     * 二叉搜索树中第K小的元素
     * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。
     * <p>
     * 示例 1：
     * <p>
     * 输入：root = [3,1,4,null,2], k = 1
     * 输出：1
     * 示例 2：
     * <p>
     * 输入：root = [5,3,6,2,4,null,null,1], k = 3
     * 输出：3
     * <p>
     * 提示：
     * <p>
     * 树中的节点数为 n 。
     * 1 <= k <= n <= 104
     * 0 <= Node.val <= 104
     *  
     * <p>
     * 进阶：如果二叉搜索树经常被修改（插入/删除操作）并且你需要频繁地查找第 k 小的值，你将如何优化算法？
     * <p>
     * 相关标签 树 深度优先搜索 二叉搜索树 二叉树
     * <p>
     * 思路：中序遍历
     * 1、二叉搜索树的中序遍历，是一个从小到大的有序序列
     * ps:为何用int[]? 值传递是复制传递
     * 思路2：统计节点个数
     */
    public int kthSmallest(TreeNode root, int k) {
        int[] res = new int[]{k, -1};
        kthHelp(root, res);
        return res[1];
    }

    public void kthHelp(TreeNode root, int[] res) {
        if (root == null) {
            return;
        }
        kthHelp(root.left, res);
        res[0]--;
        if (res[0] == 0) {
            res[1] = root.val;
            return;
        }
        kthHelp(root.right, res);
    }

    private int rank = 0;
    private int val;

    public int kthSmallest1(TreeNode root, int k) {
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
     * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
     * <p>
     * struct Node {
     * int val;
     * Node *left;
     * Node *right;
     * Node *next;
     * }
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     * <p>
     * 初始状态下，所有 next 指针都被设置为 NULL。
     * <p>
     * 示例 1：
     * <p>
     * 输入：root = [1,2,3,4,5,6,7]
     * 输出：[1,#,2,3,#,4,5,6,7,#]
     * 解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化的输出按层序遍历排列，同一层节点由 next 指针连接，'#' 标志着每一层的结束。
     * 示例 2:
     * <p>
     * 输入：root = []
     * 输出：[]
     * <p>
     * 提示：
     * <p>
     * 树中节点的数量在 [0, 212 - 1] 范围内
     * -1000 <= node.val <= 1000
     * <p>
     * 进阶：
     * 你只能使用常量级额外空间。
     * 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
     * 相关标签 树 深度优先搜索 广度优先搜索 链表 二叉树
     *
     * 思路：bfs
     * 1、二叉树的层序遍历
     * 思路2：递归
     * 1、链接节点的左右子节点, 在递归的链接 左节点的左右子节点， 右节点的左右子节点， 左节点的右节点和右节点的子节点
     * 思路3：dfs
     *
     */
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        connectHelp(root.left, root.right);
        return root;
    }

    private void connectHelp(Node left, Node right) {
        if (left == null || right == null) {
            return;
        }
        left.next = right;

        connectHelp(left.left, left.right);
        connectHelp(right.left, right.right);
        connectHelp(left.right, right.left);
    }

    public Node connect1(Node root) {
        connectDfs(root, null);
        return root;
    }

    public void connectDfs(Node curr, Node next) {
        if (curr == null) {
            return;
        }
        curr.next = next;
        connectDfs(curr.left, curr.right);
        connectDfs(curr.right, curr.next == null ? null : curr.next.left);
    }

    // 写法2
    public Node connect2(Node root) {
        if (null == root || root.left == null) {
            return root;
        }

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
     * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
     * <p>
     * 示例 1:
     * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * 输出: [3,9,20,null,null,15,7]
     * 示例 2:
     * 输入: preorder = [-1], inorder = [-1]
     * 输出: [-1]
     * <p>
     * 提示:
     * <p>
     * 1 <= preorder.length <= 3000
     * inorder.length == preorder.length
     * -3000 <= preorder[i], inorder[i] <= 3000
     * preorder 和 inorder 均 无重复 元素
     * inorder 均出现在 preorder
     * preorder 保证 为二叉树的前序遍历序列
     * inorder 保证 为二叉树的中序遍历序列
     * <p>
     * 相关标签 树 数组 哈希表 分治 二叉树
     * <p>
     * <p>
     * 思路：分治
     * 1、辅助函数  build(pre, lo1, hi1, in, lo2, hi2);  pre: 前序遍历数组， in: 后序遍历的数组
     * 2、根据 pre的第一个值构造根节点 root， 根据root的val，在in的lo2~hi2中，找到rootVal的位置
     * 3、递归调用构建左右子树, 分别传递左右子树的 前序、中序遍历范围，
     * <p>
     * 优化点：在递归中，每次都需要部分遍历 inorder 数组，来找到 rootVal 的位置 mid
     * 思路： (空间换时间，O(1)时间复杂度获取 mid)
     * 构建一个hashmap，放入 key=inorder[i], val=i。这样 mid = map.get(rootVal)。
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
        root.left = build(preorder, lo1 + 1, lo1 + leftSize, inorder, lo2, mid - 1, map);
        root.right = build(preorder, lo1 + leftSize + 1, hi1, inorder, mid + 1, hi2, map);
        return root;
    }

    public TreeNode buildTree1(int[] preorder, int[] inorder) {
        return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    public TreeNode build(int[] preorder, int lo1, int hi1, int[] inorder, int lo2, int hi2) {
        if (lo1 > hi1 || lo2 > hi2) {
            return null;
        }
        int rootVal = preorder[lo1];
        int mid = lo2;
        for (int i = lo2; i <= hi2; i++) {
            if (inorder[i] == rootVal) {
                mid = i;
                break;
            }
        }
        int leftSize = mid - lo2;

        TreeNode root = new TreeNode(rootVal);
        root.left = build(preorder, lo1 + 1, lo1 + leftSize,
                inorder, lo2, mid - 1);
        root.right = build(preorder, lo1 + leftSize + 1, hi1,
                inorder, mid + 1, hi2);
        return root;
    }

    /**
     * 二叉树的锯齿形层次遍历
     * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     * <p>
     * <p>
     * 示例 1：
     * 输入：root = [3,9,20,null,null,15,7]
     * 输出：[[3],[20,9],[15,7]]
     * <p>
     * 提示：
     * 树中节点数目在范围 [0, 2000] 内
     * -100 <= Node.val <= 100
     * 相关标签 树 广度优先搜索 二叉树
     * <p>
     * 思路1：迭代
     * 1、二叉树的层序遍历
     * 2、如果是偶数层，将遍历的结果翻转 （设 root 为第一层）
     * <p>
     * 思路2：递归
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        int r = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            LinkedList<TreeNode> nextLevel = new LinkedList<>();
            List<Integer> list = new ArrayList<>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node == null) {
                    continue;
                }
                list.add(node.val);
                nextLevel.offer(node.left);
                nextLevel.offer(node.right);
            }
            queue = nextLevel;
            if (r == 1) {
                Collections.reverse(list);
            }
            if (!list.isEmpty()) {
                res.add(list);
            }
            r = r == 0 ? 1 : 0;
        }
        return res;
    }

    public List<List<Integer>> zigzagLevelOrder1(TreeNode root) {
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

    // 递归
    public List<List<Integer>> zigzagLevelOrder3(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        traverse(root, 0, res);
        return res;
    }

    private void traverse(TreeNode root, int depth, List<List<Integer>> res) {
        if (root == null) return;
        if (depth >= res.size()) {
            res.add(new ArrayList<>());
        }
        if (depth % 2 == 0) {
            res.get(depth).add(root.val);
        } else {
            res.get(depth).add(0, root.val);
        }
        traverse(root.left, depth + 1, res);
        traverse(root.right, depth + 1, res);
    }

    /**
     * 二叉树的中序遍历
     * 给定一个二叉树的根节点 root ，返回它的 中序 遍历。
     * <p>
     * 示例 1：
     * <p>
     * 输入：root = [1,null,2,3]
     * 输出：[1,3,2]
     * <p>
     * 提示：
     * <p>
     * 树中节点数目在范围 [0, 100] 内
     * -100 <= Node.val <= 100
     *  
     * <p>
     * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
     * <p>
     * 相关标签 栈 树 深度优先搜索 二叉树
     * <p>
     * 思路1：递归
     * 1、中序遍历：左，根，右
     * <p>
     * 思路2：迭代
     * 1、借用栈：先进后出
     * <p>
     * 思路3：颜色标记 [颜色标记法-一种通用且简明的树遍历方法 - 二叉树的中序遍历 - 力扣（LeetCode）](https://leetcode-cn.com/problems/binary-tree-inorder-traversal/solution/yan-se-biao-ji-fa-yi-chong-tong-yong-qie-jian-ming/)
     * 其核心思想如下：
     * 1、使用颜色标记节点的状态，新节点为白色，已访问的节点为灰色。
     * 2、如果遇到的节点为白色，则将其标记为灰色，然后将其右子节点、自身、左子节点依次入栈。
     * 3、如果遇到的节点为灰色，则将节点的值输出。
     */
    class ColorNode {
        public ColorNode(Integer color, TreeNode node) {
            this.color = color;
            this.node = node;
        }

        public Integer color;
        public TreeNode node;
    }

    public List<Integer> inorderTraversal2(TreeNode root) {
        int white = 0;
        int gray = 1;
        List<Integer> res = new ArrayList<>();
        Stack<ColorNode> stack = new Stack<>();
        stack.push(new ColorNode(white, root));
        while (!stack.isEmpty()) {
            ColorNode top = stack.pop();
            if (top.node == null) {
                continue;
            }
            if (top.color == white) {
                stack.push(new ColorNode(white, top.node.right));
                stack.push(new ColorNode(gray, top.node));
                stack.push(new ColorNode(white, top.node.left));
            } else {
                res.add(top.node.val);
            }
        }
        return res;
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        inorderTraversal(root, res);
        return res;
    }

    public void inorderTraversal(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        inorderTraversal(root.left, res);
        res.add(root.val);
        inorderTraversal(root.right, res);
    }


    public List<Integer> inorderTraversal1(TreeNode root) {
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


    // -------------------------- 树和图 ------------------------------ end

    // -------------------------- 链表 ------------------------------ end
    // 链表
    // 链表问题相对容易掌握。 不要忘记 "双指针解法" ，它不仅适用于数组问题，而且还适用于链表问题。
    // 另一种大大简化链接列表问题的方法是 "Dummy node" 节点技巧 ，所谓 Dummy Node 其实就是带头节点的指针。
    // 我们推荐以下题目：两数相加，相交链表。

    /**
     * 相交链表
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
     * <p>
     * 图示两个链表在节点 c1 开始相交：
     * <p>
     * 题目数据 保证 整个链式结构中不存在环。
     * <p>
     * 注意，函数返回结果后，链表必须 保持其原始结构 。
     * <p>
     * 自定义评测：
     * <p>
     * 评测系统 的输入如下（你设计的程序 不适用 此输入）：
     * <p>
     * intersectVal - 相交的起始节点的值。如果不存在相交节点，这一值为 0
     * listA - 第一个链表
     * listB - 第二个链表
     * skipA - 在 listA 中（从头节点开始）跳到交叉节点的节点数
     * skipB - 在 listB 中（从头节点开始）跳到交叉节点的节点数
     * 评测系统将根据这些输入创建链式数据结构，并将两个头节点 headA 和 headB 传递给你的程序。如果程序能够正确返回相交节点，那么你的解决方案将被 视作正确答案 。
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * <p>
     * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
     * 输出：Intersected at '8'
     * 解释：相交节点的值为 8 （注意，如果两个链表相交则不能为 0）。
     * 从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,6,1,8,4,5]。
     * 在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
     * <p>
     * <p>
     * 提示：
     * <p>
     * listA 中节点数目为 m
     * listB 中节点数目为 n
     * 1 <= m, n <= 3 * 104
     * 1 <= Node.val <= 105
     * 0 <= skipA <= m
     * 0 <= skipB <= n
     * 如果 listA 和 listB 没有交点，intersectVal 为 0
     * 如果 listA 和 listB 有交点，intersectVal == listA[skipA] == listB[skipB]
     *
     * <p>
     * 进阶：你能否设计一个时间复杂度 O(m + n) 、仅用 O(1) 内存的解决方案？
     * <p>
     * 相关标签 哈希表 链表 双指针
     * <p>
     * 思路1： 哈希
     * 1、遍历HeadA，存入哈希
     * 2、遍历HeadB, 判断是否在哈希中存在，存在则返回当前节点
     * 3、返回null
     * <p>
     * --  ---
     * --
     * ---  --
     * <p>
     * 思路2：双指针
     * 1、长 + 短 = 短 + 长
     * ps: (开始时间相同、速度相同、距离相同，则到达终点的时间相同)
     * <p>
     * <p>
     * ps: 跑两遍，
     */
    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
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

    // 可行 8ms
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        HashSet<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }
        while (headB != null) {
            if (set.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }


    /**
     * 奇偶链表
     * 给定单链表的头节点 head ，将所有索引为奇数的节点和索引为偶数的节点分别组合在一起，然后返回重新排序的列表。
     * <p>
     * 第一个节点的索引被认为是 奇数 ， 第二个节点的索引为 偶数 ，以此类推。
     * 请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。
     * 你必须在 O(1) 的额外空间复杂度和 O(n) 的时间复杂度下解决这个问题。
     * <p>
     * 提示:
     * n ==  链表中的节点数
     * 0 <= n <= 104
     * -106 <= Node.val <= 106
     * <p>
     * 相关标签 链表
     * <p>
     * 思路：链表
     * 1、 两个一组往后遍历，奇数节点接在odd， 偶数节点接在even
     * ps: 若使用head往后移动，分奇偶接在两个dummy上，需要注意.next需要断开，避免环形链表
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

    public ListNode oddEvenList1(ListNode head) {
        ListNode a = new ListNode();
        ListNode b = new ListNode();
        ListNode c = a;
        ListNode d = b;
        while (head != null) {
            c.next = head;
            c = c.next;
            head = head.next;
            c.next = null;
            if (head != null) {
                d.next = head;
                d = d.next;
                head = head.next;
                d.next = null;
            }
        }
        c.next = b.next;
        return a.next;
    }


    /**
     * 两数相加
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * <p>
     * 示例：
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     * <p>
     * 提示：
     * 每个链表中的节点数在范围 [1, 100] 内
     * 0 <= Node.val <= 9
     * 题目数据保证列表表示的数字不含前导零
     * <p>
     * 相关标签 递归 链表 数学
     * <p>
     * 思路1：循环
     * 思路2：递归 (更简洁)
     * 1、l1的节点，l2的节点，进位标识 t；
     * 2、l1不为空 t += l1.val、 l1=l1.next, l2不为空 t += l2.val、 l2=l2.next;
     * 3、t%10作为新节点的值， t/10作为新节点的 t;
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode cur = head;
        int x = 0;
        while (x > 0 || l1 != null || l2 != null) {
            if (l1 != null) {
                x += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                x += l2.val;
                l2 = l2.next;
            }

            ListNode next = new ListNode(x % 10);
            cur.next = next;
            cur = next;
            x = x / 10;
        }
        return head.next;
    }

    public ListNode addTwoNumbers3(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode cur = head;
        int x = 0;
        while (l1 != null && l2 != null) {
            ListNode next = new ListNode();
            int val = l1.val + l2.val;
            if (x > 0) {
                val += x;
            }
            next.val = val % 10;
            x = val / 10;

            cur.next = next;
            cur = next;

            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null) {
            ListNode next = new ListNode();
            int val = l1.val;
            if (x > 0) {
                val += x;
            }
            next.val = val % 10;
            x = val / 10;

            cur.next = next;
            cur = next;

            l1 = l1.next;
        }

        while (l2 != null) {
            ListNode next = new ListNode();
            int val = l2.val;
            if (x > 0) {
                val += x;
            }
            next.val = val % 10;
            x = val / 10;

            cur.next = next;
            cur = next;

            l2 = l2.next;
        }

        if (x > 0) {
            ListNode next = new ListNode();
            next.val = x;
            cur.next = next;
        }
        return head.next;
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

    // -------------------------- 链表 ------------------------------ end
    // -------------------------- 数组和字符串 ------------------------------

    // 数组和字符串
    // 数组和字符串问题在面试中出现频率很高，你极有可能在面试中遇到。
    //
    // 我们推荐以下题目：字母异位词分组，无重复字符的最长子串 和 最长回文子串。

    /**
     * 递增的三元子序列
     * 给你一个整数数组 nums ，判断这个数组中是否存在长度为 3 的递增子序列。
     * <p>
     * 如果存在这样的三元组下标 (i, j, k) 且满足 i < j < k ，使得 nums[i] < nums[j] < nums[k] ，返回 true ；否则，返回 false 。
     * <p>
     * 进阶：你能实现时间复杂度为 O(n) ，空间复杂度为 O(1) 的解决方案吗？
     * <p>
     * 示例 1：
     * 输入：nums = [1,2,3,4,5]
     * 输出：true
     * 解释：任何 i < j < k 的三元组都满足题意
     * <p>
     * 相关标签 贪心 数组
     * <p>
     * 思路1： 贪心
     * 1、遍历数组，记录两个值 a,b (a最小值， a<b)
     * 2、若x<=a, 则a=x, 若 a<x<=b, 则b=x
     * 3、若出现一个值大于b，则返回true
     */
    public boolean increasingTriplet(int[] nums) {
        int a = Integer.MAX_VALUE;
        int b = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= a) {
                a = nums[i];
            } else if (nums[i] <= b) {
                b = nums[i];
            } else {
                return true;
            }
        }
        return false;
    }

    // version1 贪心
    public boolean increasingTriplet1(int[] nums) {
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
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     * <p>
     * 提示：
     * 1 <= s.length <= 1000
     * s 仅由数字和英文字母组成
     * <p>
     * 相关标签 字符串 动态规划
     * <p>
     * 思路1：
     * 1、遍历 字符串s, 每次都都向两边搜索 (定义max[3]{回文串的长度len，回文串的左下标i，回文串的右下标j})
     * a、搜索长度为奇数的最长回文串，记录 t1 {len1, i, j}
     * b、搜索长度为偶数的最长回文串，记为 t2 {len2, i, j}
     * c、依据t1[0] 和 t2[0]，令 max 等于 t1 or t2
     * 2、返回 s.substring(max[1], max[2] + 1);
     * <p>
     * 思路2：动态规划
     * 思路3：马拉车
     */
    // 中心扩散 version 2
    public String longestPalindrome(String s) {
        char[] cs = s.toCharArray();

        int[] max = new int[3];
        for (int k = 1; k < cs.length; k++) {
            int[] t1 = longestPalindrome(cs, k - 1, k);
            int[] t2 = longestPalindrome(cs, k - 1, k + 1);

            if (t1[0] > max[0]) {
                max = t1;
            }
            if (t2[0] > max[0]) {
                max = t2;
            }
        }
        return s.substring(max[1], max[2] + 1);
    }

    public int[] longestPalindrome(char[] cs, int i, int j) {
        int[] res = new int[3];
        while (i >= 0 && i < j && j < cs.length) {
            if (cs[i] != cs[j]) {
                break;
            }
            res[1] = i;
            res[2] = j;
            res[0] = j - i + 1;
            i--;
            j++;
        }
        return res;
    }

    // 动态规划
    public String longestPalindrome2(String s) {
        // 中心扩散的方法，其实做了很多重复计算。动态规划就是为了减少重复计算的问题。动态规划听起来很高大上。
        // 其实说白了就是空间换时间，将计算结果暂存起来，避免重复计算。作用和工程中用 redis 做缓存有异曲同工之妙。
        // 我们用一个 boolean dp[l][r] 表示字符串从 i 到 j 这段是否为回文。试想如果 dp[l][r]=true，
        // 我们要判断 dp[l-1][r+1] 是否为回文。只需要判断字符串在(l-1)和（r+1)两个位置是否为相同的字符，是不是减少了很多重复计算。
        // 进入正题，动态规划关键是找到初始状态和状态转移方程。
        // 初始状态，l=r 时，此时 dp[l][r]=true。
        // 状态转移方程，dp[l][r]=true 并且(l-1)和（r+1)两个位置为相同的字符，此时 dp[l-1][r+1]=true。
        if (s == null || s.length() < 2) {
            return s;
        }
        int strLen = s.length();
        int maxStart = 0;  //最长回文串的起点
        int maxEnd = 0;    //最长回文串的终点
        int maxLen = 1;  //最长回文串的长度
        boolean[][] dp = new boolean[strLen][strLen];
        for (int r = 1; r < strLen; r++) {
            for (int l = 0; l < r; l++) {
                if (s.charAt(l) == s.charAt(r) && (r - l <= 2 || dp[l + 1][r - 1])) {
                    dp[l][r] = true;
                    if (r - l + 1 > maxLen) {
                        maxLen = r - l + 1;
                        maxStart = l;
                        maxEnd = r;
                    }
                }
            }
        }
        return s.substring(maxStart, maxEnd + 1);
    }

    // 中心扩散 version 1
    public String longestPalindrome1(String s) {
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
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     * <p>
     * "abba"
     * <p>
     * 提示：
     * 0 <= s.length <= 5 * 104
     * s 由英文字母、数字、符号和空格组成
     * <p>
     * 相关标签 哈希表 字符串 滑动窗口
     * <p>
     * 思路1：哈希+滑动窗口
     * <p>
     * 思路2：数组哈希+滑动窗口
     * 1、int[128] : 记录字符最近出现的位置
     * 2、遍历s
     * a、判断是否需要缩小窗口的左边界
     * b、res = Max(res, 右边界-左边界+1)
     * c、int[cs[i]]=i+1
     * 3、返回res
     */
    public int lengthOfLongestSubstring(String s) {
        int[] map = new int[128];
        char[] cs = s.toCharArray();

        int res = 0;
        int minIndex = 0;
        for (int i = 0; i < cs.length; i++) {
            minIndex = Math.max(minIndex, map[cs[i]]);
            res = Math.max(res, i - minIndex + 1);
            map[cs[i]] = i + 1;
        }
        return res;
    }

    public int lengthOfLongestSubstring2(String s) {
        Map<Character, Integer> map = new HashMap<>(128);
        char[] cs = s.toCharArray();

        int res = 0;
        int minIndex = -1;
        for (int i = 0; i < cs.length; i++) {
            if (map.containsKey(cs[i])) {
                minIndex = Math.max(minIndex, map.get(cs[i]));
            }
            res = Math.max(res, i - minIndex);

            map.put(cs[i], i);
        }
        return res;
    }


    public int lengthOfLongestSubstring1(String s) {
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
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * <p>
     * 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。
     * <p>
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * <p>
     * 提示：
     * 1 <= strs.length <= 104
     * 0 <= strs[i].length <= 100
     * strs[i] 仅包含小写字母
     * <p>
     * 相关标签 哈希表 字符串 排序
     * <p>
     * 思路1：哈希+排序
     * 1、map<key, values> : 字母异位词有相同的key, 将具有相同的key的str加入values
     * 2、字母异位词的判断：将str转为char[], 对char[]排序后，再转为字符串即为key
     * 3、返回 new ArrayList<>(map.values());
     * ps: 字母异位词的判断：也可以通过计数每个字符出现的次数来作为key
     * ps2: 字母异位词的判断：可以通过26个素数 乘以 字符出现的次数，来做为key
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] cs = str.toCharArray();
            Arrays.sort(cs);
            String key = new String(cs);
            List<String> temp = map.getOrDefault(key, new ArrayList<>());
            temp.add(str);
            map.put(key, temp);
        }
        return new ArrayList<>(map.values());
    }

    public List<List<String>> groupAnagrams1(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            String key = cCnt(str.toCharArray());
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }

    public String cCnt(char[] cs) {
        int[] cnt = new int[26];
        for (char c : cs) {
            cnt[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (cnt[i] != 0) {
                sb.append((char) (i + 'a')).append(cnt[i]);
            }
        }
        return sb.toString();
    }


    /**
     * 矩阵置零
     * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
     * <p>
     * 进阶：
     * 一个直观的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
     * 一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
     * 你能想出一个仅使用常量空间的解决方案吗？
     * <p>
     * 相关标签 数组 哈希表 矩阵
     * <p>
     * eg:
     * [0,1,2,0],
     * [3,4,5,2],
     * [1,3,1,5]]
     * <p>
     * [1,2,3,4],
     * [5,0,7,8],
     * [0,10,11,12],
     * [13,14,15,0]]
     *
     * <p>
     * 思路1: 数组标记 0（m+n）
     * 1、遍历，记录应该置为0的行，和应该置为0的列
     * 2、遍历，将对于行置为0，将对应的列置为0
     * <p>
     * 思路2: 标记 O(1)
     * 1、由思路1 ： 需要m+n个标记来记录行列是否需要置0
     * 2、声明两个变量r、l, 来预先记录第一行或第一列是否需要置零，
     * 3、借助matrix本身的第一行和第一列来标记当前行，当前列是否需要置0
     * 4、将matrix对应的行列置0
     * 5、借助r、l, 判断第一行或第一列是否置0
     */
    // o(m+n)
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[] r = new int[m];
        int[] l = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    r[i] = 1;
                    l[j] = 1;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (r[i] == 1 || l[j] == 1) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // o(1)
    public void setZeroes2(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int r = 1;
        int l = 1;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                l = 0;
                break;
            }
        }
        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                r = 0;
                break;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (r == 0) {
            Arrays.fill(matrix[0], 0);
        }
        if (l == 0) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    public void setZeroes1(int[][] matrix) {

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

    /**
     * 三数之和
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
     * 请你找出所有和为 0 且不重复的三元组。
     * <p>
     * 注意：答案中不可以包含重复的三元组。
     * <p>
     * 相关标签 数组 双指针 排序
     * <p>
     * 思路1：排序+双指针 （注意单向） （17ms）
     * 1、排序（便于增大or减小），定义指针 i,j,k  (0<k<i<j<nuns.length)
     * 2、k一直右移，每次在[k+1, nums.length-1]的范围内找到两个数之和 等于 -nums[k], 将nums[i],nums[j],nums[k]放入结果集res
     * 3、返回res
     * ps： 小优化，当nums[i]>0时，则没必要继续调用twoSum，因为两个大于0的数加起来不可能 < 0
     * <p>
     * 思路2: 6ms左右， 未学习
     */
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) {
            return new ArrayList<>();
        }

        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                break;
            }
            int c = nums[i];
            List<List<Integer>> sub = twoSum(nums, i, -c);
            res.addAll(sub);

            while (i + 1 < nums.length && nums[i + 1] == c) {
                i++;
            }
        }
        return res;
    }

    // nums 有序
    public List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int i = start + 1;
        int j = nums.length - 1;
        while (i < j) {
            int a = nums[i];
            int b = nums[j];
            int c = a + b;
            if (c > target) {
                j--;
            } else if (c < target) {
                i++;
            } else {
                List<Integer> sub = Arrays.asList(a, b, -target);
                res.add(sub);

                while (i < j && a == nums[i]) {
                    i++;
                }
                while (i < j && b == nums[j]) {
                    j--;
                }
            }
        }
        return res;
    }

    public List<List<Integer>> threeSum1(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                break;
            }
            List<List<Integer>> list = twoSum1(nums, i + 1, -nums[i]);

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

    public List<List<Integer>> twoSum1(int[] nums, int start, int target) {
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


    // -------------------------- 数组和字符串 ------------------------------end

}
