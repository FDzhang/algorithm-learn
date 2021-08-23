package com.fd.algorithmlearn.think;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author : zxq
 * @create : 2021/8/19 23:31
 */
public class Compare {

    public static void main(String[] args) {
        List<Person> ps = new ArrayList<>();

        ps.add(new Person(1, "a"));
        ps.add(new Person(7, "b"));
        ps.add(new Person(3, "c"));
        ps.add(new Person(5, "d"));
        ps.add(new Person(9, "e"));

//        ps.sort(Comparator.comparingInt(Person::getId));
        ps.sort((a, b) -> b.getId() - a.getId());

        ps.forEach(System.err::println);

        System.err.println("-----------");

        ps.sort((a, b) -> b.getName().compareTo(a.getName()));

        ps.forEach(System.err::println);
    }

}

@Data
class Person {
    private Integer id;
    private String name;

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
