package com.roden.study.examples.com.fasterxml.jackson;

import java.util.List;
import java.util.Map;

/**
 * jacksonJson解析类
 */
public class JacksonTester {


    public static void main(String[] args) {
        //测试用字符串
        String json = "{\n" +
                "\t\"name\": {\n" +
                "\t\t\"first\": \"Joe\",\n" +
                "\t\t\"last\": \"Sixpack\"\n" +
                "\t},\n" +
                "\t\"gender\": \"MALE\",\n" +
                "\t\"verified\": false,\n" +
                "\t\"userImage\": \"Rm9vYmFyIQ==\",\n" +
                "\t\"extra\": \"0000000\",\n" +
                "\t\"note\": \"content\",\n" +
                "\t\"birthday\": \"2016-11-12\",\n" +
                "\t\"loginTime\": \"2016-11-12T22:19:35\"\n" +
                "}";


        String jsonArray = "[{\n" +
                "\t\"name\": {\n" +
                "\t\t\"first\": \"Joe\",\n" +
                "\t\t\"last\": \"Sixpack\"\n" +
                "\t},\n" +
                "\t\"gender\": \"MALE\",\n" +
                "\t\"verified\": false,\n" +
                "\t\"userImage\": \"Rm9vYmFyIQ==\",\n" +
                "\t\"extra\": \"0000000\",\n" +
                "\t\"note\": \"content\",\n" +
                "\t\"birthday\": \"2016-11-12\",\n" +
                "\t\"loginTime\": \"2016-11-12T22:19:35\"\n" +
                "},\n" +
                "{\n" +
                "\t\"name\": {\n" +
                "\t\t\"first\": \"Joe\",\n" +
                "\t\t\"last\": \"Sixpack\"\n" +
                "\t},\n" +
                "\t\"gender\": \"MALE\",\n" +
                "\t\"verified\": false,\n" +
                "\t\"userImage\": \"Rm9vYmFyIQ==\",\n" +
                "\t\"extra\": \"0000000\",\n" +
                "\t\"note\": \"content\",\n" +
                "\t\"birthday\": \"2016-11-12\",\n" +
                "\t\"loginTime\": \"2016-11-12T22:19:35\"\n" +
                "}]";


        // json测试
//        Map<String, Object> map = JacksonUtil.json2Map(json);
//        String jsonStr = JacksonUtil.pojo2Json(map);
//        System.out.println(jsonStr);
//        User jsonUser = JacksonUtil.json2Pojo(json, User.class);
//        String userJsonStr = JacksonUtil.pojo2Json(jsonUser);
//        System.out.println(userJsonStr);

        // jsonArray测试
        List<Map<String, Object>> listMap = JacksonUtil.jsonArray2ListMap(jsonArray);
        String jsonArrayStr = JacksonUtil.listMap2JsonArray(listMap);
        System.out.println(jsonArrayStr);
        List<User> jsonUser = JacksonUtil.jsonArray2PojoList(jsonArray, User.class);
        String userJsonStr = JacksonUtil.pojo2Json(jsonUser);
        System.out.println(userJsonStr);


        // xml测试
//        String xmlStr = JacksonUtil.pojo2Xml(map);
//        System.out.println(xmlStr);
//        Map<String, Object> xmlMap = JacksonUtil.xml2Map(xmlStr);
//        User xmlUser = JacksonUtil.xml2Pojo(xmlStr, User.class);
//        String xmlUserStr = JacksonUtil.pojo2Xml(xmlUser);
//        System.out.println(xmlUserStr);


        String xmlStr = JacksonUtil.pojo2Xml(listMap);
        System.out.println(xmlStr);
        List<Map<String, Object>> xmlListMap = JacksonUtil.xml2ListMap(xmlStr);
        List<User> xmlUserList = JacksonUtil.xml2PojoList(xmlStr, User.class);
        String xmlUserStr = JacksonUtil.pojo2Xml(xmlUserList);
        System.out.println(xmlUserStr);

    }


}