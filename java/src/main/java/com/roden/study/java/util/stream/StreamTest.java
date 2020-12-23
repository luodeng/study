package com.roden.study.java.util.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTest {
    @Test
    public void test1(){
        ArrayList<User> users = new ArrayList<User>();
        users.add(new User(22, "王旭", "123456", (short)1, true));
        users.add(new User(22, "王旭", "123456", (short)1, true));
        users.add(new User(22, "王旭", "123456", (short)1, true));
        users.add(new User(21, "孙萍", "a123456", (short)2, false));
        users.add(new User(23, "步传宇", "b123456", (short)1, false));
        users.add(new User(18, "蔡明浩", "c123456", (short)1, true));
        users.add(new User(17, "郭林杰", "d123456", (short)1, false));
        users.add(new User(5, "韩凯", "e123456", (short)1, true));
        users.add(new User(22, "韩天琪", "f123456", (short)2, false));
        users.add(new User(21, "郝玮", "g123456", (short)2, false));
        users.add(new User(19, "胡亚强", "h123456", (short)1, false));
        users.add(new User(14, "季恺", "i123456", (short)1, false));
        users.add(new User(17, "荆帅", "j123456", (short)1, true));
        users.add(new User(16, "姜有琪", "k123456", (short)1, false));


        //场景一、对用户进行排序
        Comparator<User> ageComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                // TODO Auto-generated method stub
                if(o1.age>o2.age)return 1;
                if(o1.age<o2.age)return -1;
                return 0;
            }
        };

        long time = System.currentTimeMillis();
        Collections.sort(users, ageComparator);
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(users);

        time = System.currentTimeMillis();
        List<User> sortedUsers = users.stream().sorted(ageComparator).collect(Collectors.toList());
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(sortedUsers);

        //场景一（2）选出年龄最小的三个人
        Collections.sort(users, ageComparator);
        users.subList(0, 2);

        time = System.currentTimeMillis();
        List<User> resultArr = users.stream().sorted(ageComparator).limit(3).collect(Collectors.toList());
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(resultArr);

        //场景二：去除重复数据
        Comparator<User> equalComparator = new Comparator<User>() {

            @Override
            public int compare(User o1, User o2) {
                // TODO Auto-generated method stub
                //首先比较年龄大小，因为年龄的区分度比较高
                if(o1.age>o2.age)return 1;
                if(o1.age<o2.age)return -1;
                //如果年龄相同就比较性别，女的排在前面
                if(o1.gendar>o2.gendar)return 1;
                if(o1.gendar<o2.gendar)return -1;
                //如果性别也一样就比较是否已婚
                if(o1.hasMarried == true && o2.hasMarried==false)return 1;//结婚的排在前面
                if(o1.hasMarried == false && o2.hasMarried==true)return 1;//结婚的排在前面
                //最后比较姓名，因为字符串比较耗时较长
                if(o1.name.hashCode()>o2.name.hashCode())return 1;
                if(o1.name.hashCode()<o2.name.hashCode())return -1;
                return 0;
            }
        };
        Collections.sort(users, ageComparator);
        time = System.currentTimeMillis();
        int length = users.size();
        for(int i=1;i<length;i++){
            if(users.get(i).equals(users.get(i-1))){
                users.remove(i);
                i--;
                length--;
            }
        }
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(users);

        time = System.currentTimeMillis();
        resultArr = users.stream().sorted().distinct().collect(Collectors.toList());
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(resultArr);

        //场景三：按条件筛选
        time = System.currentTimeMillis();
        resultArr = new ArrayList<User>();//用于存放结果
        for(User u:users){
            if(u.name.startsWith("韩"))resultArr.add(u);
        }
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(resultArr);

        time = System.currentTimeMillis();
        resultArr = users.stream().filter(t->t.name.startsWith("韩")).collect(Collectors.toList());
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(resultArr);

        resultArr = users.stream().filter(t->t.name.startsWith("韩") && t.gendar==2).collect(Collectors.toList());
        resultArr = users.stream().filter(t->t.name.startsWith("韩")).filter(t->t.gendar==2).collect(Collectors.toList());

       resultArr = users.stream().filter(t->t.gendar==2).filter(t->t.name.startsWith("韩")).collect(Collectors.toList());

       //场景四：只列出所有人的名字和婚姻状况
        time = System.currentTimeMillis();
        ArrayList<String> marryStatus = new ArrayList<String>();
        for(User u:users){
            marryStatus.add(u.name+":".concat(u.hasMarried?"已婚":"未婚")+"\n");
        }
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(marryStatus);

        time = System.currentTimeMillis();
        List<String> marryStatus2 = users.stream().map(t->t.name+":".concat(t.hasMarried?"已婚":"未婚")+"\n").collect(Collectors.toList());
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(marryStatus2);

        //场景五：判断当前数组是否包含某些特定元素
        time = System.currentTimeMillis();
        boolean isChild = false;
        for(User u:users){
            if(u.age<18){
                isChild = true;
                break;
            }
        }
        System.out.println("耗时"+(System.currentTimeMillis()-time)+isChild);

        time = System.currentTimeMillis();
        isChild = users.stream().anyMatch(t->t.age<18);
        System.out.println("耗时"+(System.currentTimeMillis()-time)+isChild);

        //场景六：确认所有元素均满足某一条件
        time = System.currentTimeMillis();
        boolean allMarried = true;
        for(User u:users){
            if(!u.hasMarried){
                allMarried = false;
                break;
            }
        }
        System.out.println("耗时"+(System.currentTimeMillis()-time)+allMarried);

        time = System.currentTimeMillis();
        allMarried = users.stream().allMatch(t->t.hasMarried);
        System.out.println("耗时"+(System.currentTimeMillis()-time)+allMarried);

        //场景七：求和求平均值
        time = System.currentTimeMillis();
        int sum = 0;
        for(User u:users){
            sum+=u.age;
        }
        System.out.println("耗时"+(System.currentTimeMillis()-time)+sum/users.size());

        time = System.currentTimeMillis();
        OptionalInt sumo = users.stream().mapToInt(t->t.age).reduce(Integer::sum);
        System.out.println("耗时"+(System.currentTimeMillis()-time)+sumo.getAsInt()/users.size());

        //场景八：分组
        time = System.currentTimeMillis();
        Map<Integer,List<User>> group = new HashMap<Integer,List<User>>();
        for(User u:users){
            List<User> list = group.get(u.age);
            if(list==null){
                list = new ArrayList<User>();
                group.put(u.age,list);
            }
            list.add(u);
        }
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(group);

        time = System.currentTimeMillis();
        group = users.stream().collect(Collectors.groupingBy(t->t.age));
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.out.println(group);

        Map<Boolean,List<User>> group2 = users.stream().collect(Collectors.partitioningBy(t->t.hasMarried));

        //场景九：链式操作
        time = System.currentTimeMillis();
        users.stream().filter(t->t.gendar==2).map(t->t.name).forEach(System.out::println);
        System.out.println("耗时"+(System.currentTimeMillis()-time));
    }
}
