package com.fd.algorithmlearn.util;

import com.fd.algorithmlearn.entity.TreeNode;
import com.fd.algorithmlearn.linked.ListNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author zhangxinqiang
 * @create 2021/12/15 11:36
 */
public class IUtil {

    public static ListNode buildNodes(int[] nums) {
        ListNode head = new ListNode();
        ListNode p = head;
        for (int num : nums) {
            p.next = new ListNode(num);
            p = p.next;
        }
        return head.next;
    }

    public static void printNodes(TreeNode head) {

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

    public static void printNodes(ListNode head) {
        while (head != null) {
            System.err.print(head.val + " ");
            head = head.next;
        }
        System.err.println();
    }

    public static void print(int[][] nums) {
        if (nums == null) {
            System.err.println("null");
            return;
        }
        System.err.println("------------------------------");
        for (int[] num : nums) {
            for (int x : num) {
                System.err.print(x + "\t");
            }
            System.err.println();
        }
        System.err.println();
    }

    public static void print(int[] list) {
        if (list == null) {
            System.err.println("null");
            return;
        }
        System.err.println("------------------------------");
        for (int i : list) {
            System.err.print(i + "\t");
        }
        System.err.println();
    }

    public static <T> void print(List<T> list) {
        if (list == null) {
            System.err.println("null");
            return;
        }
        System.err.println("------------------------------");
        for (T i : list) {
            System.err.print(i + "\t");
        }
    }
}
