package com.fd.algorithmlearn.linked;

/**
 * 题：25. K 个一组翻转链表
 * https://leetcode-cn.com/problems/reverse-nodes-in-k-group
 *
 * @author: zxq
 * @date-time: 2021/3/13 15:21
 */
public class LinkReverseBetween {
    /**
     * 反转以 a 为头结点的链表
     */
    ListNode reverse(ListNode a) {
        ListNode pre, cur, nxt;
        pre = null;
        cur = a;
        nxt = a;
        while (cur != null) {
            nxt = cur.next;
            // 逐个结点反转
            cur.next = pre;
            // 更新指针位置
            pre = cur;
            cur = nxt;
        }
        // 返回反转后的头结点
        return pre;
    }

    /**
     * 反转区间 [a, b) 的元素，注意是左闭右开
     */
    ListNode reverse(ListNode a, ListNode b) {
        ListNode pre, cur, nxt;
        pre = null;
        cur = a;
        nxt = a;
        // while 终止的条件改一下就行了
        while (cur != b) {
            nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        // 返回反转后的头结点
        return pre;
    }


    ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        // 区间 [a, b) 包含 k 个待反转元素
        ListNode a, b;
        a = b = head;
        for (int i = 0; i < k; i++) {
            // 不足 k 个，不需要反转，base case
            if (b == null) {
                return a;
            }
            b = b.next;
        }
        // 反转前 k 个元素
        ListNode newHead = reverse(a, b);
        // 递归反转后续链表并连接起来
        a.next = reverseKGroup(b, k);
        return newHead;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        ListNode head = LinkUtil.create(arr);

        LinkUtil.print(head);

        LinkReverseBetween reverse = new LinkReverseBetween();

        ListNode newHead = reverse.reverseKGroup(head, 2);

        LinkUtil.print(newHead);
    }
}
