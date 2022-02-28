package com.fd.algorithmlearn;

import org.junit.jupiter.api.Test;

/**
 * @author zhangxinqiang
 * @create 2022/2/28 14:45
 */
class Level22Test {
    private Level22 test = new Level22();

    @Test
    void lengthOfLongestSubstringTest() {
        int res = test.lengthOfLongestSubstring("abba");
        System.err.println(res);
    }

}