package com.fd.algorithmlearn;

import com.fd.algorithmlearn.util.IUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : zxq
 * @create : 2022/5/11 16:35
 */
class Level33Test {
    private Level33 test = new Level33();

    @Test
    void testFirstMissingPositive() {
        int[] x = {0,1,2};
        System.out.println(test.firstMissingPositive(x));
    }

    @Test
    void testLongestConsecutive(){
        int[] x = {0,3,7,2,5,8,4,6,0,1};

        System.err.println(test.longestConsecutive(x));
    }

    @Test
    void test1() {
//        int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] m = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        List<Integer> list = test.spiralOrder(m);
        IUtil.print(list);
    }


    @Test
    void test2() {
        Integer x = 0;
        System.err.println(x.equals(null));
    }

}