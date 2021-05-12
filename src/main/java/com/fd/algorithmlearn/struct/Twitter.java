package com.fd.algorithmlearn.struct;

import java.util.*;

/**
 * 355. 设计推特
 * https://leetcode-cn.com/problems/design-twitter/
 *
 * @author zhangxinqiang
 * @create 2021/5/12 16:37
 */
public class Twitter {
    private static int timestamp = 0;

    private static class Tweet {
        private int id;
        private int time;
        private Tweet next;

        public Tweet(int id, int time) {
            this.id = id;
            this.time = time;
            this.next = null;
        }
    }

    private static class User {
        private int id;
        public Set<Integer> followed;
        // 用户发表的推文链表头结点
        public Tweet head;

        public User(int userId) {
            followed = new HashSet<>();
            this.id = userId;
            this.head = null;
            // 关注一下自己
            follow(id);
        }

        public void follow(int userId) {
            followed.add(userId);
        }

        public void unfollow(int userId) {
            // 不可以取关自己
            if (userId != this.id) {
                followed.remove(userId);
            }
        }

        public void post(int tweetId) {
            Tweet twt = new Tweet(tweetId, timestamp);
            timestamp++;
            // 将新建的推文插入链表头
            // 越靠前的推文 time 值越大
            twt.next = head;
            head = twt;
        }
    }

    // 我们需要一个映射将 userId 和 User 对象对应起来
    private HashMap<Integer, User> userMap = new HashMap<>();


    /**
     * Initialize your data structure here.
     */
    public Twitter() {

    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        if (!userMap.containsKey(userId)) {
            userMap.put(userId, new User(userId));
        }
        User user = userMap.get(userId);
        user.post(tweetId);
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed.
     * Each item in the news feed must be posted by users who the user followed or by the user herself.
     * Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        if (!userMap.containsKey(userId)) {
            return res;
        }
        // 关注列表的用户 Id
        Set<Integer> users = userMap.get(userId).followed;
        // 自动通过 time 属性从大到小排序，容量为 users 的大小
        PriorityQueue<Tweet> pq = new PriorityQueue<>(users.size(), (a, b) -> (b.time - a.time));
        // 先将所有链表头节点插入优先级队列
        for (int id : users) {
            Tweet tweet = userMap.get(id).head;
            if (tweet != null) {
                pq.add(tweet);
            }
        }
        while (!pq.isEmpty()) {
            // 最多返回 10 条就够了
            if (res.size() == 10) {
                break;
            }
            // 弹出 time 值最大的（最近发表的）
            Tweet tweet = pq.poll();
            res.add(tweet.id);
            // 将下一篇 Tweet 插入进行排序
            if (tweet.next != null) {
                pq.add(tweet.next);
            }
        }
        return res;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followeeId)) {
            userMap.put(followeeId, new User(followeeId));
        }
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new User(followerId));
        }
        userMap.get(followerId).follow(followeeId);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        if (userMap.containsKey(followeeId)) {
            userMap.get(followerId).unfollow(followeeId);
        }
    }
}
