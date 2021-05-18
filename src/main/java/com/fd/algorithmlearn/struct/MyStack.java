package com.fd.algorithmlearn.struct;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 225. 用队列实现栈
 * https://leetcode-cn.com/problems/implement-stack-using-queues/
 *
 * @author zhangxinqiang
 * @create 2021/5/18 10:36
 */
public class MyStack {
    private Queue<Integer> queue = new LinkedList<>();
    int top = 0;

    /**
     * Initialize your data structure here.
     */
    public MyStack() {

    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        // x 是队列的队尾，是栈的栈顶
        queue.offer(x);
        top = x;
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        int size = queue.size();
        // 留下队尾 2 个元素
        while (size > 2) {
            queue.offer(queue.poll());
            size--;
        }
        // 记录新的队尾元素
        top = queue.peek();
        queue.offer(queue.poll());
        // 删除之前的队尾元素
        return queue.poll();
    }

    /**
     * Get the top element.
     */
    public int top() {
        return top;
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return queue.isEmpty();
    }
}
