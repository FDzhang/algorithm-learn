package com.fd.algorithmlearn.tree;

/**
 * @author ：zxq
 * @date ：Created in 2021/3/15 17:45
 */

public class TreeUtil {


    public static void print(TreeNode root){

        if (root == null){
            return;
        }

        System.out.println(root.val);

        print(root.left);
        print(root.right);
    }


}
