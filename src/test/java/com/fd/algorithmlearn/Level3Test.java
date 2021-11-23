package com.fd.algorithmlearn;

import com.fd.algorithmlearn.linked.ListNode;
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
    void test() {
//        Map<Integer, Integer> map = new HashMap<>();
//        map.put(1, 1);
//        System.err.println(map.get(1));
//        System.err.println(map.get(null));

        String s = "123";

        System.err.println(s.substring(3));
//
//        Stack<String> stack = new Stack<>();
//        stack.push("123");
//        List<String> list = new ArrayList<>(stack);
//        stack.push("321");

//        print(list);
    }

    @Test
    void isMatchTest() {
        String s = "acdcb";
        String p = "a*c?b";
        System.err.println(test.isMatch(s, p));
    }

    @Test
    void countSmaller() {
        int[] nums = {5, 2, 6, 1};
        int[] nums1 = {26, 78, 27, 100, 33, 67, 90, 23, 66, 5, 38, 7, 35, 23, 52, 22, 83, 51, 98, 69, 81, 32, 78, 28, 94, 13, 2, 97, 3, 76, 99, 51, 9, 21, 84, 66, 65, 36, 100, 41};

        List<Integer> res = test.countSmaller(nums1);
        print(res);
    }

    void print(int[] list) {
        System.err.println("------------------------------");
        for (int i : list) {
            System.err.print(i + "\t");
        }
        System.err.println();
    }

    <T> void print(List<T> list) {
        System.err.println("------------------------------");
        for (T i : list) {
            System.err.print(i + " ");
        }
    }

    @Test
    void bFind() {
        List<Integer> list = new ArrayList<>();
        list.add(-1);
        list.add(1, 2);

        int res = test.bFind(list, -1);
        System.err.println(list);
    }

    @Test
    void longestIncreasingPathTest() {
        int[][] matrix = {{7, 7, 5}, {2, 4, 6}, {8, 2, 0}};
        int res = test.longestIncreasingPath(matrix);
        System.err.println(res);
    }

    @Test
    void findCircleNumTest() {
        int[][] x = {{1, 0, 0, 1},
                {0, 1, 1, 0},
                {0, 1, 1, 1},
                {1, 0, 1, 1}};

        int res = test.findCircleNum(x);
        System.err.println(res);
    }


    @Test
    void maxPathSumTest() {
        Integer[] vals = {-10, 9, 20, null, null, 15, 7};
        Integer[] vals1 = {-2, -1};
        TreeNode head = TreeNode.buildByArray(vals1);
        int max = test.maxPathSum(head);
        System.err.println(max);
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