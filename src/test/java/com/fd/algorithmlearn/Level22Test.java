package com.fd.algorithmlearn;

import com.fd.algorithmlearn.entity.Node;
import com.fd.algorithmlearn.entity.TreeNode;
import com.fd.algorithmlearn.linked.ListNode;
import com.fd.algorithmlearn.util.IUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhangxinqiang
 * @create 2022/2/28 14:45
 */
class Level22Test {
    private Level22 test = new Level22();

    @Test
    public void lengthOfLIS() {

        int[] x = {10, 9, 2, 5, 3, 7, 101, 18};

//        int[] x = {1, 3, 6, 7, 9, 4, 10, 5, 6};
        int res = test.lengthOfLIS1(x);
        System.err.println(res);
    }

    @Test
    public void leastIntervalTest(){
//        char[] x = {'A','A','A','B','B','B'};
        char[] x = {'A','A','A','A','A','A','B','C','D','E','F','G'};
        int n = 2;
        int res = test.leastInterval(x, n);
        System.err.println(res);
    }

    @Test
    public void evalTest(){
        String[] x = {"2","1","+","3","*"};
        int res = test.evalRPN(x);
        System.err.println(res);
    }

    @Test
    public void fractionToDecimalTest(){
        String res = test.fractionToDecimal(4, 333);
        System.err.println(res);
    }

    @Test
    public void divideTest(){
        System.err.println(2%1);
//        int divide = test.divide(-2147483648, 1);
//        System.err.println(divide);
    }

    @Test
    public void sqrtTest(){
//        int x = 2147395599;
        int x = 1;
        int res = test.mySqrt(x);
        System.err.println(res);
    }

    @Test
    public void test() {
//        for (int i = 0; i < 26; i++) {
//            System.err.print("'"+(char) ('A'+i) +"'"+",");
//        }
        System.err.println('A' - 64);
        List<Integer> x = new ArrayList<>();
        x.add(1);
        x.add(1);
        x.add(1);
        x.add(2);
        x.add(3);
        Iterator<Integer> iterator = x.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            if (next.equals(1)){
                iterator.remove();
            }
        }
        x.forEach(System.out::println);
    }

    @Test
    public void myPowTest(){
        double res = test.myPow(2.0, 10);
        System.err.println(res);
    }

    @Test
    public void randomizedSetTest() {
        Level22.RandomizedSet set = new Level22.RandomizedSet();
        set.insert(0);
        set.insert(1);
        set.remove(0);
        set.insert(2);
        set.remove(1);
        set.insert(3);
        set.insert(4);
        set.insert(5);
        System.err.println(set.getRandom());
        System.err.println(set.getRandom());
        System.err.println(set.getRandom());
        System.err.println(set.getRandom());
        System.err.println(set.getRandom());
        System.err.println(set.getRandom());
    }

    @Test
    public void coinChange() {
        int[] x = new int[]{1, 2147483647};
        int res = test.coinChange(x, 2);

        System.err.println(res);
    }

    @Test
    public void sortColors() {
        int[] x = new int[]{2, 0, 2, 1, 1, 0};
        test.sortColors(x);
        System.err.println(Arrays.toString(x));
    }

    @Test
    public void mergeTest() {
        int[][] x = new int[][]{{1, 2}, {3, 9}, {8, 10}, {15, 18}};
        int[][] merge = test.merge(x);
        IUtil.print(merge);
    }

    @Test
    public void findKthLargestTest() {
//        int[] x = new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6};
        int[] x = new int[]{3, 2, 1, 5, 6, 4};
//        int res = test.findKthLargest(x, 4);
        int res = test.findKthLargest3(x, 2);
        System.err.println(res);
    }

    @Test
    void subsets() {
        int[] x = new int[]{1, 2, 3};
        List<List<Integer>> res = test.subsets1(x);

        res.forEach(System.err::println);
    }

    @Test
    void connect() {
        Integer[] root = new Integer[]{1, 2, 3, 4, 5, 6, 7};

        Node input = Node.build(root);
        Node res = test.connect(input);
        Node.printByNext(res);
    }

    @Test
    void generateParenthesis() {
        List<String> strings = test.generateParenthesis(3);
        System.err.println(strings);
    }

    @Test
    void kthTest() {

        Integer[] input = new Integer[]{3, 1, 4, null, 2};

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.left.right = new TreeNode(2);


        int res = test.kthSmallest(root, 1);
        System.err.println(res);
    }

    @Test
    void lengthOfLongestSubstringTest() {
        int res = test.lengthOfLongestSubstring("abba");
        System.err.println(res);
    }

    @Test
    void oddEvenList() {
        int[] x = new int[]{1, 2, 3, 4, 5};
        ListNode res = IUtil.buildNodes(x);
        IUtil.printNodes(res);

        ListNode node = test.oddEvenList(res);
        IUtil.printNodes(node);
    }

    @Test
    void buildTreeTest() {
        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] inorder = new int[]{9, 3, 15, 20, 7};

        TreeNode res = test.buildTree(preorder, inorder);
        IUtil.printNodes(res);
    }

    @Test
    void test1() {
        int res = Arrays.binarySearch(new int[]{1, 2, 3}, 2);
        System.err.println(res);
    }
}