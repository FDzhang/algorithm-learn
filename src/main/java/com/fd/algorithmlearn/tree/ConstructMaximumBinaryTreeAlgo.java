package com.fd.algorithmlearn.tree;

/**
 * 654. 最大二叉树
 * https://leetcode-cn.com/problems/maximum-binary-tree/
 * <p>
 * 给定一个不含重复元素的整数数组 nums 。一个以此数组直接递归构建的 最大二叉树 定义如下：
 * <p>
 * 二叉树的根是数组 nums 中的最大元素。
 * 左子树是通过数组中 最大值左边部分 递归构造出的最大二叉树。
 * 右子树是通过数组中 最大值右边部分 递归构造出的最大二叉树。
 * 返回有给定数组 nums 构建的 最大二叉树 。
 *
 * @author ：zxq
 * @date ：Created in 2021/3/16 15:12
 */

public class ConstructMaximumBinaryTreeAlgo {

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    public TreeNode build(int[] nums, int lo, int hi) {
        if (lo > hi) {
            return null;
        }

        int maxIndex = maxIndex(nums, lo, hi);
        TreeNode root = new TreeNode(nums[maxIndex]);

        root.left = build(nums, lo, maxIndex - 1);
        root.right = build(nums, maxIndex + 1, hi);
        return root;
    }

    public int maxIndex(int[] nums, int start, int end) {
        int maxIndex = -1;
        int max = Integer.MIN_VALUE;
        for (int i = start; i <= end; i++) {
            if (nums[i] >= max) {
                max = nums[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static void main(String[] args) {
        ConstructMaximumBinaryTreeAlgo algo = new ConstructMaximumBinaryTreeAlgo();
        int[] x = {3, 2, 1, 6, 0, 5};
        TreeNode root = algo.constructMaximumBinaryTree(x);
        TreeUtil.print(root);

//        [6,3,5,null,2,0,null,null,1]

    }
}
