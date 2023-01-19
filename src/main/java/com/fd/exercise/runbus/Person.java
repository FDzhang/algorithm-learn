package com.fd.exercise.runbus;

public class Person {
    private int direction;
    // 目标站点编号
    private int targetSite;

    public Person(int direction, int targetSite) {
        this.direction = direction;
        this.targetSite = targetSite;
    }

    public int getDirection() {
        return direction;
    }

    public int getTargetSite() {
        return targetSite;
    }

}
