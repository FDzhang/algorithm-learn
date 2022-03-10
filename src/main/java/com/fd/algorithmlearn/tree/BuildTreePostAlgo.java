package com.fd.algorithmlearn.tree;

import com.fd.algorithmlearn.entity.TreeNode;

/**
 * 106. 从中序与后序遍历序列构造二叉树 （中等）
 * https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
 *
 * @author : zxq
 * @date-time : 2021/3/16 23:17
 */
public class BuildTreePostAlgo {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTree(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    public TreeNode buildTree(int[] inorder, int lo1, int hi1,
                              int[] postorder, int lo2, int hi2) {
        if (lo1 > hi1 || lo2 > hi2) {
            return null;
        }
        // root 节点对应的值就是后续遍历数组的最后一个元素
        int rootVal = postorder[hi2];
        // rootVal 在中序遍历数组中的索引
        int in = lo1;
        for (int i = lo1; i <= hi1; i++) {
            if (rootVal == inorder[i]) {
                in = i;
                break;
            }
        }

        int leftSize = in - lo1;

        // 先构造出当前根节点
        TreeNode root = new TreeNode(rootVal);
        // 递归构造左右子树
        root.left = buildTree(
                inorder, lo1, in - 1,
                postorder, lo2, lo2 + leftSize - 1);
        root.right = buildTree(
                inorder, in + 1, hi1,
                postorder, lo2 + leftSize, hi2 - 1);

        return root;
    }
}
