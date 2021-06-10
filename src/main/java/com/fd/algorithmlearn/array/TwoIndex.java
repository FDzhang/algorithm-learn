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

    /**
     * 设 slow 走了 K 步 ， 则fast 走了 2K 步
     * slow 与 fast 在环的某一处相遇，设相遇点与 环起点的距离为 m，
     * 则链表头 距离 环起点 的距离为 K-m
     *
     * k = 环的整数倍 （如果有环的话）
     */
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                break;
            }
        }
        // 上面的代码类似 hasCycle 函数
        if ((fast == null || fast.next == null)) {
            // fast 遇到空指针说明没有环
            return null;
        }

        slow = head;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

}
