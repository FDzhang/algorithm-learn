package com.fd.exercise.runbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Site> sites = buildSite();
        List<Bus> buses = buildBuses();
        Manager manager = new Manager(sites, buses);
        // 模拟300分钟
        for (int i = 0; i < 300 * 60; i++) {
            manager.run();
        }

        System.err.println(manager);
        System.err.println("finish");
    }

    private static List<Bus> buildBuses() {
        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus(1, 1, 0));
        buses.add(new Bus(1, 1, 60*15*1));
        buses.add(new Bus(1, 1, 60*15*2));
        buses.add(new Bus(1, 1, 60*15*3));
        buses.add(new Bus(1, 1, 60*15*4));
        buses.add(new Bus(0, 15, 0));
        buses.add(new Bus(0, 15, 60*15*1));
        buses.add(new Bus(0, 15, 60*15*2));
        buses.add(new Bus(0, 15, 60*15*3));
        buses.add(new Bus(0, 15, 60*15*4));
        return buses;
    }

    private static List<Site> buildSite() {
        List<Site> sites = new ArrayList<>();
        sites.add(new Site(1,qM(0,2), qMT(2, 5),true));
        sites.add(new Site(2,qM(0,3,1,1), qMT(3,6,1,4),false));
        sites.add(new Site(3,qM(0,4,1,2), qMT(4,8,2,7),false));
        sites.add(new Site(4,qM(0,5,1,3), qMT(5,8,3,5),false));
        sites.add(new Site(5,qM(0,6,1,4), qMT(6,4,4,6),false));
        sites.add(new Site(6,qM(0,7,1,5), qMT(7,3,5,3),false));
        sites.add(new Site(7,qM(0,8,1,6), qMT(8,6,6,4),false));
        sites.add(new Site(8,qM(0,9,1,7), qMT(9,5,7,5),false));
        sites.add(new Site(9,qM(0,10,1,8), qMT(10,6,8,3),false));
        sites.add(new Site(10,qM(0,11,1,9), qMT(11,7,9,7),false));
        sites.add(new Site(11,qM(0,12,1,10), qMT(12,4,10,4),false));
        sites.add(new Site(12,qM(0,13,1,11), qMT(13,3,11,5),false));
        sites.add(new Site(13,qM(0,14,1,12), qMT(14,6,12,4),false));
        sites.add(new Site(14,qM(0,15,1,13), qMT(15,3,13,5),false));
        sites.add(new Site(15,qM(1,14), qMT(14,4),true));
        return sites;
    }

    private static Map<Integer, Integer> qM(int... data) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(data[0], data[1]);
        if (data.length > 2) {
            map.put(data[2], data[3]);
        }
        return map;
    }
    private static Map<Integer, Integer> qMT(int... data) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(data[0], data[1]*60);
        if (data.length > 2) {
            map.put(data[2], data[3]*60);
        }
        return map;
    }
}