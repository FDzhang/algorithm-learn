package com.fd.algorithmlearn.leetcode.everyday;

import java.util.*;

public class Solution {

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

//        String s = "abcde";
//        String[] ss = {"a", "bb", "acd", "ace"};
//
        Solution solution = new Solution();
//        int cnt = solution.numMatchingSubseq(s, ss);
//        System.err.println(cnt);

//        System.err.println(solution.toHex(15));
        System.err.println(solution.toHex(16));
    }


}
