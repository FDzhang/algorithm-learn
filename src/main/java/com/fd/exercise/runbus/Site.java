package com.fd.exercise.runbus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Site {
    // 站点编号
    private final int number;

    private final List<Person> persons = new ArrayList<>();

    // 方向，下一站编号
    private final Map<Integer, Integer> next;
    // 下一站编号，和预计时间
    private final Map<Integer, Integer> nextTime;

    // 是否是终点站
    private final boolean isEnd;

    public Site(int number, Map<Integer, Integer> next, Map<Integer, Integer> nextTime, boolean isEnd) {
        this.number = number;
        this.next = next;
        this.nextTime = nextTime;
        this.isEnd = isEnd;
    }

    public boolean isEnd() {
        return isEnd;
    }

    /**
     * 删除某个方向的指定数量的人，并返回
     * 具体删除的数量 = 指定数量和已有数量中的较小值
     */
    public List<Person> remove(int max, int direction) {
        List<Person> removePersons = new ArrayList<>();

        final int[] cnt = {0};
        persons.removeIf(person -> {
            // 到达上限不再删除
            if (cnt[0] >= max) {
                return false;
            }
            // 方向一致，则删除
            if (person.getDirection() == direction) {
                removePersons.add(person);
                cnt[0] = cnt[0] + 1;
                return true;
            }
            return false;
        });

        return removePersons;
    }

    // 插队
    public void insert(List<Person> busPersons) {
        persons.addAll(0, busPersons);
    }

    // 返回下一站编号
    public int getNext(int direction) {
        return next.get(direction);
    }

    // 返回抵达下一站耗时
    public int getNextSpend(int direction) {
        return nextTime.get(getNext(direction));
    }

    public int getNumber() {
        return number;
    }

    public void add(Person person) {
        persons.add(person);
    }
}
