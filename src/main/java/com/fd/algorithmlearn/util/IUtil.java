package com.fd.algorithmlearn.util;

import java.util.List;

/**
 * @author zhangxinqiang
 * @create 2021/12/15 11:36
 */
public class IUtil {
    public static void print(int[][] nums) {
        System.err.println("------------------------------");
        for (int[] num : nums) {
            for (int x : num) {
                System.err.print(x + "\t");
            }
            System.err.println();
        }
        System.err.println();
    }

    public static void print(int[] list) {
        System.err.println("------------------------------");
        for (int i : list) {
            System.err.print(i + "\t");
        }
        System.err.println();
    }

    public static <T> void print(List<T> list) {
        System.err.println("------------------------------");
        for (T i : list) {
            System.err.print(i + "\t");
        }
    }
}
