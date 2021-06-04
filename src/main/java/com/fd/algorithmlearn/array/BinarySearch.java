package com.fd.algorithmlearn.array;

import java.util.Arrays;

/**
 * 875. 爱吃香蕉的珂珂 (中等)
 * https://leetcode-cn.com/problems/koko-eating-bananas/
 * <p>
 * 1011. 在 D 天内送达包裹的能力
 * https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days/
 * <p>
 * 410. 分割数组的最大值
 * https://leetcode-cn.com/problems/split-array-largest-sum/
 *
 * @author zhangxinqiang
 * @create 2021/5/19 10:28
 */
public class BinarySearch {

    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = Integer.MAX_VALUE;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canFinish(piles, mid, h)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean canFinish(int[] piles, int speed, int h) {
        int time = 0;
        for (int pile : piles) {
            time += (pile / speed) + ((pile % speed) > 0 ? 1 : 0);
        }
        return time <= h;
    }

    private static int getMax(int[] piles) {
        return Arrays.stream(piles).max().orElse(0);
    }

    private static int getSum(int[] weights) {
        return Arrays.stream(weights).sum();
    }

    // 寻找左侧边界的二分查找
    int shipWithinDays(int[] weights, int D) {
        // 载重可能的最小值
        int left = getMax(weights);
        // 载重可能的最大值 + 1
        int right = getSum(weights) + 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canFinish(weights, D, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    // 如果载重为 cap，是否能在 D 天内运完货物？
    boolean canFinish1(int[] w, int D, int cap) {
        int maxCap = cap;
        int d = 1;
        for (int i : w) {
            if (maxCap < i) {
                maxCap = cap;
                d++;
                if (d > D) {
                    return false;
                }
            }
            maxCap -= i;
        }
        return true;

//        for (int i = 0; i < w.length; i++) {
//            if ((maxCap -= w[i]) < 0) {
//                d++;
//                maxCap = cap;
//                i--;
//                if (d >= D) {
//                    return false;
//                }
//            }
//        }
//        return true;
//        int i = 0;
//        for (int day = 0; day < D; day++) {
//            int maxCap = cap;
//            while ((maxCap -= w[i]) >= 0) {
//                i++;
//                if (i == w.length)
//                    return true;
//            }
//        }
//        return false;
    }

    public static int splitArray(int[] nums, int m) {
        int i = getMax(nums);
        int j = getSum(nums);
        while (i < j) {
            int mid = i + (j - i) >> 1;
            // 要求每组的和最大为 i， 则至少需要分多少组
            int n = group(nums, mid);
            if (n <= m) {
                j = mid;
            } else {
                i = mid + 1;
            }
        }
        return i;
    }

    private static int group(int[] nums, int max) {
        int count = 1;
        int sum = 0;
        for (int num : nums) {
            if (sum + num <= max) {
                sum += num;
            } else {
                sum = num;
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {

        System.out.println(-20 >> 2);
        System.out.println(20 >> 2);

        System.out.println(-20 >>> 2);
        System.out.println(20 >>> 2);
//        int[] x = {7, 2, 5, 10, 8};
//        System.out.println(splitArray(x, 2));
    }

}
