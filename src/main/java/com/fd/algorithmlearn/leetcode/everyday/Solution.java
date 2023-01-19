package com.fd.algorithmlearn.leetcode.everyday;

import java.util.*;

public class Solution {
    static class FreqStack {
        Map<Integer, Integer> map;
        PriorityQueue<int[]> stack;

        int op;

        public FreqStack() {
            op = 0;
            map = new HashMap<>();
            stack = new PriorityQueue<>((a, b) -> b[1] == a[1] ? Integer.compare(b[2], a[2]) : Integer.compare(b[1], a[1]));
        }

        public void push(int val) {
            if (!map.containsKey(val)) {
                map.put(val, 0);
            }
            map.put(val, map.get(val) + 1);
            stack.add(new int[]{val, map.get(val), ++op});
        }

        public int pop() {
            int[] top = stack.poll();
            map.put(top[0], map.get(top[0]) - 1);
            return top[0];
        }

        public static void main(String[] args) {
            FreqStack freqStack = new FreqStack();
            freqStack.push(5);
            freqStack.push(7);
            freqStack.push(5);
            freqStack.push(7);
            freqStack.push(4);
            freqStack.push(5);
            System.err.println(freqStack.pop());
            System.err.println(freqStack.pop());
            System.err.println(freqStack.pop());
            System.err.println(freqStack.pop());
        }
    }

    /**
     * 1、路径：已经走过的路径
     * 2、选择列表：可以连接的节点。
     * 3、结束条件：达到长度n
     */
    class Node {
        public int max = 0;
        public boolean arrived = false;
    }

    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        // 连接关系
        Map<String, int[]> res = new HashMap<>();
        Map<Integer, List<Integer>> select = new HashMap<>();
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            int cnt = edge[2];

            res.put(a + "-" + b, new int[]{0, cnt});
            res.put(b + "-" + a, new int[]{0, cnt});
            if (!select.containsKey(a)) {
                select.put(a, new ArrayList<>());
            }
            if (!select.containsKey(b)) {
                select.put(b, new ArrayList<>());
            }
            select.get(a).add(b);
            select.get(b).add(a);
        }
        Set<Integer> path = new LinkedHashSet<>();
        Node[] nodeCnt = new Node[n];
        for (int i = 0; i < nodeCnt.length; i++) {
            nodeCnt[i] = new Node();
        }
        nodeCnt[0].arrived = true;
        nodeCnt[0].max = maxMoves;
        path.add(0);
        reachable(0, path, select, maxMoves, res, nodeCnt);

        int ans = 0;
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            int cnt = edge[2];

            String ab = a + "-" + b;
            String ba = b + "-" + a;
            ans += Math.min(cnt, res.get(ab)[0] + res.get(ba)[0]);

            if (res.get(ab)[0] + res.get(ba)[0] < cnt) {
                System.err.println(ab + Arrays.toString(res.get(ab)) + Arrays.toString(res.get(ba)));
            }
        }
        int nCnt = 0;
        for (Node b : nodeCnt) {
            if (b.arrived) {
                nCnt++;
            }
        }
        return ans + nCnt;
    }

    private void reachable(int cur, Set<Integer> path, Map<Integer, List<Integer>> select, int maxMoves,
                           Map<String, int[]> res, Node[] nodeCnt) {
        System.err.println(path + ", " + maxMoves);
        if (maxMoves < 0) {
            return;
        }
        nodeCnt[cur].arrived = true;
        if (cur != 0 && maxMoves <= nodeCnt[cur].max) {
            return;
        }
        nodeCnt[cur].max = maxMoves;

        if (maxMoves == 0) {
            return;
        }
        if (select.get(cur) == null) {
            return;
        }
        for (Integer next : select.get(cur)) {
            String key = cur + "-" + next;
            res.get(key)[0] = Math.min(Math.max(maxMoves, res.get(key)[0]), res.get(key)[1]);
            if (path.contains(next)) {
                continue;
            }
            path.add(next);
            reachable(next, path, select, maxMoves - 1 - res.get(key)[1], res, nodeCnt);
            path.remove(next);
        }
    }


    /**
     * 100
     * 0.25 * 1 +
     */
    public double soupServings(int n) {
        n = (int) Math.ceil((double) n / 25);
        if (n >= 179) {
            return 1.0;
        }
        double[][] dp = new double[n + 1][n + 1];
        dp[0][0] = 0.5;
        for (int i = 1; i <= n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                double a = dp[Math.max(i - 4, 0)][j];
                double b = dp[Math.max(i - 3, 0)][Math.max(j - 1, 0)];
                double c = dp[Math.max(i - 2, 0)][Math.max(j - 2, 0)];
                double d = dp[Math.max(i - 1, 0)][Math.max(j - 3, 0)];
                dp[i][j] = 0.25 * (a + b + c + d);
            }
        }
        return dp[n][n];
    }

    public int largestAltitude(int[] gain) {
        int ans = 0;
        int now = 0;
        for (int g : gain) {
            now += g;
            ans = Math.max(ans, now);
        }
        return ans;
    }

    static final char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    public String toHex(int x) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int y = cut(x, i);
            sb.append(to16(y));
        }
        return sb.reverse().toString();
    }

    /**
     * 1111
     * 1111 0000
     * 0001 0000
     * 0001 0000
     */
    private int cut(int x, int i) {
        int m = 15;
        return (x >> (i * 4)) & m;
    }

    public char to16(int num) {
        return digits[num];
    }


    /**
     * 题解：https://leetcode.cn/problems/sum-of-subsequence-widths/solutions/1977682/by-endlesscheng-upd1/
     */
    public int sumSubseqWidths(int[] nums) {
        int mod = (int) 1e9 + 7;
        Arrays.sort(nums);
        int n = nums.length;
        int[] p2 = new int[n];
        p2[0] = 1;
        for (int i = 1; i < n; i++) {
            p2[i] = p2[i - 1] * 2 % mod;
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += (long) (p2[i] - p2[n - 1 - i]) * nums[i];
        }
        return (int) (ans % mod + mod) % mod;
    }

    public int numMatchingSubseq(String s, String[] words) {
        Deque<String>[] dq = new Deque[26];
        for (int i = 0; i < dq.length; i++) {
            dq[i] = new ArrayDeque<>();
        }

        for (String word : words) {
            dq[word.charAt(0) - 'a'].add(word);
        }

        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int size = dq[c - 'a'].size();
            for (int j = 0; j < size; j++) {
                String word = dq[c - 'a'].removeFirst();
                if (word.length() == 1) {
                    ans++;
                } else {
                    dq[word.charAt(1) - 'a'].offerLast(word.substring(1));
                }
            }
        }
        return ans;
    }

//    public int numMatchingSubseq(String s, String[] words) {
//        int cnt = 0;
//        Map<Character, List<Integer>> maps = buildMap(s);
//        for (String word : words) {
//            if (isSub(word, maps)) {
//                cnt++;
//            }
//        }
//        return cnt;
//    }

    private Map<Character, List<Integer>> buildMap(String s) {
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), new ArrayList<>());
            }
            map.get(s.charAt(i)).add(i);
        }
        return map;
    }

    /**
     * true: word 可以在 map中找到
     * 1、查找word[i]在mapKeys中是否存在，不存在则返回false，存在则继续
     * 2、查找word[i] 从当前下标开始，在mapValues中的下标，找不到则返回false，存在则继续
     */
    private boolean isSub(String word, Map<Character, List<Integer>> map) {
        int pre = -1;
        for (int i = 0; i < word.length(); i++) {
            List<Integer> idx = map.get(word.charAt(i));
            if (idx == null) {
                return false;
            }
            pre = find(pre, idx);
            if (pre == -2) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在index中找到第一个比pre大的值
     * 如果找不到则返回 -2
     */
    private int find(int pre, List<Integer> idx) {
        int i = 0;
        int j = idx.size() - 1;
        while (i < j) {
            int mIdx = (i + j) / 2;
            Integer mid = idx.get(mIdx);
            if (mid > pre) {
                j = mIdx;
            } else {
                i = mIdx + 1;
            }
        }
        return idx.get(j) > pre ? idx.get(j) : -2;
    }

    public static void main(String[] args) {
//        System.err.println(Math.ceil(2.5));

//        String s = "abcde";
//        String[] ss = {"a", "bb", "acd", "ace"};
//
        Solution solution = new Solution();
        int[][] edges = new int[][]{{0, 1, 10}, {0, 2, 1}, {1, 2, 2}};
//        int[][] edges = new int[][]{{0, 1, 4}, {1, 2, 6}, {0, 2, 8}, {1, 3, 1}};

//        int[][] edges = new int[][]{{4, 6, 11}, {5, 6, 2}, {2, 6, 11}, {0, 3, 19},
//                {1, 7, 21}, {5, 7, 1}, {1, 5, 4}, {0, 7, 12}, {6, 7, 3}, {3, 6, 22},
//                {0, 5, 24}, {1, 2, 8}, {3, 7, 11},
//                {1, 3, 14}, {4, 5, 7}, {4, 7, 14}, {0, 4, 5}, {2, 4, 7}, {3, 4, 11},
//                {3, 5, 15}, {2, 5, 13}, {2, 3, 6}, {1, 4, 6}, {0, 2, 3}, {1, 6, 20}};
        System.err.println(solution.reachableNodes(edges, 6, 3));

//        int cnt = solution.numMatchingSubseq(s, ss);
//        System.err.println(cnt);
//        System.err.println(solution.soupServings(200 * 25));

//        System.err.println(solution.toHex(15));
//        System.err.println(solution.toHex(16));
    }


}
