package com.fd.algorithmlearn.tree;

import com.fd.algorithmlearn.entity.TreeNode;

/**
 * @author ：zxq
 * @date ：Created in 2021/3/15 17:45
 */

public class TreeUtil {


    public static void print(TreeNode root) {

        if (root == null) {

            return;
        }

        System.out.print(root.val + ", ");

        TreeUtil.print(root.left);
        TreeUtil.print(root.right);
    }

    /**
     * 二叉树的节点数
     */
    int count(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 先算出左右子树有多少节点
        int left = count(root.left);
        int right = count(root.right);
        /* 后序遍历代码位置 */
        // 加上自己，就是整棵二叉树的节点数
        return 1 + left + right;
    }

}
