package com.fd.algorithmlearn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhangxinqiang
 * @create 2022/1/6 11:35
 */
class Level1Test {

    private Level1 test = new Level1();

    @Test
    void test(){
        System.err.println((-2147483648 - 8));
    }

    @Test
    void myAtoi(){
        String s = "2147483648";
        int res = test.myAtoi(s);
        System.err.println(res);
    }

    @Test
    void singleNumber1() {
        int[] x = {2,2,1};
        int res = test.singleNumber1(x);
        System.err.println(res);
    }
}