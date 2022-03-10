package com.fd.algorithmlearn.tree;

import com.fd.algorithmlearn.entity.TreeNode;

/**
 * 538. 把二叉搜索树转换为累加树 中等
 * https://leetcode-cn.com/problems/convert-bst-to-greater-tree/
 *
 * @author ：zxq
 * @date ：Created in 2021/3/23 14:36
 */

public class ConvertBSTAlgo {

    public TreeNode convertBST(TreeNode root) {
        convert(root);
        return root;
    }

    // 记录累加和
    private int sum = 0;

    public void convert(TreeNode root) {

        if (root == null) {
            return;
        }
        convert(root.right);

        // 维护累加和
        sum += root.val;
        // 将 BST 转化成累加树
        root.val = sum;

        convert(root.left);

    }
}
