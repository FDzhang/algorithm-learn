//package com.fd.algorithmlearn.tree;
//
///**
// * @author ：zxq
// * @date ：Created in 2021/3/26 17:42
// */
//
//public class DeleteNodeAlgo {
//    public TreeNode deleteNode(TreeNode root, int key) {
//        return null;
//    }
//
//    public TreeNode delete(TreeNode root, int key) {
//        if (root.val == key) {
//            if (root.left == null) {
//                return root.right;
//            }
//            if (root.right == null) {
//                return root.left;
//            }
//
////            root.val = getMin(root.right);
//            root.right = deleteNode(root.right, root.val);
//
//        } else if (root.val > key) {
//            root.left = deleteNode(root.left, key);
//        } else if (root.val < key) {
//            root.right = deleteNode(root.right, key);
//        }
//    }
//}
