package com.fd.algorithmlearn.haweiodexem2022;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 数字涂色
 * Created by fd 2022-3-21
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        int[] nums = new int[count];
        for (int i = 0; i < count; i++) {
            nums[i] = sc.nextInt();
        }

        boolean[] color = new boolean[101];
        int res = 0;
        Arrays.sort(nums);
        for (int num : nums) {
            if (!color[num]) {
                color[num] = true;
                int j = 2;
                while (num * j < 101) {
                    color[num * j] = true;
                    j++;
                }
                res++;
            }
        }
        System.out.println(res);
    }
}
