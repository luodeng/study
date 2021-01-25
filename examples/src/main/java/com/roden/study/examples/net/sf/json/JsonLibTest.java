package com.roden.study.examples.net.sf.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonLibTest {
    @Test
    public void test(){
        //创建product对象
        Product product1 = new Product("火龙果",20d,"水果",2d);
        Product product2 = new Product("东北散装珍珠米",10d,"粮食",3d);
        Product product3 = new Product("排骨",24d,"肉类",0.5d);
        //创建产品list集合
        List<Product> list =new ArrayList<Product>();
        list.add(product1);
        list.add(product2);
        list.add(product3);
        //创建产品map集合
        Map<String, Product> map = new HashMap<String, Product>();
        map.put(product1.getName(), product1);
        map.put(product2.getName(), product2);
        map.put(product3.getName(), product3);

        //将product转换为json
        JSONArray jsonBean = JSONArray.fromObject(product1);
        System.out.println("输出product的json数据");
        System.out.println(jsonBean);
        //将list集合转换为json
        JSONArray jsonArray = JSONArray.fromObject(list);
        System.out.println("输出list的json数据");
        System.out.println(jsonArray);
        //将map集合转为json
        JSONArray jsonMap = JSONArray.fromObject(map);
        System.out.println("输出map的json数据");
        System.out.println(jsonMap);

        /*
         * 设置筛选条件
         */
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"weight", "category"});

        //单个bean不显示重量，分类
        System.out.println("输出product筛选的json数据");
        JSONArray jsonBean1 = JSONArray.fromObject(product1, jsonConfig);
        System.out.println(jsonBean1);

        //list不显示重量分类
        System.out.println("输出list筛选的json数据");
        JSONArray jsonArray1 = JSONArray.fromObject(list, jsonConfig);
        System.out.println(jsonArray1);

        //map不显示重量分类
        JSONArray jsonMap1 = JSONArray.fromObject(map, jsonConfig);
        System.out.println("输出mapt筛选的json数据");
        System.out.println(jsonMap1);


        //jsonObject转换为JavaBean
        String str = "{'name':'鸡蛋','price':8,'weight':1.3,'category':'禽蛋'}";
        Product product4 = (Product) JSONObject.toBean(JSONObject.fromObject(str), Product.class);
        System.out.println(product4);

    }
}
