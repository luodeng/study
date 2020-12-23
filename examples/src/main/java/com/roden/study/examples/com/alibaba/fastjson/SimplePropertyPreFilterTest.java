package com.roden.study.examples.com.alibaba.fastjson;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;


public class SimplePropertyPreFilterTest {

	public static void main(String[] args) {
		List<User> list=new ArrayList<>();
		User user1=new User();
		user1.setName("ann");
		user1.setAge(11);
		User user2=new User();
		user2.setName("annie");
		user2.setAge(22);
		
		list.add(user1);
		list.add(user2);
	    SimplePropertyPreFilter filter = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
		filter.getIncludes().add("id");
		filter.getIncludes().add("name");		
		//filter.getExcludes().add("provinceID");		
	   // SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Province.class, "provinceState","provinceName");
	   
	    System.out.println(JSON.toJSONString(list, filter));

	}

}
