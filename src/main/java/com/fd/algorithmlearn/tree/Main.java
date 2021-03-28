package com.fd.algorithmlearn.tree;

import java.util.*;

/**
 * 1. 构建二叉树
 * 2. 剔除叶子节点
 * 3. 后序遍历
 * <p>
 * 1 2 3 4 5 6 7 8 9 10 11 12
 *
 * @author ：zxq
 * @date ：Created in 2021/3/28 15:16
 */

public class Main {

    static class Node {
        private int val;
        private Node left;
        private Node right;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] ss = in.nextLine().split(" ");
        for (String s : ss) {
            queue.add(Integer.parseInt(s));
        }

        Node root = build();
        remove(root);

        List<String> p = new ArrayList<>();
        print(root, p);

        System.out.println(String.join(" ", p));
    }

    private static Queue<Integer> queue = new ArrayDeque<>();
    private static Queue<Node> nodes = new ArrayDeque<>();

    public static Node build() {
        Node root = new Node(queue.poll());
        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node top = nodes.poll();

            if (!queue.isEmpty()) {
                Node left = new Node(queue.poll());
                top.left = left;
                nodes.add(left);
            } else {
                break;
            }

            if (!queue.isEmpty()) {
                Node right = new Node(queue.poll());
                top.right = right;
                nodes.add(right);
            } else {
                break;
            }
        }
        return root;
    }


    public static Node remove(Node root) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            return null;
        }

        root.left = remove(root.left);
        root.right = remove(root.right);

        return root;
    }

    public static void print(Node root, List<String> p) {
        if (root == null) {
            return;
        }

        print(root.left, p);
        print(root.right, p);
        p.add(String.valueOf(root.val));
    }
}
