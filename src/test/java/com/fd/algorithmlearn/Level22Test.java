package com.fd.algorithmlearn;

import com.fd.algorithmlearn.entity.TreeNode;
import com.fd.algorithmlearn.linked.ListNode;
import com.fd.algorithmlearn.util.IUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author zhangxinqiang
 * @create 2022/2/28 14:45
 */
class Level22Test {
    private Level22 test = new Level22();

    @Test
    void lengthOfLongestSubstringTest() {
        int res = test.lengthOfLongestSubstring("abba");
        System.err.println(res);
    }

    @Test
    void oddEvenList() {
        int[] x = new int[]{1,2,3,4,5};
        ListNode res = IUtil.buildNodes(x);
        IUtil.printNodes(res);

        ListNode node = test.oddEvenList(res);
        IUtil.printNodes(node);
    }

    @Test
    void buildTreeTest(){
       int[] preorder = new int[]{3,9,20,15,7};
       int[] inorder = new int[]{9,3,15,20,7};

        TreeNode res = test.buildTree(preorder, inorder);
        IUtil.printNodes(res);
    }

    @Test
    void test1(){
        int res = Arrays.binarySearch(new int[]{1, 2, 3}, 2);
        System.err.println(res);
    }
}