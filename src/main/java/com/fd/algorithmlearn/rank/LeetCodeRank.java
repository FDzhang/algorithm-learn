package com.fd.algorithmlearn.rank;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 力扣挑战赛
 *
 * @author zhangxinqiang
 * @create 2021/9/11 15:02
 */
public class LeetCodeRank {
    /**
     * 在 「力扣挑战赛」 开幕式的压轴节目 「无人机方阵」中，每一架无人机展示一种灯光颜色。 无人机方阵通过两种操作进行颜色图案变换：
     * 调整无人机的位置布局
     * 切换无人机展示的灯光颜色
     * 给定两个大小均为 N*M 的二维数组 source 和 target 表示无人机方阵表演的两种颜色图案，由于无人机切换灯光颜色的耗能很大，请返回从 source 到 target 最少需要多少架无人机切换灯光颜色。
     * <p>
     * 注意： 调整无人机的位置布局时无人机的位置可以随意变动。
     * <p>
     * 123
     * 345
     * <p>
     * 135
     * 234
     */
    public int minimumSwitchingTimes(int[][] source, int[][] target) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        int res = 0;
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                cnt.merge(source[i][j], 1, Integer::sum);
            }
        }

        for (int i = 0; i < target.length; i++) {
            for (int j = 0; j < target[0].length; j++) {
                if (cnt.containsKey(target[i][j]) && cnt.get(target[i][j]) > 0) {
                    cnt.merge(target[i][j], -1, Integer::sum);
                } else {
                    res++;
                }
            }
        }
        return res;
    }

    /**
     * 2. 心算挑战
     * 「力扣挑战赛」心算项目的挑战比赛中，要求选手从 N 张卡牌中选出 cnt 张卡牌，若这 cnt 张卡牌数字总和为偶数，则选手成绩「有效」且得分为 cnt 张卡牌数字总和。
     * 给定数组 cards 和 cnt，其中 cards[i] 表示第 i 张卡牌上的数字。 请帮参赛选手计算最大的有效得分。若不存在获取有效得分的卡牌方案，则返回 0。
     * <p>
     * 示例 1：
     * 输入：cards = [1,2, 6, 8,9], cnt = 3
     * 输出：18
     * 解释：选择数字为 1、8、9 的这三张卡牌，此时可获得最大的有效得分 1+8+9=18。
     * <p>
     * 1 4 11 11 12 12   cnt 3
     * 33
     * 28
     * <p>
     * 34
     * dp[n][2]
     * <p>
     * dp[n][0] 最大的偶数
     * dp[n][1] 最大的基数
     * <p>
     * dp[0]
     *
     *
     *
     * <p>
     * 9 8 2 19
     * 9 8 1 18
     * <p>
     * 9 8 5
     *
     * <p>
     * 思路： 回溯
     * 1、路径：已经选择过的数字
     * 2、选择列表：可以选择的数字
     * 3、结束条件：（cnt==0 && num为偶数）
     * 4、所有偶数中的最大值：max ( list )
     */
    private int res = Integer.MIN_VALUE;

    public int maxmiumScore(int[] cards, int cnt) {
        int sum = 0;
        boolean[] valid = new boolean[cards.length];

        maxmiumScoreBackTrack(sum, cards, cnt, valid);

        return res == Integer.MIN_VALUE ? 0 : res;
    }

    private void maxmiumScoreBackTrack(int sum, int[] cards, int cnt, boolean[] valid) {
        if (cnt == 0) {
            if (sum % 2 == 0 && sum > res) {
                res = sum;
            }
            return;
        }

        for (int i = 0; i < cards.length; i++) {
            if (valid[i]) {
                continue;
            }

            sum += cards[i];
            valid[i] = true;
            maxmiumScoreBackTrack(sum, cards, cnt - 1, valid);
            valid[i] = false;
            sum -= cards[i];
        }
    }


    public int maxmiumScore1(int[] cards, int cnt) {
        int n = cards.length, odd = 0, even = 0, i1 = 0, i2 = 0, res = 0;
        for (int i : cards) if (i % 2 == 1) odd++;
        even = n - odd;
        int[] odds = new int[odd + 1], evens = new int[even + 1];
        for (int i : cards) {
            if (i % 2 == 1) odds[i1++] = i;
            else evens[i2++] = i;
        }
        Arrays.sort(odds);
        Arrays.sort(evens);
        for (int i = 1; i <= odd; i++)
            odds[i] += odds[i - 1];
        for (int i = 1; i <= even; i++)
            evens[i] += evens[i - 1];
        for (int i = 0; i <= Math.min(Math.min(n, odd), cnt); i += 2) {
            if (even >= cnt - i)
                res = Math.max(res, odds[odd] - odds[odd - i] + evens[even] - evens[even - (cnt - i)]);
        }
        return res;
    }

    /**
     * 3. 黑白翻转棋
     * 在 n*m 大小的棋盘中，有黑白两种棋子，黑棋记作字母 "X", 白棋记作字母 "O"，空余位置记作 "."。当落下的棋子与其他相同颜色的棋子在行、列或对角线完全包围（中间不存在空白位置）另一种颜色的棋子，则可以翻转这些棋子的颜色。
     * 「力扣挑战赛」黑白翻转棋项目中，将提供给选手一个未形成可翻转棋子的棋盘残局，其状态记作 chessboard。若下一步可放置一枚黑棋，请问选手最多能翻转多少枚白棋。
     * 注意：
     * <p>
     * 若翻转白棋成黑棋后，棋盘上仍存在可以翻转的白棋，将可以 继续 翻转白棋
     * 输入数据保证初始棋盘状态无可以翻转的棋子且存在空余位置
     * 示例 1：
     * <p>
     * 输入：chessboard = ["....X.","....X.","XOOO..","......","......"]
     * <p>
     * 输出：3
     * <p>
     * 解释：
     * 可以选择下在 [2,4] 处，能够翻转白方三枚棋子。
     */
    public int flipChess(String[] chessboard) {


        return 0;
    }

    /**
     * 4. 玩具套圈
     * 「力扣挑战赛」场地外，小力组织了一个套玩具的游戏。所有的玩具摆在平地上，toys[i] 以 [xi,yi,ri] 的形式记录了第 i 个玩具的坐标 (xi,yi) 和半径 ri。小扣试玩了一下，他扔了若干个半径均为 r 的圈，circles[j] 记录了第 j 个圈的坐标 (xj,yj)。套圈的规则如下：
     * <p>
     * 若一个玩具被某个圈完整覆盖了（即玩具的任意部分均在圈内或者圈上），则该玩具被套中。
     * 若一个玩具被多个圈同时套中，最终仅计算为套中一个玩具
     * 请帮助小扣计算，他成功套中了多少玩具。
     * <p>
     * 注意：
     * <p>
     * 输入数据保证任意两个玩具的圆心不会重合，但玩具之间可能存在重叠。
     * 示例 1：
     * <p>
     * 输入：toys = [[3,3,1],[3,2,1]], circles = [[4,3]], r = 2
     * <p>
     * 输出：1
     * <p>
     * 思路：
     * 1、计算玩具圆心 和 圆圈圆心的距离 Rr + toyR <= r , 则为套中, 计数且标记
     * 2、第二个圆环同样计数，重复套中不计数
     */
    public int circleGame(int[][] toys, int[][] circles, int r) {
        int cnt = 0;
        boolean[] ts = new boolean[toys.length];
        for (int i = 0; i < circles.length; i++) {
            for (int j = 0; j < toys.length; j++) {
                if (ts[j]){
                    continue;
                }
                double len = len(circles[i][0], circles[i][1], toys[j][0], toys[j][1]);
                if (len + toys[j][2] <= r) {
                    ts[j] = true;
                    cnt ++;
                }
            }
        }
        return cnt;
    }

    public double len(int x1, int y1, int x2, int y2) {
        double a2 = Math.pow(Math.abs(x2 - x1), 2);
        double b2 = Math.pow(Math.abs(y2 - y1), 2);
        return Math.sqrt(a2 + b2);
    }

    /**
     * 5. 十字路口的交通
     * 前往「力扣挑战赛」场馆的道路上，有一个拥堵的十字路口，该十字路口由两条双向两车道的路交叉构成。由于信号灯故障，交警需要手动指挥拥堵车辆。假定路口没有新的来车且一辆车从一个车道驶入另一个车道所需的时间恰好为一秒钟，长度为 4 的一维字符串数组 directions 中按照 东、南、西、北 顺序记录了四个方向从最靠近路口到最远离路口的车辆计划开往的方向。其中：
     * <p>
     * "E" 表示向东行驶；
     * "S" 表示向南行驶；
     * "W" 表示向西行驶；
     * "N" 表示向北行驶。
     * 交警每秒钟只能指挥各个车道距离路口最近的一辆车，且每次指挥需要满足如下规则：
     * <p>
     * 同一秒钟内，一个方向的车道只允许驶出一辆车；
     * 同一秒钟内，一个方向的车道只允许驶入一辆车；
     * 同一秒钟内，车辆的行驶路线不可相交。
     * 请返回最少需要几秒钟，该十字路口等候的车辆才能全部走完。
     * <p>
     * 各个车道驶出的车辆可能的行驶路线如图所示：
     * <p>
     * 注意：
     * <p>
     * 测试数据保证不会出现掉头行驶指令，即某一方向的行驶车辆计划开往的方向不会是当前车辆所在的车道的方向;
     * 表示堵塞车辆行驶方向的字符串仅用大写字母 "E"，"N"，"W"，"S" 表示。
     * 示例 1：
     * <p>
     * 输入：directions = ["W","N","ES","W"]
     * <p>
     * 输出：2
     * <p>
     * 解释：
     * 第 1 秒：东西方向排在最前的车先行，剩余车辆状态 ["","N","S","W"]；
     * 第 2 秒：南、西、北方向的车行驶，路口无等待车辆；
     * 因此最少需要 2 秒，返回 2。
     */
    public int trafficCommand(String[] directions) {
        return 0;
    }


}
