package com.fd.algorithmlearn;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhangxinqiang
 * @create 2021/8/16 13:52
 */
class Level2Test {
    private Level2 test = new Level2();

    @Test
    void test1() {

        int[] a = {1, 2};
        int[] b = {2, 1};
        test.buildTree(a, b);
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
    void subsetsTest(){
        int[] b = {1, 2,3};
        List<List<Integer>> subsets = test.subsets(b);

        System.err.println(subsets);
    }

    @Test
    void findKthLargestTest(){
        int[] b = {3,2,3,1,2,4,5,5,6};
        int res = test.findKthLargest(b, 4);

        System.err.println(res);
    }

    @Test
    void searchRangeTest(){
        int[] b = {1,1,2};
        int[] res = test.searchRange(b, 1);

        System.err.println(Arrays.toString(res));
    }

    @Test
    void mergeTest(){
        int[][] b = {{1,4},{2,3}};
        int[][] merge = test.merge(b);

        for (int[] ints : merge) {
            System.err.println(Arrays.toString(ints));
        }
    }

}