///*
//package com.rainbowsea.rainbowsealiving;
//
//import java.lang.reflect.Array;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class TestStream {
//    public static void main(String[] args) {
//        // 创建 5 个 Person 对象
//        Person person1 = new Person(1, "a", 22);
//        Person person2 = new Person(2, "b", 34);
//        Person person3 = new Person(3, "c", 10);
//        Person person4 = new Person(4, "d", 110);
//        Person person5 = new Person(5, "e", 9);
//
//
//        // 放入到 List 集合
//        List<Person> list = Arrays.asList(person1, person2, person3, person4, person5);
//        System.out.println("list=>" + list);
//
//        //
//        List<Person> list2 = list.stream().filter(
//                person -> {
//                    return person.getId() % 2 != 0;
//                }
//        ).map(person -> {
//            Cat cat = new Cat(person.getId() + 100, "小花猫", "花色");
//            person.setCat(cat);
//            return person;
//        }).sorted((p1,p2)->{
//            return p1.getId() - p2.getId();
//        }).collect(Collectors.toList());
//
//        System.out.println("list2=>" + list2);
//
//        System.out.println("list=>" + list);
//    }
//}
//*/
