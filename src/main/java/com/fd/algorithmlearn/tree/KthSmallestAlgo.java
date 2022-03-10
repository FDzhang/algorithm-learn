package com.fd.algorithmlearn.tree;

import com.fd.algorithmlearn.entity.TreeNode;

/**
 * 230. 二叉搜索树中第K小的元素 中等
 * https://leetcode-cn.com/problems/kth-smallest-element-in-a-bst/
 *
 * @author ：zxq
 * @date ：Created in 2021/3/23 14:25
 */

public class KthSmallestAlgo {
    public int kthSmallest(TreeNode root, int k) {
        // 利用 BST 的中序遍历特性
        kth(root, k);
        return val;
    }

    // 记录当前元素的排名
    private int rank = 0;
    // 记录结果
    private int val = 0;

    public void kth(TreeNode root, int k) {
        if (root == null) {
            return;
        }

        kth(root.left, k);
        /* 中序遍历代码位置 */
        rank++;
        if (rank == k) {
            // 找到第 k 小的元素
            val = root.val;
            return;
        }

        kth(root.right, k);
    }
}
