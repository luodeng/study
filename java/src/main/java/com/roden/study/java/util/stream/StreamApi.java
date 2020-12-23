package com.roden.study.java.util.stream;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamApi {
    private static Logger logger = LoggerFactory.getLogger(StreamApi.class);

    @Test
    //分割List
    public void splitList(){
        List<Person> personList=new ArrayList<>();
        for(int i=0;i<95;i++){
            Person p=new Person();
            p.setAge(i);
            p.setName("name"+i);
            personList.add(p);
        }
        int limit=personList.size()/10+1;

        Stream.iterate(0, n->n+1).limit(limit).parallel().forEach(a->{
            List<Person> tempList=personList.stream().skip(a*10).limit(10).parallel().collect(Collectors.toList());
            System.out.println(JSON.toJSONString(tempList));
        });


        List<List<Person>> splitList = Stream.iterate(0,n->n+1).limit(limit).parallel().map(a->{
            List<Person> sendList = personList.stream().skip(a*10).limit(10).parallel().collect(Collectors.toList());
            return sendList;
        }).collect(Collectors.toList());
       for(List<Person> temp:splitList){
            for (Person p : temp) {
                logger.info(JSON.toJSONString(p));
            }
           logger.info("........................................................................");
        }
    }

    @Test
    //分组
    public void groupList() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Person p = new Person();
            p.setAge(i);
            p.setName("name" + i);
            p.setGender(i%2==0?"男":"女");
            personList.add(p);
        }

        Map<String, List<Person>> result = new HashMap<>();
        for (Person p : personList) {
            String gender = p.getGender();
            List<Person> persons = result.get(gender);
            if (persons == null) {
                persons = new ArrayList<>();
                result.put(gender, persons);
            }
            persons.add(p);
        }
        System.out.println(JSON.toJSONString(result));
        //分组
        Map<String, List<Person>> listMap = personList.stream().collect(Collectors.groupingBy(Person::getGender));
        System.out.println(JSON.toJSONString(listMap));
        //分组统计数量
        Map<String, Long> listMap2 = personList.stream().collect(Collectors.groupingBy(Person::getGender,Collectors.counting()));
        System.out.println(JSON.toJSONString(listMap2));
        //分组平均
        Map<String, Double> listMap3 = personList.stream().collect(Collectors.groupingBy(Person::getGender,Collectors.averagingInt(Person::getAge)));
        System.out.println(JSON.toJSONString(listMap3));
        //分组分区
        Map<Boolean, List<Person>> listMap4 =personList.stream().collect(Collectors.partitioningBy(e -> e.getAge() > 1));
        System.out.println(JSON.toJSONString(listMap4));

        //分组分区统计
        Map<Boolean, Map<String, Long>> listMap5 =  personList.stream().collect(Collectors.partitioningBy(e -> e.getAge() > 1,
                Collectors.groupingBy(Person::getGender, Collectors.counting())));
        System.out.println(JSON.toJSONString(listMap5));
    }
}
