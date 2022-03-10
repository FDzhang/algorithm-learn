package com.fd.algorithmlearn.tree;

import com.fd.algorithmlearn.entity.TreeNode;

/**
 * 226. 翻转二叉树
 * https://leetcode-cn.com/problems/invert-binary-tree/
 *
 * @author ：zxq
 * @date ：Created in 2021/3/15 17:38
 */

public class InvertTreeAlgo {

    public TreeNode invertTree(TreeNode root) {
        // base case
        if (root == null) {
            return null;
        }
        /* 前序遍历位置 */
        // root 节点需要交换它的左右子节点
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        // 让左右子节点继续翻转它们的子节点
        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

}
