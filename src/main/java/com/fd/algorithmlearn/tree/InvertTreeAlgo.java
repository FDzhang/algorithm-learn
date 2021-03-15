package com.fd.algorithmlearn.tree;

/**
 * 翻转二叉树
 * @author ：zxq
 * @date ：Created in 2021/3/15 17:38
 */

public class InvertTreeAlgo {

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

}
