package com.fd.algorithmlearn.tree;

import java.util.*;

/**
 * 1. 排序
 * 2. 流水线 m
 * <p>
 * <p>
 * 3 5
 * 2 3 4 8 10
 * <p>
 * m1 2 8
 * m2 3 10
 * m3 4
 *
 * @author ：zxq
 * @date ：Created in 2021/3/28 16:08
 */

public class Main1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String[] nm = in.nextLine().split(" ");
        int m = Integer.parseInt(nm[0]);
        int n = Integer.parseInt(nm[1]);

        String[] nt = in.nextLine().split(" ");

        int[] t = new int[n];
        for (int i = 0; i < n; i++) {
            t[i] = Integer.parseInt(nt[i]);
        }
        Arrays.sort(t);
        Queue<Integer> qt = new ArrayDeque<>();
        for (int i : t) {
            qt.add(i);
        }

        int[] res = new int[m];
        while (!qt.isEmpty()) {
            res[0] += qt.poll();
            Arrays.sort(res);
        }

        System.out.println(res[m-1]);
    }
}
