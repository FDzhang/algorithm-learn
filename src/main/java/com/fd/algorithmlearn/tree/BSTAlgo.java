package com.fd.algorithmlearn.tree;


import com.fd.algorithmlearn.entity.TreeNode;

/**
 * 98. 验证二叉搜索树
 * https://leetcode-cn.com/problems/validate-binary-search-tree/
 * <p>
 * 700. 二叉搜索树中的搜索
 * https://leetcode-cn.com/problems/search-in-a-binary-search-tree/
 * <p>
 * 701. 二叉搜索树中的插入操作
 * https://leetcode-cn.com/problems/insert-into-a-binary-search-tree/
 * <p>
 * 450. 删除二叉搜索树中的节点
 * https://leetcode-cn.com/problems/delete-node-in-a-bst/
 *
 * @author ：zxq
 * @date ：Created in 2021/4/2 10:41
 */

public class BSTAlgo {

    /*-----------------------验证二叉搜索树------------------------------------*/

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    public boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) {
            return true;
        }
        if (min != null && root.val <= min.val) {
            return false;
        }
        if (max != null && root.val >= max.val) {
            return false;
        }

        return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
    }

    /*--------------------------验证二叉搜索树---------------------------------*/


    /*--------------------------二叉搜索树中的搜索---------------------------------*/

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        } else if (root.val < val) {
            return searchBST(root.right, val);
        } else {
            return searchBST(root.left, val);
        }
    }

    /*--------------------------二叉搜索树中的搜索---------------------------------*/


    /*--------------------------二叉搜索树中的插入操作---------------------------------*/

    public TreeNode insertIntoBST(TreeNode root, int val) {
        // 找到空位置插入新节点
        if (root == null) {
            return new TreeNode(val);
        }

        if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }

    /*--------------------------二叉搜索树中的插入操作---------------------------------*/

    /*--------------------------删除二叉搜索树中的节点---------------------------------*/

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val == key) {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            TreeNode minNode = getMinNode(root.right);
            root.val = minNode.val;
            root.right = deleteNode(root.right, minNode.val);
        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else {
            root.right = deleteNode(root.right, key);
        }
        return root;
    }

    private TreeNode getMinNode(TreeNode root) {
        // BST 最左边的就是最小的
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    /*--------------------------删除二叉搜索树中的节点---------------------------------*/

}
