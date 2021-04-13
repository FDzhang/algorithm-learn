package com.fd.algorithmlearn.struct;

import java.util.Arrays;

/**
 * 130. 被围绕的区域 (中等)
 * https://leetcode-cn.com/problems/surrounded-regions/
 * <p>
 * <p>
 * [["X","O","X","X"],
 * ["O","X","O","X"],
 * ["X","O","X","O"],
 * ["O","X","O","X"]]
 *
 * @author ：zxq
 * @date ：Created in 2021/4/13 16:34
 */

public class SolveAlgo {

    public static void solve(char[][] board) {
        if (board.length == 0) {
            return;
        }

        int m = board.length;
        int n = board[0].length;

        // 给dummy留一个额外的位置
        UF uf = new UF(m * n + 1);
        int dummy = m * n;
        // 将首列和末列的O与dummy连通
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                uf.union(i * n, dummy);
            }
            if (board[i][n - 1] == 'O') {
                uf.union(i * n + n - 1, dummy);
            }
        }
        // 将首行和末行的O与dummy联通
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                uf.union(j, dummy);
            }
            if (board[m - 1][j] == 'O') {
                uf.union(n * (m - 1) + j, dummy);
            }
        }
        // System.out.println(Arrays.toString(uf.parent));


        // 方向数组d是上下左右搜索的常用手法
        int[][] d = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (board[i][j] == 'O') {
                    //将此O与上下左右的O连通
                    for (int k = 0; k < 4; k++) {
                        int x = i + d[k][0];
                        int y = j + d[k][1];
                        if (board[x][y] == 'O') {
                            uf.union(x * n + y, i * n + j);
                        }
                    }
                }
            }
        }

        // 所有不和dummy连通的O，都要被替换
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (!uf.connected(dummy, i * n + j)) {
                    board[i][j] = 'X';
                }
            }
        }

        //System.out.println(Arrays.toString(uf.parent));
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'X', 'O', 'X', 'X'},
                {'O', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'O'},
                {'O', 'X', 'O', 'X'}
        };
        print(board);
        solve(board);
        System.out.println();
        print(board);

    }
    private static void print(char[][] board){
        for (char[] chars : board) {
            System.out.println(Arrays.toString(chars));
        }
    }
}
