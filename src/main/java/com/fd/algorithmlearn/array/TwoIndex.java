package com.fd.algorithmlearn.array;

import com.fd.algorithmlearn.linked.ListNode;

/**
 * 141. 环形链表 (简单)
 * https://leetcode-cn.com/problems/linked-list-cycle/
 *
 * @author zhangxinqiang
 * @create 2021/6/5 17:05
 */
public class TwoIndex {

    /**
     * Definition for singly-linked list.
     * class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) {
     * val = x;
     * next = null;
     * }
     * }
     */

    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
}
