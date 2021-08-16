package com.fd.algorithmlearn;

import org.junit.jupiter.api.Test;

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
}