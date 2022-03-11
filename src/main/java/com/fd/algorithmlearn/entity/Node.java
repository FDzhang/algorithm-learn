package com.fd.algorithmlearn.entity;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author : zxq
 * @create : 2022/3/11 20:37
 */
public class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }

    public static Node build(Integer[] vals) {
        Node head = new Node(vals[0]);

        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        for (int i = 1; i < vals.length; i += 2) {
            if (queue.isEmpty()) {
                break;
            }
            Node node = queue.poll();
            if (vals[i] != null) {
                node.left = new Node(vals[i]);
                queue.offer(node.left);
            }
            if (i == vals.length - 1) {
                break;
            }
            if (vals[i + 1] != null) {
                node.right = new Node(vals[i + 1]);
                queue.offer(node.right);
            }
        }
        return head;
    }

    public static void print(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Queue<Node> nextLevel = new LinkedList<>();
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                if (node.left != null) {
                    nextLevel.add(node.left);
                }
                if (node.right != null) {
                    nextLevel.add(node.right);
                }
                System.err.print(node.val + " ");
            }
            queue = nextLevel;
            System.err.print("# ");
        }
    }

    public static void printByNext(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left != null) {
                queue.add(node.left);
            }
            System.err.print(node.val + " ");
            while (node.next != null) {
                node = node.next;
                System.err.print(node.val + " ");
            }
            System.err.print("# ");
        }
    }
}
