package com.fd.algorithmlearn.tree;

/**
 * Definition for a binary tree node.
 *
 * @author ：zxq
 * @date ：Created in 2021/3/15 17:39
 */

public class TreeNode {
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
