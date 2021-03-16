package com.fd.algorithmlearn.tree;

/**
 * 105. 从前序与中序遍历序列构造二叉树
 * https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 * @author : zxq
 * @date-time : 2021/3/16 22:46
 */
public class BuildTreeAlgo {


    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    public TreeNode buildTree(int[] preorder, int lo1, int hi1,
                              int[] inorder, int lo2, int hi2) {
        if (lo1 > hi1 || lo2 > hi2) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[lo1]);

        int in = lo2;
        for (int i = lo2; i <= hi2; i++) {
            if (preorder[lo1] == inorder[i]) {
                in = i;
                break;
            }
        }

        int pre = in - lo2;

        root.left = buildTree(preorder, lo1 + 1, lo1 + pre,
                inorder, lo2, in - 1);
        root.right = buildTree(preorder, lo1 + pre + 1, hi1,
                inorder, in + 1, hi2);

        return root;
    }

    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};

        BuildTreeAlgo algo = new BuildTreeAlgo();
        algo.buildTree(preorder, inorder);
    }
}
