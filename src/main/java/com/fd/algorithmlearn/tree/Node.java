package com.fd.algorithmlearn.tree;

/**
 * Definition for a Node.
 *
 * @author: zxq
 * @date-time: 2021/3/16 0:17
 */
public class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {
    }

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, Node left, Node right, Node next) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.next = next;
    }
}
