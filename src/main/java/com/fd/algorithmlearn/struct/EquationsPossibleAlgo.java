package com.fd.algorithmlearn.struct;

/**
 * 990. 等式方程的可满足性 (中等)
 * https://leetcode-cn.com/problems/satisfiability-of-equality-equations/
 *
 * @author ：zxq
 * @date ：Created in 2021/4/13 17:40
 */

public class EquationsPossibleAlgo {
    public static boolean equationsPossible(String[] equations) {
        UF uf = new UF(26);
        for (String equation : equations) {
            if (equation.charAt(1) == '=') {
                char x = equation.charAt(0);
                char y = equation.charAt(3);
                uf.union(x - 'a', y - 'a');
            }
        }
        for (String equation : equations) {
            if (equation.charAt(1) == '!') {
                char x = equation.charAt(0);
                char y = equation.charAt(3);
                if (uf.connected(x - 'a', y - 'a')) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] ss = {"a==b", "b!=a"};
        System.out.println(equationsPossible(ss));

    }
}
