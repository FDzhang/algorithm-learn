package com.fd.algorithmlearn.haweiodexem2022;


/**
 * Created by fd 2022-3-21
 */

public class Main4 {

    public static void main(String[] args) {
        int[] x = new int[]{3, 2, 3, 4};

        int res = money(x);
        System.err.println(res);

    }

    public static int money(int[] nums) {

        // 到第i家为止，所能偷到的最大金额
        // 0 第1家没偷，1第1家偷了
        int[][] dp = new int[nums.length][2];
        dp[0][0] = 0;
        dp[1][0] = nums[1];

        dp[0][1] = nums[0];
        dp[1][1] = nums[0];

        for (int i = 2; i < nums.length; i++) {
            dp[i][0] = Math.max(dp[i - 2][0] + nums[i], dp[i - 1][0]);
            dp[i][1] = Math.max(dp[i - 2][1] + nums[i], dp[i - 1][1]);
        }

        int max1 = Math.max(dp[nums.length - 1][0], dp[nums.length - 1][1]);
        int max2 = Math.max(dp[nums.length - 2][0], dp[nums.length - 2][1]);

        return Math.max(max1, max2);
    }


}
