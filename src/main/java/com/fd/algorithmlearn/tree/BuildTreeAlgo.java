package com.fd.algorithmlearn.tree;

/**
 * 105. 从前序与中序遍历序列构造二叉树 （中等）
 * https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 *
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
        // root 节点对应的值就是前序遍历数组的第一个元素
        int rootVal = preorder[lo1];
        // rootVal 在中序遍历数组中的索引
        int in = lo2;
        for (int i = lo2; i <= hi2; i++) {
            if (rootVal == inorder[i]) {
                in = i;
                break;
            }
        }

        int leftSize = in - lo2;

        // 先构造出当前根节点
        TreeNode root = new TreeNode(rootVal);
        // 递归构造左右子树
        root.left = buildTree(
                preorder, lo1 + 1, lo1 + leftSize,
                inorder, lo2, in - 1);
        root.right = buildTree(
                preorder, lo1 + leftSize + 1, hi1,
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
