package com.fd.algorithmlearn.entity;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhangxinqiang
 * @create 2022/3/10 13:12
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public  TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode buildByArray(Integer[] vals) {
        TreeNode head = new TreeNode(vals[0]);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(head);
        for (int i = 1; i < vals.length; i += 2) {
            if (queue.isEmpty()) {
                break;
            }
            TreeNode node = queue.poll();
            if (vals[i] != null) {
                node.left = new TreeNode(vals[i]);
                queue.offer(node.left);
            }
            if (i == vals.length - 1) {
                break;
            }
            if (vals[i + 1] != null) {
                node.right = new TreeNode(vals[i + 1]);
                queue.offer(node.right);
            }
        }
        return head;
    }
}
