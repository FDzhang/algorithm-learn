package com.fd.algorithmlearn.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 51. N 皇后
 * https://leetcode-cn.com/problems/n-queens/
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * <p>
 * 链接：https://leetcode-cn.com/problems/n-queens
 * <p>
 * 思路：回溯算法
 * 1、套用回溯算法框架 （路径， 选择列表， 结束条件）
 * 2、无效选择的判定
 * <p>
 * 路径：board 中小于row的行都已经成功放置了皇后
 * 选择列表：第row行的所有列都是放置皇后的选择
 * 结束条件：row超过board的最后一行
 *
 * @author zhangxinqiang
 * @create 2021/8/25 11:24
 */
public class SolveNQueens {
    public static void main(String[] args) {
        SolveNQueens s = new SolveNQueens();

        System.err.println(s.solveNQueens(8).size());
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();

        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }

        backtrack(board, 0, res);
        return res;
    }

    private List<String> build(char[][] board) {
        List<String> list = new ArrayList<>();
        for (char[] chars : board) {
            list.add(new String(chars));
        }
        return list;
    }

    private void backtrack(char[][] board, int row, List<List<String>> res) {
        if (row == board.length) {
            res.add(build(board));
            return;
        }

        int n = board[row].length;
        for (int col = 0; col < n; col++) {
            // 排除不合法选择
            if (!valid(board, row, col)) {
                continue;
            }
            // 做选择
            board[row][col] = 'Q';
            // 进入下一层决策
            backtrack(board, row + 1, res);
            // 撤销选择
            board[row][col] = '.';
        }
    }
    /* 是否可以在 board[row][col] 放置皇后？ */
    private boolean valid(char[][] board, int row, int col) {
        // 检查列是否有皇后互相冲突
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') {
                return false;
            }
        }
        // 检查 右上方 是否有皇后互相冲突
        for (int i = row - 1, j = col + 1; i >= 0 && j < board[row].length; i--, j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        // 检查 左上方 是否有皇后互相冲突
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }
}
