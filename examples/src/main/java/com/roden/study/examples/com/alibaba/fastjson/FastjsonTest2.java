package com.roden.study.examples.com.alibaba.fastjson;


import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastjsonTest2 {
    @Test
    public void test(){
        // 创建product对象
        Product product1 = new Product("火龙果", 20d, "水果", 2d);
        Product product2 = new Product("东北散装珍珠米", 10d, "粮食", 3d);
        Product product3 = new Product("排骨", 24d, "肉类", 0.5d);
        // 创建产品list集合
        List<Product> list = new ArrayList<Product>();
        list.add(product1);
        list.add(product2);
        list.add(product3);
        // 创建产品map集合
        Map<String, Product> map = new HashMap<String, Product>();
        map.put(product1.getName(), product1);
        map.put(product2.getName(), product2);
        map.put(product3.getName(), product3);

        String jsonProduct = JSON.toJSONString(product1);
        System.out.println(jsonProduct);

        String jsonArray = JSON.toJSONString(list);
        System.out.println(jsonArray);

        String jsonMap = JSON.toJSONString(map);
        System.out.println(jsonMap);

        String str = "{'name':'鸡蛋','price':8,'weight':1.3,'category':'禽蛋'}";
        Product product4 = JSON.parseObject(str, Product.class);
        System.out.println(product4);

    }
}
