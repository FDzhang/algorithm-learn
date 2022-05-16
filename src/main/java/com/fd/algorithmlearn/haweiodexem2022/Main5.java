package com.fd.algorithmlearn.haweiodexem2022;


import java.util.Scanner;

/**
 * marry的烦恼
 * Created by fd 2022-3-21
 */

public class Main5 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String[] nm = sc.nextLine().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        String[] numsStr = sc.nextLine().split(" ");
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(numsStr[i]);
        }


        dfs(0, m, nums);

        if (b){
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

    }

    public static boolean b = false;

    public static void dfs(int index, int target, int[] nums) {
        if (b) {
            return;
        }

        if (target < 0) {
            return;
        }
        if (target == 0) {
            b = true;
            return;
        }

        for (int i = index; i < nums.length; i++) {
            target -= nums[i];
            dfs(i, target, nums);
            target += nums[i];
        }
    }


}
