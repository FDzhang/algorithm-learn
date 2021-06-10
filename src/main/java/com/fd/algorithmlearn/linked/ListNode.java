package com.fd.algorithmlearn.linked;

/**
 * Definition for singly-linked list.
 *
 * @author: zxq
 * @date-time: 2021/3/13 16:33
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

   public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
