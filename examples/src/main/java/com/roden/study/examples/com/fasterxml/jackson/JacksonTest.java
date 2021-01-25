package com.roden.study.examples.com.fasterxml.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JacksonTest {
    @Test
    public void test() throws JsonProcessingException {
        //创建product对象
        Product product1 = new Product("火龙果", 20d, "水果", 2d);
        Product product2 = new Product("东北散装珍珠米", 10d, "粮食", 3d);
        Product product3 = new Product("排骨", 24d, "肉类", 0.5d);
        //创建产品list集合
        List<Product> list = new ArrayList<Product>();
        list.add(product1);
        list.add(product2);
        list.add(product3);
        //创建产品map集合
        Map<String, Product> map = new HashMap<String, Product>();
        map.put(product1.getName(), product1);
        map.put(product2.getName(), product2);
        map.put(product3.getName(), product3);

        //ObjectMapper为Jackson的核心，所有json操作都是用ObjectMapper实现
        ObjectMapper mapper = new ObjectMapper();

        //Product转化为json
        String productJson = mapper.writeValueAsString(product1);
        System.out.println("Product转化为json");
        System.out.println(productJson);
        //list转化为json
        String listJson = mapper.writeValueAsString(list);
        System.out.println("list转化为json");
        System.out.println(listJson);
        //map转化为json
        String mapJson = mapper.writeValueAsString(map);
        System.out.println("map转化为json");
        System.out.println(mapJson);


        String str = "{\"name\":\"鸡蛋\",\"price\":8,\"weight\":1.3,\"category\":\"禽蛋\"}";
        Product product4 = mapper.readValue(str, Product.class);
        System.out.println(product4);

    }

}
