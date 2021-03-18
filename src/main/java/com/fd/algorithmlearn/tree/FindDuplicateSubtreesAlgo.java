package com.fd.algorithmlearn.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 652. 寻找重复的子树 中等
 * https://leetcode-cn.com/problems/find-duplicate-subtrees/
 *
 * @author : zxq
 * @date-time : 2021/3/18 23:41
 */
public class FindDuplicateSubtreesAlgo {

    HashMap<String, Integer> memo = new HashMap<>();

    LinkedList<TreeNode> res = new LinkedList<>();

    /**
     * 主函数
     */
    List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        traverse(root);
        return res;
    }

    String traverse(TreeNode root) {
        // 对于空节点，可以用一个特殊字符表示
        if (root == null) {
            return "#";
        }
        // 将左右字数序列化为字符串
        String left = traverse(root.left);
        String right = traverse(root.right);
        /* 后序遍历代码位置 */
        // 左右子树加上自己，就是以自己为根的二叉树序列化结果
        String subTree = left + "," + right + "," + root.val;

        int freq = memo.getOrDefault(subTree, 0);
        // 多次重复也只会被加入结果集一次
        if (freq == 1) {
            res.add(root);
        }
        // 给子树对应的出现次数加一
        memo.put(subTree, freq + 1);
        return subTree;
    }

}
