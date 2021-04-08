package com.fd.algorithmlearn.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 341.扁平化嵌套列表迭代器（中等）
 * https://leetcode-cn.com/problems/flatten-nested-list-iterator/
 *
 * @author ：zxq
 * @date ：Created in 2021/4/8 15:09
 */

public class NestedIterator implements Iterator<Integer> {
    private LinkedList<NestedInteger> list;

    public NestedIterator(List<NestedInteger> nestedList) {
        this.list = new LinkedList<>(nestedList);
    }

    @Override
    public Integer next() {
        return list.remove(0).getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!list.isEmpty() && !list.get(0).isInteger()) {
            List<NestedInteger> integers = this.list.remove(0).getList();
            for (int i = integers.size() - 1; i >= 0; i--) {
                list.addFirst(integers.get(i));
            }
        }
        return !list.isEmpty();
    }
}