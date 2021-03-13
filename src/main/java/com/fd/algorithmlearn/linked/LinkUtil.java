package com.fd.algorithmlearn.linked;

/**
 * 创建链表
 * 打印链表
 *
 * @author: zxq
 * @date-time: 2021/3/13 16:34
 */
public class LinkUtil {

    public static ListNode create(int[] arr) {
        if (arr.length <= 0) {
            return null;
        }

        ListNode head = new ListNode(arr[0], null);
        ListNode pre = head;
        for (int i = 1; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i], null);
            pre.next = node;
            pre = node;
        }
        return head;
    }

    public static void print(ListNode head) {
        ListNode p = head;
        while (p != null) {
            System.out.print(p.val + ", ");
            p = p.next;
        }
        System.out.println();
    }

    /* 倒序打印单链表中的元素值 */
    static void traverse(ListNode head) {
        if (head == null) {
            return;
        }
        traverse(head.next);
        // 后序遍历代码
        System.out.print(head.val+ ", ");
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};

        ListNode head = create(arr);
        print(head);
        traverse(head);
    }

}
