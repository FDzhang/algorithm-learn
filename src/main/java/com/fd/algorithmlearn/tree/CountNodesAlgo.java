package com.fd.algorithmlearn.tree;

import com.fd.algorithmlearn.entity.TreeNode;

/**
 * 222.完全二叉树的节点个数（中等）
 * https://leetcode-cn.com/problems/count-complete-tree-nodes/
 *
 * @author ：zxq
 * @date ：Created in 2021/4/12 14:19
 */

public class CountNodesAlgo {


    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int lh = 0;
        TreeNode left = root;
        while (left != null) {
            left = left.left;
            lh++;
        }
        int lr = 0;
        TreeNode right = root;
        while (right != null) {
            right = right.right;
            lr++;
        }

        if (lh == lr) {
            return (int) Math.pow(2, lh) - 1;
        }

        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}
