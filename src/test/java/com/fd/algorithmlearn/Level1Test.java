package com.fd.algorithmlearn;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
    void test1(){
        int res = test.hammingWeight(-3);
        System.err.println(res);
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


    @Test
    void shuffleTest(){
        int[] nums  = new int[]{1,2,3};



        Level1.Solution obj = new Level1.Solution(nums);
        int[] param_1 = obj.reset();
        System.err.println(Arrays.toString(param_1));

        int[] param_2 = obj.shuffle();
        System.err.println(Arrays.toString(param_2));

        for (int i = 0; i < param_1.length; i++) {
            param_1[i] = i;
        }
        System.err.println(Arrays.toString(param_1));
        int[] param_3 = obj.reset();
        System.err.println(Arrays.toString(param_3));


        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
        System.err.println(Arrays.toString(nums));
        int[] param_4 = obj.reset();
        System.err.println(Arrays.toString(param_4));

    }
}