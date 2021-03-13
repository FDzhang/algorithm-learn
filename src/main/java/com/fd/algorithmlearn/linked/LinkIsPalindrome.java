package com.fd.algorithmlearn.linked;

import static com.fd.algorithmlearn.linked.LinkUtil.*;

/**
 * 题：234. 回文链表
 * https://leetcode-cn.com/problems/palindrome-linked-list/
 *
 * @author: zxq
 * @date-time: 2021/3/13 16:31
 */
public class LinkIsPalindrome {
    /**
     * 左侧指针
     */

    ListNode left;


    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return false;
        }
        if (head.next == null){
            return true;
        }
        ListNode p = head, q = head;

        ListNode slow, fast;
        slow = fast = head;
        while (fast != null && fast.next != null) {
            p = slow;
            q = fast.next;

            slow = slow.next;
            fast = fast.next.next;
        }
        // slow 指针现在指向链表中点
        if (fast != null) {
            p = p.next;
            q = q.next;

            slow = slow.next;
        }

        ListNode left = head;
        ListNode right = reverse(slow);

        while (right != null) {
            if (left.val != right.val) {
                p.next = reverse(q);
                return false;
            }
            left = left.next;
            right = right.next;
        }

        p.next = reverse(q);
        return true;
    }

    private ListNode reverse(ListNode slow) {
        ListNode pre = null, cur = slow;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }


    boolean traverse(ListNode right) {
        if (right == null) {
            return true;
        }

        boolean res = traverse(right.next);

        res = res && (right.val == left.val);
        left = left.next;
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 1, 2};

        ListNode head = create(arr);

        LinkIsPalindrome linkIsPalindrome = new LinkIsPalindrome();

        System.out.println(linkIsPalindrome.isPalindrome(head));
        print(head);
    }
}
