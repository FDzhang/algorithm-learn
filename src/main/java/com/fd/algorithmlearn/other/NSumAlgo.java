package com.fd.algorithmlearn.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 15. 三数之和 中等
 * https://leetcode-cn.com/problems/3sum/
 * 18. 四数之和 中等
 * https://leetcode-cn.com/problems/4sum/
 * @author ：zxq
 * @date ：Created in 2021/3/29 17:56
 */

public class NSumAlgo {

    //    public int[] twoSum(int[] nums, int target) {
//        Arrays.sort(nums);
//        int lo = 0, hi = nums.length - 1;
//        while (lo < hi) {
//            int sum = nums[lo] + nums[hi];
//            if (sum < target) {
//                lo++;
//            } else if (sum > target) {
//                hi--;
//            } else if (sum == target) {
//                return new int[]{nums[lo], nums[hi]};
//            }
//        }
//        return new int[]{};
//    }
    public List<List<Integer>> twoSum(int[] nums, int start, int target) {
        //排序
        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList<>();
        int lo = start, hi = nums.length - 1;
        while (lo < hi) {
            int sum = nums[lo] + nums[hi];
            int left = nums[lo], right = nums[hi];
            if (sum < target) {
                while (lo < hi && nums[lo] == left) {
                    lo++;
                }
            } else if (sum > target) {
                while (lo < hi && nums[hi] == right) {
                    hi--;
                }
            } else if (sum == target) {
                List<Integer> l = new ArrayList<>();
                l.add(left);
                l.add(right);
                res.add(l);

                while (lo < hi && nums[lo] == left) {
                    lo++;
                }
                while (lo < hi && nums[hi] == right) {
                    hi--;
                }
            }
        }
        return res;
    }

    public List<List<Integer>> twoSum(int[] nums, int target) {
        return twoSum(nums, 0, target);
    }

    public List<List<Integer>> threeSumTarget(int[] nums, int target) {
        return threeSumTarget(nums, 0, target);
    }

    public List<List<Integer>> threeSumTarget(int[] nums, int start, int target) {
        // 排序
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        // 穷举 threeSum的第一个数
        for (int i = start; i < n; i++) {
            // 对 target - nums[i] 计算 twoSum
            List<List<Integer>> tuples = twoSum(nums, i + 1, target - nums[i]);
            for (List<Integer> tuple : tuples) {
                tuple.add(nums[i]);
                res.add(tuple);
            }
            // 跳过第一个数字重复的情况
            while (i < n - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        // 求和为 0 的三元组
        return res;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        // 输入数组 nums，返回所有和为 target 的三元组
        return threeSumTarget(nums, 0);
    }

    public List<List<Integer>> fourSum(int[] nums) {
        return fourSumTarget(nums, 0);
    }

    public List<List<Integer>> fourSumTarget(int[] nums, int target) {
        // 排序
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        // 穷举 threeSum的第一个数
        for (int i = 0; i < n; i++) {
            // 对 target - nums[i] 计算 twoSum
            List<List<Integer>> tuples = threeSumTarget(nums, i + 1, target - nums[i]);
            for (List<Integer> tuple : tuples) {
                tuple.add(nums[i]);
                res.add(tuple);
            }
            // 跳过第一个数字重复的情况
            while (i < n - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        // 求和为 0 的三元组
        return res;
    }

//    public List<List<Integer>> nSumTarget(int[] nums, int n, int start, int target) {
//        int sz = nums.length;
//
//        List<List<Integer>> res = new ArrayList<>();
//        // 至少是 2Sum，且数组大小不应该小于 n
//        if (n < 2 || sz < n) {
//            return res;
//        }
//        // 2Sum 是 base case
//        if (n == 2) {
//            // 双指针那一套操作
//            int lo = start, hi = nums.length - 1;
//            while (lo < hi) {
//                int sum = nums[lo] + nums[hi];
//                int left = nums[lo], right = nums[hi];
//                if (sum < target) {
//                    while (lo < hi && nums[lo] == left) {
//                        lo++;
//                    }
//                } else if (sum > target) {
//                    while (lo < hi && nums[hi] == right) {
//                        hi--;
//                    }
//                } else if (sum == target) {
//                    List<Integer> l = new ArrayList<>();
//                    l.add(left);
//                    l.add(right);
//                    res.add(l);
//                    while (lo < hi && nums[lo] == left) {
//                        lo++;
//                    }
//                    while (lo < hi && nums[hi] == right) {
//                        hi--;
//                    }
//                }
//            }
//        } else {
//            // n > 2 时，递归计算 (n-1)Sum 的结果
//            for (int i = start; i < sz; i++) {
//                List<List<Integer>> sub = nSumTarget(nums, n - 1, start + 1, target);
//                for (List<Integer> tuple : sub) {
//                    tuple.add(nums[i]);
//                    res.add(tuple);
//                }
//                while (i < sz - 1 && nums[i] == nums[i + 1]) {
//                    i++;
//                }
//            }
//        }
//        return res;
//    }


    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return nSumTarget(nums, 4, 0, target);
    }

    public List<List<Integer>> nSumTarget(int[] nums, int n, int start, int target) {
        int sz = nums.length;

        List<List<Integer>> res = new ArrayList<>();
        // 至少是 2Sum，且数组大小不应该小于 n
        if (n < 2 || sz < n) {
            return res;
        }
        // 2Sum 是 base case
        if (n == 2) {
            // 双指针那一套操作
            int lo = start, hi = sz - 1;
            while (lo < hi) {
                int sum = nums[lo] + nums[hi];
                int left = nums[lo], right = nums[hi];
                if (sum < target) {
                    while (lo < hi && nums[lo] == left) {
                        lo++;
                    }
                } else if (sum > target) {
                    while (lo < hi && nums[hi] == right) {
                        hi--;
                    }
                } else {
                    List<Integer> l = new ArrayList<>();
                    l.add(left);
                    l.add(right);
                    res.add(l);
                    while (lo < hi && nums[lo] == left) {
                        lo++;
                    }
                    while (lo < hi && nums[hi] == right) {
                        hi--;
                    }
                }
            }
        } else {
            // n > 2 时，递归计算 (n-1)Sum 的结果
            for (int i = start; i < sz; i++) {
                if (nums[i] > 0) {
                    break;
                }
                List<List<Integer>> sub = nSumTarget(nums, n - 1, i + 1, target - nums[i]);
                for (List<Integer> tuple : sub) {
                    tuple.add(nums[i]);
                    res.add(tuple);
                }
                while (i < sz - 1 && nums[i] == nums[i + 1]) {
                    i++;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        NSumAlgo nSumAlgo = new NSumAlgo();

        int[] x = new int[]{1, 0, -1, 0, -2, 2};
        Arrays.sort(x);
        System.out.println(Arrays.toString(x));
        System.out.println(nSumAlgo.nSumTarget(x, 4, 0, 0));
    }
}
