package com.fd.algorithmlearn;


import org.junit.jupiter.api.Test;

/**
 * @author : zxq
 * @create : 2022/5/18 11:43
 */
public class LeetCodeFindTest {
    private LeetCodeFind test = new LeetCodeFind();

    @Test
    void testRob() {
        int[] x = {1, 2, 3, 1};
        int res = test.rob(x);
        System.err.println(res);
    }

}