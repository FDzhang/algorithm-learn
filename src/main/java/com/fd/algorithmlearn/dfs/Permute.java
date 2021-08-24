package com.fd.algorithmlearn.dfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 46. 全排列
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 * <p>
 * https://leetcode-cn.com/problems/permutations/
 *
 * @author zhangxinqiang
 * @create 2021/8/24 11:28
 *
 * 思路：回溯算法 （路径，选择列表，结束条件）
 * 1、路径：也就是已经做出的选择。
 * 2、选择列表：也就是你当前可以做的选择。
 * 3、结束条件：也就是到达决策树底层，无法再做选择的条件。
 *
 * https://labuladong.gitee.io/algo/4/29/93/
 */
public class Permute {
    private List<List<Integer>>  res = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        LinkedList<Integer> arr = new LinkedList<>();
        help(nums, arr);
        return res;
    }

    private void help(int[] nums, LinkedList<Integer> arr) {
        if (arr.size() == nums.length){
            res.add(new LinkedList<>(arr));
            return;
        }

        for (int num : nums) {
            if (arr.contains(num)){
                continue;
            }
            arr.add(num);
            help(nums, arr);
            arr.removeLast();
        }
    }
}
