package com.fd.algorithmlearn;

import com.fd.algorithmlearn.linked.ListNode;
import com.fd.algorithmlearn.util.IUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

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
        PriorityQueue<Integer> max = new PriorityQueue<>((a, b) -> (Integer.compare(b, a)));
        max.offer(1);
        max.offer(2);
        max.offer(2);
        max.offer(3);
        System.err.println(max.peek());
        for (Integer integer : max) {
            System.err.print(integer);
        }

//        String s = "123";
//        System.err.println(s.substring(3));
//
//        Stack<String> stack = new Stack<>();
//        stack.push("123");
//        List<String> list = new ArrayList<>(stack);
//        stack.push("321");

//        print(list);
    }

    @Test
    void largestRectangleAreaTest(){
        int[] x = {1, 1};
        int res = test.largestRectangleArea(x);
        System.err.println(res);
    }

    @Test
    void reconstructQueueTest() {

        int[][] people = {
                {7, 0},
                {4, 4},
                {7, 1},
                {5, 0},
                {6, 1},
                {5, 2}};
        int[][] res = test.reconstructQueue(people);
        IUtil.print(res);

    }

    @Test
    void maxPointsTest() {
        int[][] input = {
                {1, 1},
                {3, 2},
                {5, 3},
                {4, 1},
                {2, 3},
                {1, 4}};

        int res = test.maxPoints(input);
        System.err.println(res);
    }

    @Test
    void largestNumberTest() {
        int[] x = {10, 2};
        String res = test.largestNumber(x);
        System.err.println(res);
    }

    @Test
    void TestMaxCoins() {
        int[] nums = {3, 1, 5, 8};
        int res = test.maxCoins(nums);
        System.err.println(res);
    }

    @Test
    public void TestWordBreakII() {

    }

    @Test
    public void TestWordBreak() {
        String s = "cars";
        String[] words = {"car", "ca", "rs"};

        boolean res = test.wordBreak(s, Arrays.asList(words));
        System.err.println(res);
    }

    @Test
    public void TestNumSquares() {
        int res = test.numSquares(12);
        System.err.println(res);
    }

    @Test
    void wiggleSortTest() {
        int[] x = {1, 5, 1, 1, 6, 4};
        test.wiggleSort(x);
        print(x);
    }

    @Test
    void isMatch2() {
        String s = "abbbbc";
        String p = "ab*d*c";
        System.err.println(test.isMatch2(s, p));
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