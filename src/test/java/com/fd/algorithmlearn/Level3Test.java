package com.fd.algorithmlearn;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author zhangxinqiang
 * @create 2021/10/14 11:15
 */
class Level3Test {

    private Level3 test = new Level3();

    @Test
    void spiralOrderTest() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        List<Integer> list = test.spiralOrder(matrix);

        list.forEach(System.err::println);
    }

}