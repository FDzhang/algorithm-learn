package com.fd.algorithmlearn.tree;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * 限制条件： N 大于0 ，小于等于10万;  x大于等于0，小于等于1000万
 * 第一行输入两个数据 N x
 * 第二行输入 N个数 （每个数大于等于0，小于等于100）
 *
 * 求有多少个 连续区间之和 大于x
 *
 * 例如：
 * 输入：
 * 3 7
 * 3 4 7
 *
 * 结果：4 （因为 3+4 3+4+7 4+7 7）
 *
 * 23 56
 * 75 39 35 34 50 87 54 16 17 91 70 65 8 30 87 42 58 49 74 54 94 40 15
 * <p>
 * 189 600
 * 60 73 72 43 95 19 82 91 41 54 35 14 27 34 82 1 29 80 47 64 69 1 77 18 64 93 88 96 72 48 66 40 62 46 44 92 57 72 67 1 3 48 96 76 76 72 77 66 72 83 36 78 37 79 28 56 66 63 0 92 36 41 62 64 50 25 5 71 78 41 37 25 100 35 78 23 56 28 71 64 40 16 7 79 100 6 2 32 41 26 70 79 50 95 28 20 19 44 86 0 77 98 26 14 86 36 52 72 12 48 40 41 83 99 89 23 65 77 38 4 10 38 46 88 99 15 63 41 92 16 28 44 10 38 90 66 47 10 7 83 20 95 10 87 98 73 49 44 39 2 44 62 78 37 89 0 100 89 45 88 48 59 26 49 3 98 20 30 98 15 64 41 40 72 71 65 90 38 79 53 23 2 20 46 30 99 22 87 40
 *
 * @author ：zxq
 * @date ：Created in 2021/3/28 16:27
 */

public class Main2 {

//    public static void main(String[] args) {
//        int n =10;
//        String[] ns = r(n).split(" ");
//        int[] nums = new int[n];
//        for (int i = 0; i < n; i++) {
//            nums[i] = Integer.parseInt(ns[i]);
//        }
//        System.out.println(Arrays.toString(nums));
//        System.out.println(ns.length);
//    }

    public static String r(int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            Random random = new Random(System.currentTimeMillis());

            s.append(random.nextInt(101)).append(" ");
        }
        return s.toString();
    }

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//
//        String[] nx = in.nextLine().split(" ");
//        int n = Integer.parseInt(nx[0]);
//        int x = Integer.parseInt(nx[1]);
//
//        String[] ns = in.nextLine().split(" ");
//
//        int[] nums = new int[n];
//        for (int i = 0; i < n; i++) {
//            nums[i] = Integer.parseInt(ns[i]);
//        }
//
//        long res = 0;
//        for (int i = 0; i < nums.length; i++) {
//            long sum = nums[i];
//            if (nums[i] >= x) {
//                res++;
//            }
//            for (int j = i + 1; j < nums.length; j++) {
//                sum += nums[j];
//                if (sum >= x) {
//                    res += (nums.length - j);
//                    break;
//                }
//            }
//        }
//
//        System.out.println(res);
//    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

//        String[] nx = in.nextLine().split(" ");
//        int n = Integer.parseInt(nx[0]);
//        int x = Integer.parseInt(nx[1]);
        int n = 100000;
        int x = 1312424;

//        String[] ns = in.nextLine().split(" ");
        String[] ns = r(n).split(" ");
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ns[i]);
        }

        long res = 0;
        for (int i = 0; i < nums.length; i++) {
            long sum = nums[i];
            if (nums[i] >= x) {
                res++;
            }
            for (int j = i + 1; j < nums.length; j++) {
                sum += nums[j];
                if (sum >= x) {
                    res += (nums.length - j);
                    break;
                }
            }
        }

        System.out.println(res);
    }
}
