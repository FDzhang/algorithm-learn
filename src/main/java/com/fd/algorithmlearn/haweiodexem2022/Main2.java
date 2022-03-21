package com.fd.algorithmlearn.haweiodexem2022;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 太阳能板最大面积
 * Created by fd 2022-3-21
 */

public class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] arr = sc.nextLine().split(",");
        int[] nums = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nums[i] = Integer.parseInt(arr[i]);
        }

        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int h = Math.min(nums[i], nums[j]);
                int x = h * (j - i);
                if (x > res) {
                    res = x;
                }
            }
        }
        System.out.println(res);
    }
}
