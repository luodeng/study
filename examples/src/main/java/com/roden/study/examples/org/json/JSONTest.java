package com.roden.study.examples.org.json;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JSONTest {
    @Test
    public void build(){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("female", true);
        jsonObj.put("hobbies", Arrays.asList(new String[] { "yoga", "swimming" }));
        jsonObj.put("discount", 9.5);
        jsonObj.put("age", "26");
        jsonObj.put("features", new HashMap<String, Integer>() {
            private static final long serialVersionUID = 1L;
            {
                put("height", 175);
                put("weight", 70);
            }
        });
        System.out.println(jsonObj);
    }
    @Test
    public void mapBuild(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("female", true);
        map.put("hobbies", Arrays.asList(new String[] { "yoga", "swimming" }));
        map.put("discount", 9.5);
        map.put("age", "26");
        map.put("features", new HashMap<String, Integer>() {
            private static final long serialVersionUID = 1L;
            {
                put("height", 175);
                put("weight", 70);
            }
        });
        JSONObject jsonObj = new JSONObject(map);
        System.out.println(jsonObj);
    }

    @Test
    public void beanBuild(){
        UserInfo userInfo = new UserInfo();
        userInfo.setFemale(true);
        userInfo.setHobbies(new String[] { "yoga", "swimming" });
        userInfo.setDiscount(9.5);
        userInfo.setAge(26);
        userInfo.setFeatures(new HashMap<String, Integer>() {
            private static final long serialVersionUID = 1L;
            {
                put("height", 175);
                put("weight", 70);
            }
        });
        JSONObject jsonObj = new JSONObject(userInfo);
        System.out.println(jsonObj);

        /****************************************************/

        // 获取基本类型数据
        System.out.println("Female: " + jsonObj.getBoolean("female"));
        System.out.println("Discount: " + jsonObj.getDouble("discount"));
        System.out.println("Age: " + jsonObj.getLong("age"));

        // 获取JSONObject类型数据
        JSONObject features = jsonObj.getJSONObject("features");
        String[] names = JSONObject.getNames(features);
        System.out.println("Features: ");
        for (int i = 0; i < names.length; i++) {
            System.out.println("\t"+features.get(names[i]));
        }

        // 获取数组类型数据
        JSONArray hobbies = jsonObj.getJSONArray("hobbies");
        System.out.println("Hobbies: ");
        for (int i = 0; i < hobbies.length(); i++) {
            System.out.println("\t"+hobbies.get(i));
        }
    }

}
