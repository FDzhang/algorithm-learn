package com.fd.algorithmlearn.haweiodexem2022;

import java.util.*;

/**
 * 最小传输时延  dfs
 * Created by fd 2022-3-21
 */

public class Main3 {

    private static int res = Integer.MAX_VALUE;
    private static int source;
    private static int target;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] nm = sc.nextLine().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        // 节点，链接节点
        Map<Integer, List<Integer>> nMap = new HashMap<>(n);
        // 节点间的距离  u*100+v, w
        Map<Integer, Integer> wMap = new HashMap<>(m);

        for (int i = 0; i < m; i++) {
            String[] uvw = sc.nextLine().split(" ");
            int u = Integer.parseInt(uvw[0]);
            int v = Integer.parseInt(uvw[1]);
            int w = Integer.parseInt(uvw[2]);

            if (!nMap.containsKey(u)) {
                nMap.put(u, new ArrayList<>());
            }
            nMap.get(u).add(v);
            wMap.put(u * 100 + v, w);
        }
        String[] st = sc.nextLine().split(" ");
        int source = Integer.parseInt(st[0]);
        target = Integer.parseInt(st[1]);

        findShort(source, 0, nMap, wMap);

        System.out.println(res == Integer.MAX_VALUE ? -1 : res);
    }

    private static void findShort(int source, int curLen, Map<Integer, List<Integer>> nMap, Map<Integer, Integer> wMap) {
        if (source == target) {
            if (curLen < res) {
                res = curLen;
            }
        }

        // 剪枝
        if (curLen > res) {
            return;
        }

        List<Integer> nexts = nMap.get(source);

        if (nexts == null) {
            return;
        }

        for (Integer next : nexts) {
            Integer w = wMap.get(source * 100 + next);
            if (w == null) {
                return;
            }
            curLen += w;
            findShort(next, curLen, nMap, wMap);
            curLen -= w;
        }
    }
}
