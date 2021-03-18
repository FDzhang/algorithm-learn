package com.fd.algorithmlearn.tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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

        print(root.left);
        print(root.right);
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
