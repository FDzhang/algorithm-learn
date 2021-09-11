package com.fd.algorithmlearn.rank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhangxinqiang
 * @create 2021/9/11 15:17
 */
class LeetCodeRankTest {
    LeetCodeRank test = new LeetCodeRank();


    @Test
    void minimumSwitchingTimesTest(){
        int[][] source = {{1,3},{5,4}};

        int[][] target = {{3,1},{6,5}};


        int[][]  source1 = {{1,2,3},{3,4,5}};
        int[][]  target1 = {{1,3,5},{2,3,4}};


        int i = test.minimumSwitchingTimes(source1, target1);



        System.err.println(i);
    }

    @Test
    void maxmiumScoreTest(){
        int[] cards = {1, 4, 11, 11, 12, 12};
        int[] cards2 = {1,2,8,9};
        int cnt = 3;

        int[]  cards1 = {3,3,1};
        int cnt1 = 1;
        int i = test.maxmiumScore(cards, cnt);
        System.err.println(i);
    }

    @Test
    void lenTest(){
        double len = test.len(1, 1, 4, 5);

        System.err.println(len);
    }
}