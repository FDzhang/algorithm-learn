package com.fd.algorithmlearn;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangxinqiang
 * @create 2021/8/16 13:52
 */
public class Level2Test {
    private Level2 test = new Level2();

    @Test
    void test1() {

        int[] a = {1, 2};
        int[] b = {2, 1};
        test.buildTree(a, b);
    }

    @Test
    void test() {
        StringBuilder sb = new StringBuilder();

        sb.append("123");
        sb.insert(3, "x");

        System.err.println(sb.toString());
    }

    @Test
    void majorityElement2Test() {
        int[] nums = {3, 3, 3, 3, 1, 1, 1, 2, 2, 2};
        int res = test.majorityElement2(nums);
        System.err.println(res);
    }

    @Test
    void leastIntervalTest() {
        char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B'};
        int res = test.leastInterval(tasks, 0);
        System.err.println(res);
    }

    @Test
    void fractionToDecimalTest() {
        String res = test.fractionToDecimal(0, -5);
        System.err.println(res);
    }

    @Test
    void titleToNumberTest() {
        int res = test.titleToNumber("AB");
        System.err.println(res);
    }

    @Test
    void myPowTest() {
        double res = test.myPow(2, 1);
        System.err.println(res);
    }


    @Test
    void isHappyTest() {
        boolean happy = test.isHappy(19);
        System.err.println(happy);
    }

    @Test
    void coinChangeTest() {
        int[] coins = {1, 2, 5};
        int amount = 11;

        int[] coins1 = {2};
        int amount1 = 3;

        int[] coins2 = {1, 2147483647};
        int amount2 = 2;

        int res = test.coinChange1(coins2, amount2);

        System.err.println(res);
    }

    @Test
    void uniquePathsTest() {
        int m = 7;
        int n = 3;

        int m1 = 1;
        int n1 = 1;

        int m2 = 3;
        int n2 = 3;

        int res = test.uniquePaths1(m, n);
        System.err.println(res);
    }


    @Test
    void canJumpTest() {
        int[] x = {2, 3, 1, 1, 4};
        boolean b = test.canJump(x);
        System.err.println(b);
    }


    @Test
    void letterCombinationsTest() {
        List<String> strings = test.letterCombinations("");

        System.err.println(strings);
    }

    @Test
    void generateParenthesisTest() {
        List<String> strings = test.generateParenthesis(3);

        System.err.println(strings);
    }

    @Test
    void subsetsTest() {
        int[] b = {1, 2, 3};
        List<List<Integer>> subsets = test.subsets(b);

        System.err.println(subsets);
    }

    @Test
    void findKthLargestTest() {
        int[] b = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int res = test.findKthLargest(b, 4);

        System.err.println(res);
    }

    @Test
    void searchRangeTest() {
        int[] b = {1, 1, 2};
        int[] res = test.searchRange(b, 1);

        System.err.println(Arrays.toString(res));
    }

    @Test
    void mergeTest() {
        int[][] b = {{1, 4}, {2, 3}};
        int[][] merge = test.merge(b);

        for (int[] ints : merge) {
            System.err.println(Arrays.toString(ints));
        }
    }

}