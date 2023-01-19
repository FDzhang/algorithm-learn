package com.fd.exercise.runbus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bus {
    private static final int LIMIT = 29;
    private final List<Person> persons = new ArrayList<>(LIMIT);

    // 方向
    private int direction = -1;
    // 下一站
    private int nextSite = -1;
    // 下一站到达时间
    private int nextSiteTime = -1;

    private final List<String> logs = new ArrayList<>();

    public Bus(int direction, int nextSite, int nextSiteTime) {
        this.direction = direction;
        this.nextSite = nextSite;
        this.nextSiteTime = nextSiteTime;
    }

    public List<Person> clear() {
        List<Person> down = new ArrayList<>(persons);
        persons.clear();
        return down;
    }

    public void addLog(String msg) {
        logs.add(msg);
    }

    // 下车，并返回下车的乘客
    public List<Person> remove(int number) {
        List<Person> downs = new ArrayList<>(LIMIT);
        persons.removeIf(person -> {
            if (person.getTargetSite() == number) {
                downs.add(person);
                return true;
            }
            return false;
        });
        return downs;
    }

    public int need() {
        return LIMIT - persons.size();
    }

    public void add(List<Person> sitePersons) {
        persons.addAll(sitePersons);
    }

    // 车辆故障
    public boolean isBad() {
        Random random = new Random(System.nanoTime());
        int bad = random.nextInt(10);
        return bad == 0;
    }

    public int size() {
        return persons.size();
    }

    public int getNextSite() {
        return nextSite;
    }

    public void setNextSite(int nextSite) {
        this.nextSite = nextSite;
    }

    public int getNextSiteTime() {
        return nextSiteTime;
    }

    public void setNextSiteTime(int nextSiteTime) {
        this.nextSiteTime = nextSiteTime;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}
