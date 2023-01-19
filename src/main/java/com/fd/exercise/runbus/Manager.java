package com.fd.exercise.runbus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Manager {

    // 时间，单位秒
    private int time = 0;
    private final List<Site> sites;
    private final List<Bus> buses;

    private final List<Bus> badBuses = new ArrayList<>();

    // 每五分钟 10个人
    private static final int P_NUM = 10;

    public Manager(List<Site> sites, List<Bus> buses) {
        this.sites = sites;
        this.buses = buses;
    }

    public void run() {
        // 每5分钟
        if (isPerFive()) {
            int cnt = P_NUM;
            // 随机投放到某一个站点
            while (cnt > 0) {
                Person p = randomPerson();
                Site s = randomSite();
                if (p.getTargetSite() != s.getNumber()) {
                    cnt--;
                    s.add(p);
                }
            }
        }

        // 处理每一辆车
        for (Bus bus : buses) {
            if (time == bus.getNextSiteTime()) {
                Site cur = getSiteByNumber(bus.getNextSite());
                int spend = dealBus(bus, cur);
            }
        }
        // 移除故障车辆，下次不再处理
        buses.removeAll(badBuses);

        // 时间前进
        time++;
    }

    private Site randomSite() {
        return sites.get(random(15) - 1);
    }

    private Person randomPerson() {
        return new Person(random(), random(15));
    }


    private boolean isPerFive() {
        if (time < 60) {
            return false;
        }
        return (time / 5) % 60 == 0;
    }

    private static int random() {
        Random random = new Random(System.nanoTime());
        return random.nextInt(2);
    }

    private int random(int bound) {
        Random random = new Random(System.nanoTime());
        return random.nextInt(bound) + 1;
    }

    private String buildLog(int time, int site, int up, int down, boolean isEnd) {
        if (isEnd) {
            return String.format("%d: 下客 %d 人, 从 %s 站发车，乘客 %d 人", time, down, site, up);
        }
        return String.format("%d: 下客 %d 人，上客 %d 人，继续出发", time, down, up);
    }

    private int dealBus(Bus bus, Site site) {
        // 耗时
        int spend = 0;

        List<Person> downs = new ArrayList<>();
        // 下车
        if (site.isEnd()) { // 处理终点
            bus.addLog(time + ": 抵达终点站");
            // 清空乘客
            downs = bus.clear();
            spend += downs.size() * 10;
            // 终点站，转向
            bus.setDirection((bus.getDirection() + 1) % 2);
        } else {
            bus.addLog(time + ": 到达 " + site.getNumber() + " 站");
            downs = bus.remove(site.getNumber());
            spend += downs.size() * 10;
        }

        // 故障
        if (bus.isBad()) {
            List<Person> needUpBus = bus.clear();
            spend += needUpBus.size() * 10;
            site.insert(needUpBus);
            // 记录故障车辆
            badBuses.add(bus);
            bus.addLog((time + spend) + ": 车故障，于站点: " + site.getNumber());
            return spend;
        }

        // 上车
        int need = bus.need();
        List<Person> ups = site.remove(need, bus.getDirection());
        bus.add(ups);
        spend += ups.size() * 10;
        bus.addLog(buildLog(time + spend, site.getNumber(), ups.size(), downs.size(), site.isEnd()));

        // 更新车的信息，下一站，以及抵达时间
        bus.setNextSite(site.getNext(bus.getDirection()));
        bus.setNextSiteTime(time + site.getNextSpend(bus.getDirection()) + spend);
        return spend;
    }

    // 通过编号获取站点
    private Site getSiteByNumber(int nextSite) {
        for (Site site : sites) {
            if (site.getNumber() == nextSite) {
                return site;
            }
        }
        return null;
    }
}
