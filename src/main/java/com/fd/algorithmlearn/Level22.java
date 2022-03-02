package com.fd.algorithmlearn;

import java.util.*;

/**
 * 中级算法
 * LeetCode 官方推出的经典面试题目清单 —— 「中级算法 - 巩固训练」
 *
 * @author zhangxinqiang
 * @create 2022/2/24 11:18
 */
public class Level22 {
    // -------------------------- 数组和字符串 ------------------------------

    // 数组和字符串
    // 数组和字符串问题在面试中出现频率很高，你极有可能在面试中遇到。
    //
    // 我们推荐以下题目：字母异位词分组，无重复字符的最长子串 和 最长回文子串。

    /**
     * 最长回文子串
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     * <p>
     * 示例 1：
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     * <p>
     * 提示：
     * 1 <= s.length <= 1000
     * s 仅由数字和英文字母组成
     * <p>
     * 相关标签 字符串 动态规划
     * <p>
     * 思路1：
     * 1、遍历 字符串s, 每次都都向两边搜索 (定义max[3]{回文串的长度len，回文串的左下标i，回文串的右下标j})
     * a、搜索长度为奇数的最长回文串，记录 t1 {len1, i, j}
     * b、搜索长度为偶数的最长回文串，记为 t2 {len2, i, j}
     * c、依据t1[0] 和 t2[0]，令 max 等于 t1 or t2
     * 2、返回 s.substring(max[1], max[2] + 1);
     *
     * 思路2：动态规划
     * 思路3：马拉车
     */
    // 中心扩散 version 2
    public String longestPalindrome(String s) {
        char[] cs = s.toCharArray();

        int[] max = new int[3];
        for (int k = 1; k < cs.length; k++) {
            int[] t1 = longestPalindrome(cs, k - 1, k);
            int[] t2 = longestPalindrome(cs, k - 1, k + 1);

            if (t1[0] > max[0]) {
                max = t1;
            }
            if (t2[0] > max[0]) {
                max = t2;
            }
        }
        return s.substring(max[1], max[2] + 1);
    }

    public int[] longestPalindrome(char[] cs, int i, int j) {
        int[] res = new int[3];
        while (i >= 0 && i < j && j < cs.length) {
            if (cs[i] != cs[j]) {
                break;
            }
            res[1] = i;
            res[2] = j;
            res[0] = j - i + 1;
            i--;
            j++;
        }
        return res;
    }

    // 动态规划
    public String longestPalindrome2(String s) {
        // 中心扩散的方法，其实做了很多重复计算。动态规划就是为了减少重复计算的问题。动态规划听起来很高大上。
        // 其实说白了就是空间换时间，将计算结果暂存起来，避免重复计算。作用和工程中用 redis 做缓存有异曲同工之妙。
        // 我们用一个 boolean dp[l][r] 表示字符串从 i 到 j 这段是否为回文。试想如果 dp[l][r]=true，
        // 我们要判断 dp[l-1][r+1] 是否为回文。只需要判断字符串在(l-1)和（r+1)两个位置是否为相同的字符，是不是减少了很多重复计算。
        // 进入正题，动态规划关键是找到初始状态和状态转移方程。
        // 初始状态，l=r 时，此时 dp[l][r]=true。
        // 状态转移方程，dp[l][r]=true 并且(l-1)和（r+1)两个位置为相同的字符，此时 dp[l-1][r+1]=true。
        if (s == null || s.length() < 2) {
            return s;
        }
        int strLen = s.length();
        int maxStart = 0;  //最长回文串的起点
        int maxEnd = 0;    //最长回文串的终点
        int maxLen = 1;  //最长回文串的长度
        boolean[][] dp = new boolean[strLen][strLen];
        for (int r = 1; r < strLen; r++) {
            for (int l = 0; l < r; l++) {
                if (s.charAt(l) == s.charAt(r) && (r - l <= 2 || dp[l + 1][r - 1])) {
                    dp[l][r] = true;
                    if (r - l + 1 > maxLen) {
                        maxLen = r - l + 1;
                        maxStart = l;
                        maxEnd = r;
                    }
                }
            }
        }
        return s.substring(maxStart, maxEnd + 1);
    }

    // 中心扩散 version 1
    public String longestPalindrome1(String s) {
        String res = "";

        for (int i = 0; i < s.length(); i++) {
            String s1 = isPalindrome(s, i, i);
            String s2 = isPalindrome(s, i, i + 1);

            res = res.length() > s1.length() ? res : s1;
            res = res.length() > s2.length() ? res : s2;
        }

        return res;
    }

    public String isPalindrome(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return s.substring(l + 1, r);
    }

    /**
     * 无重复字符的最长子串
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     * <p>
     * "abba"
     * <p>
     * 提示：
     * 0 <= s.length <= 5 * 104
     * s 由英文字母、数字、符号和空格组成
     * <p>
     * 相关标签 哈希表 字符串 滑动窗口
     * <p>
     * 思路1：哈希+滑动窗口
     * <p>
     * 思路2：数组哈希+滑动窗口
     * 1、int[128] : 记录字符最近出现的位置
     * 2、遍历s
     * a、判断是否需要缩小窗口的左边界
     * b、res = Max(res, 右边界-左边界+1)
     * c、int[cs[i]]=i+1
     * 3、返回res
     */
    public int lengthOfLongestSubstring(String s) {
        int[] map = new int[128];
        char[] cs = s.toCharArray();

        int res = 0;
        int minIndex = 0;
        for (int i = 0; i < cs.length; i++) {
            minIndex = Math.max(minIndex, map[cs[i]]);
            res = Math.max(res, i - minIndex + 1);
            map[cs[i]] = i + 1;
        }
        return res;
    }

    public int lengthOfLongestSubstring2(String s) {
        Map<Character, Integer> map = new HashMap<>(128);
        char[] cs = s.toCharArray();

        int res = 0;
        int minIndex = -1;
        for (int i = 0; i < cs.length; i++) {
            if (map.containsKey(cs[i])) {
                minIndex = Math.max(minIndex, map.get(cs[i]));
            }
            res = Math.max(res, i - minIndex);

            map.put(cs[i], i);
        }
        return res;
    }


    public int lengthOfLongestSubstring1(String s) {
        Map<Character, Integer> map = new HashMap<>(128);
        int left = 0;
        int right = 0;
        int max = 0;

        char[] cs = s.toCharArray();
        while (right < cs.length) {
            char cr = cs[right++];
            map.put(cr, map.getOrDefault(cr, 0) + 1);

            while (map.get(cr) > 1) {
                char cl = cs[left++];
                map.put(cl, map.get(cl) - 1);
            }

            max = Math.max(max, right - left);
        }
        return max;
    }

    /**
     * 字母异位词分组
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * <p>
     * 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。
     * <p>
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * <p>
     * 提示：
     * 1 <= strs.length <= 104
     * 0 <= strs[i].length <= 100
     * strs[i] 仅包含小写字母
     * <p>
     * 相关标签 哈希表 字符串 排序
     * <p>
     * 思路1：哈希+排序
     * 1、map<key, values> : 字母异位词有相同的key, 将具有相同的key的str加入values
     * 2、字母异位词的判断：将str转为char[], 对char[]排序后，再转为字符串即为key
     * 3、返回 new ArrayList<>(map.values());
     * ps: 字母异位词的判断：也可以通过计数每个字符出现的次数来作为key
     * ps2: 字母异位词的判断：可以通过26个素数 乘以 字符出现的次数，来做为key
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] cs = str.toCharArray();
            Arrays.sort(cs);
            String key = new String(cs);
            List<String> temp = map.getOrDefault(key, new ArrayList<>());
            temp.add(str);
            map.put(key, temp);
        }
        return new ArrayList<>(map.values());
    }

    public List<List<String>> groupAnagrams1(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            String key = cCnt(str.toCharArray());
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }

    public String cCnt(char[] cs) {
        int[] cnt = new int[26];
        for (char c : cs) {
            cnt[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (cnt[i] != 0) {
                sb.append((char) (i + 'a')).append(cnt[i]);
            }
        }
        return sb.toString();
    }


    /**
     * 矩阵置零
     * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
     * <p>
     * 进阶：
     * 一个直观的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
     * 一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
     * 你能想出一个仅使用常量空间的解决方案吗？
     * <p>
     * 相关标签 数组 哈希表 矩阵
     * <p>
     * eg:
     * [0,1,2,0],
     * [3,4,5,2],
     * [1,3,1,5]]
     * <p>
     * [1,2,3,4],
     * [5,0,7,8],
     * [0,10,11,12],
     * [13,14,15,0]]
     *
     * <p>
     * 思路1: 数组标记 0（m+n）
     * 1、遍历，记录应该置为0的行，和应该置为0的列
     * 2、遍历，将对于行置为0，将对应的列置为0
     * <p>
     * 思路2: 标记 O(1)
     * 1、由思路1 ： 需要m+n个标记来记录行列是否需要置0
     * 2、声明两个变量r、l, 来预先记录第一行或第一列是否需要置零，
     * 3、借助matrix本身的第一行和第一列来标记当前行，当前列是否需要置0
     * 4、将matrix对应的行列置0
     * 5、借助r、l, 判断第一行或第一列是否置0
     */
    // o(m+n)
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[] r = new int[m];
        int[] l = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    r[i] = 1;
                    l[j] = 1;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (r[i] == 1 || l[j] == 1) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // o(1)
    public void setZeroes2(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int r = 1;
        int l = 1;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                l = 0;
                break;
            }
        }
        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                r = 0;
                break;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (r == 0) {
            Arrays.fill(matrix[0], 0);
        }
        if (l == 0) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    public void setZeroes1(int[][] matrix) {

        Set<Integer> r = new HashSet<>();
        Set<Integer> l = new HashSet<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    r.add(i);
                    l.add(j);
                }
            }
        }

        for (Integer i : r) {
            Arrays.fill(matrix[i], 0);
        }

        for (Integer j : l) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][j] = 0;
            }
        }
    }

    /**
     * 三数之和
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
     * 请你找出所有和为 0 且不重复的三元组。
     * <p>
     * 注意：答案中不可以包含重复的三元组。
     * <p>
     * 相关标签 数组 双指针 排序
     * <p>
     * 思路1：排序+双指针 （注意单向） （17ms）
     * 1、排序（便于增大or减小），定义指针 i,j,k  (0<k<i<j<nuns.length)
     * 2、k一直右移，每次在[k+1, nums.length-1]的范围内找到两个数之和 等于 -nums[k], 将nums[i],nums[j],nums[k]放入结果集res
     * 3、返回res
     * ps： 小优化，当nums[i]>0时，则没必要继续调用twoSum，因为两个大于0的数加起来不可能 < 0
     * <p>
     * 思路2: 6ms左右， 未学习
     */
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) {
            return new ArrayList<>();
        }

        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                break;
            }
            int c = nums[i];
            List<List<Integer>> sub = twoSum(nums, i, -c);
            res.addAll(sub);

            while (i + 1 < nums.length && nums[i + 1] == c) {
                i++;
            }
        }
        return res;
    }

    // nums 有序
    public List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int i = start + 1;
        int j = nums.length - 1;
        while (i < j) {
            int a = nums[i];
            int b = nums[j];
            int c = a + b;
            if (c > target) {
                j--;
            } else if (c < target) {
                i++;
            } else {
                List<Integer> sub = Arrays.asList(a, b, -target);
                res.add(sub);

                while (i < j && a == nums[i]) {
                    i++;
                }
                while (i < j && b == nums[j]) {
                    j--;
                }
            }
        }
        return res;
    }

    public List<List<Integer>> threeSum1(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                break;
            }
            List<List<Integer>> list = twoSum1(nums, i + 1, -nums[i]);

            if (!list.isEmpty()) {
                for (List<Integer> integers : list) {
                    integers.add(nums[i]);
                    res.add(integers);
                }
            }

            while (i + 1 < nums.length && nums[i] == nums[i + 1]) {
                i++;
            }
        }

        return res;
    }

    public List<List<Integer>> twoSum1(int[] nums, int start, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int lo = start;
        int hi = nums.length - 1;
        while (lo < hi) {
            int left = nums[lo];
            int right = nums[hi];
            if (left + right < target) {
                lo++;
            } else if (left + right > target) {
                hi--;
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(nums[lo]);
                list.add(nums[hi]);

                res.add(list);
                while (nums[lo] == left && lo < hi) {
                    lo++;
                }
                while (nums[hi] == right && lo < hi) {
                    hi--;
                }
            }
        }
        return res;
    }


    // -------------------------- 数组和字符串 ------------------------------end

}
