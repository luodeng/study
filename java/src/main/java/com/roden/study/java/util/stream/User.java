package com.roden.study.java.util.stream;

public class User implements Comparable
    {  
        public int age;//年龄  
        public String name;//姓名  
        private String password;//密码  
        public short gendar;//性别，0未知，1男，2女  
        public boolean hasMarried;//是否已婚  
          
          
        public String getPassword() {  
            return password;  
        }  
          
        public User(int age, String name, String password, short gendar,
                    boolean hasMarried) {
            super();  
            this.age = age;  
            this.name = name;  
            this.password = password;  
            this.gendar = gendar;  
            this.hasMarried = hasMarried;  
        }  
        @Override  
        public String toString() {  
            return "{\"age\":\"" + age + "\", \"name\":\"" + name  
                    + "\", \"password\":\"" + password + "\", \"gendar\":\""  
                    + gendar + "\", \"hasMarried\":\"" + hasMarried + "\"} \n";  
        }

        @Override
        public boolean equals(Object obj) {
            // TODO Auto-generated method stub
            if(!(obj instanceof User))return false;
            User u = (User)obj;
            if(age != u.age
                    || gendar!=u.gendar
                    || hasMarried!=u.hasMarried
                    || !name.equals(u.name)
                    || !password.equals(u.getPassword())
                    )return false;
            return true;
        }


        @Override
        public int compareTo(Object obj) {
            // TODO Auto-generated method stub
            //首先比较年龄大小，因为年龄的区分度比较高
            User  o2=(User)obj;
            User o1 = this;
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
    }