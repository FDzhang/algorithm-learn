package com.fd.algorithmlearn.tree;

import java.util.List;

/**
 * @author ：zxq
 * @date ：Created in 2021/4/8 15:19
 */

public class NestedIntegerImpl {
    private Integer val;
    private List<NestedIntegerImpl> list;

    public NestedIntegerImpl(Integer val) {
        this.val = val;
        this.list = null;
    }

    public NestedIntegerImpl(List<NestedIntegerImpl> list) {
        this.list = list;
        this.val = null;
    }

    // 如果其中存的是一个整数，则返回 true，否则返回 false
    public boolean isInteger() {
        return val != null;
    }

    // 如果其中存的是一个整数，则返回这个整数，否则返回 null
    public Integer getInteger() {
        return this.val;
    }

    // 如果其中存的是一个列表，则返回这个列表，否则返回 null
    public List<NestedIntegerImpl> getList() {
        return this.list;
    }
}
