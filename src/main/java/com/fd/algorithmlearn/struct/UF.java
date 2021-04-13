package com.fd.algorithmlearn.struct;

/**
 * Union-Find 算法 : 并查集算法
 * 主要是解决图论中「动态连通性」问题的
 *
 * @author ：zxq
 * @date ：Created in 2021/4/13 16:06
 */

public class UF {

    /**
     * 连通分量个数
     */
    private int count;
    /**
     * 存储一棵树
     */
    private int[] parent;
    /**
     * 记录树的“重量”
     */
    private int[] size;

    public UF(int n) {
        this.count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public void union(int p, int q) {
        int rootp = find(p);
        int rootq = find(q);
        if (rootp == rootq) {
            return;
        }

        // 小树接到大树下面，较平衡
        if (size[rootp] > size[rootq]) {
            parent[rootq] = rootp;
            size[rootp] += size[rootq];
        } else {
            parent[rootp] = rootq;
            size[rootq] += size[rootp];
        }
        count--;
    }

    public boolean connected(int p, int q) {
        int rootp = find(p);
        int rootq = find(q);
        return rootp == rootq;
    }

    private int find(int x) {
        while (parent[x] != x) {
            // 进行路径压缩
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    public int count() {
        return count;
    }

}
