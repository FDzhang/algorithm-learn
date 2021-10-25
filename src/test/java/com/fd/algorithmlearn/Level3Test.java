package com.fd.algorithmlearn;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangxinqiang
 * @create 2021/10/14 11:15
 */
class Level3Test {

    private Level3 test = new Level3();

    @Test
    void calculateTest() {
        String s = "3+2*2";
        int res = test.calculate1(s);
        System.err.println(res);
    }

    @Test
    void spiralOrderTest() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        List<Integer> list = test.spiralOrder(matrix);

        list.forEach(System.err::println);
    }

    @Test
    void gameOfLifeTest() {
        int[][] b = {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}};
        int[][] b1 = {{1, 1}, {1, 0}};
        int[][] b3 = {{0, 1, 0, 0, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 1},
                {1, 1, 0, 0, 1, 0, 0}};
        test.gameOfLife(b3);

        for (int[] ints : b3) {
            System.err.println(Arrays.toString(ints));
        }


    }

}