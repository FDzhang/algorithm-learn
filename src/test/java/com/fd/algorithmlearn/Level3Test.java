package com.fd.algorithmlearn;

import com.fd.algorithmlearn.linked.ListNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangxinqiang
 * @create 2021/10/14 11:15
 */
class Level3Test {

    private Level3 test = new Level3();

    @Test
    void test() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        System.err.println(map.get(1));
        System.err.println(map.get(null));
    }


    @Test
    void solveTest() {

        char[][] board = {{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'X', 'X'}};
        print(board);

        test.solve(board);

        print(board);
    }

    private void print(char[][] board) {
        System.err.println("------------------------------");
        for (char[] chars : board) {
            System.err.println(Arrays.toString(chars));
        }
    }

    @Test
    void ladderLengthTest() {
        String begin = "hit";
        String end = "cog";
        List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log");
        int i = test.ladderLength(begin, end, wordList);
        System.err.println(i);
    }

    @Test
    void calculateTest() {
        String s = "3+2*2";
        int res = test.calculate1(s);
        System.err.println(res);
    }

    @Test
    void minWindowTest() {

        String s = "ADOBECODEBANC";
        String t = "ABC";
//        String s = "bba";
//        String t = "ab";
//        String s = "aa";
//        String t = "aa";
        String res = test.minWindow1(s, t);
        System.err.println(res);
    }

    @Test
    void mergeKListsTest() {
        //[[-2,-1,-1,-1],[]]
        ListNode[] listNodes = new ListNode[2];
        listNodes[0] = bNode(new int[]{-2, -1, -1, -1});
        listNodes[1] = bNode(new int[]{});

        ListNode node = test.mergeKLists(listNodes);
        nodePrint(node);

    }

    void nodePrint(ListNode head) {
        while (head != null) {
            System.err.print(head.val + " ");
            head = head.next;
        }
    }

    ListNode bNode(int[] values) {
        ListNode res = new ListNode();
        ListNode p = res;
        for (int value : values) {
            ListNode node = new ListNode();
            node.val = value;

            p.next = node;
            p = p.next;
        }
        return res.next;
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