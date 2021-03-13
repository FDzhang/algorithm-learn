package com.fd.algorithmlearn.linked;

import java.util.List;

/**
 * 题目：https://leetcode-cn.com/problems/reverse-linked-list-ii/
 *
 *
 * @author: zxq
 * @date-time: 2021/3/13 2:38
 */

public class LinkReverse {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 递归反转整个链表
     */
    ListNode reverse(ListNode head) {
        if (head.next == null) {
            return head;
        }
        ListNode last = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }

    /* ---------------------------------------- */

    /**
     * 后驱节点
     */
    ListNode successor = null;

    /**
     * 反转链表前 N 个节点
     */
    ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            // 记录第n+1个节点
            successor = head.next;
            return head;
        }

        // 以 head.next 为起点，需要反转前 n - 1 个节点
        ListNode last = reverseN(head.next, n - 1);

        head.next.next = head;
        // 让反转之后的 head 节点和后面的节点连起来
        head.next = successor;
        return last;
    }

    /* ---------------------------------------- */

    /**
     * 反转链表的一部分
     */
    ListNode reverseBetween(ListNode head, int m, int n) {
        // base case
        if (m == 1) {
            return reverseN(head, n);
        }
        // 前进到反转的起点触发 base case
        head.next = reverseBetween(head.next, m - 1, n - 1);
        return head;
    }

    public static void main(String[] args) {

    }
}
