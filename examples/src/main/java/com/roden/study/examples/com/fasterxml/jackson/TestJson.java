package com.roden.study.examples.com.fasterxml.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestJson {
    private static final JsonMapper mapper = new JsonMapper();
    private static Logger logger = LoggerFactory.getLogger(TestJson.class);

    /**
     * fastxml序列化json
     */
    @Test
    public void test76() {
        UserModel user = new UserModel();
        user.setAge(19);
        user.setBigDecimal(new BigDecimal(12));
        user.setCreateDate(new Date());
        user.setEnabled(true);
        user.setPasswd("122");
        user.setUsername("admin");
        user.setHelloDouble(3.1);

        String json = mapper.toJson(user);
        logger.info(json);
    }

    @Test
    public void testu68() {
        String helloJson = "{\"username\":\"admin\",\"passwd\":\"122\",\"createDate\":\"2015-03-07 13:25:47\",\"birthday\":\"2015-03-07 13:25:47\"}";
        UserModel user = mapper.fromJson(helloJson, UserModel.class);
        logger.info(user.toString());
        logger.info(user.getCreateDate().toString());
    }

    /**
     * Map 转换为 json
     */
    @Test
    public void MyTest01() {
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("name", "zhang");
        hashMap.put("sex", "1");
        hashMap.put("login", "Jack");
        hashMap.put("password", "123abc");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String userMapJson = objectMapper.writeValueAsString(hashMap);

            JsonNode node = objectMapper.readTree(userMapJson);

            // 输出结果转意，输出正确的信息
            System.out.println(node.get("password").asText());
            // 输出不转意,输出结果会包含""，这是不正确的，除非作为json传递，如果是输出结果值，必须如上一行的操作
            System.out.println(node.get("name"));
        } catch (IOException e) {
        }
    }

    /**
     * 解析 json 格式字符串
     */
    @Test
    public void MyTest03() {
        try {
            String str = "{\"data\":{\"birth_day\":7,\"birth_month\":6},\"errcode\":0,\"msg\":\"ok\",\"ret\":0}";

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(str);

            JsonNode data = root.path("data");

            JsonNode birth_day = data.path("birth_day");
            System.out.println(birth_day.asInt());

            JsonNode birth_month = data.path("birth_month");
            System.out.println(birth_month.asInt());

            JsonNode msg = root.path("msg");
            System.out.println(msg.textValue());
        } catch (IOException e) {
        }
    }

    /**
     * json 直接提取 值
     */
    @Test
    public void MyTest05() {
        try {
            // 演示字符串
            String str = "{\"data\":{\"hasnext\":0,\"info\":[{\"id\":\"288206077664983\",\"timestamp\":1371052476},{\"id\":\"186983078111768\",\"timestamp\":1370944068},{\"id\":\"297031120529307\",\"timestamp\":1370751789},{\"id\":\"273831022294863\",\"timestamp\":1369994812}],\"timestamp\":1374562897,\"totalnum\":422},\"errcode\":0,\"msg\":\"ok\",\"ret\":0,\"seqid\":5903702688915195270}";

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(str);

            // 提取 data
            JsonNode data = root.path("data");
            // 提取 info
            JsonNode info = data.path("info");

            System.out.println(info.size());

            // 得到 info 的第 0 个
            JsonNode item = info.get(0);
            System.out.println(item.get("id"));
            System.out.println(item.get("timestamp"));

            // 得到 info 的第 2 个
            item = info.get(2);
            System.out.println(item.get("id"));
            System.out.println(item.get("timestamp"));

            // 遍历 info 内的 array
            if (info.isArray()) {
                for (JsonNode objNode : info) {
                    System.out.println(objNode);
                }
            }

        } catch (Exception e) {

        }
    }

    /**
     * 创建一个 json，并向该 json 添加内容
     */
    @Test
    public void MyTest07() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root1 = mapper.createObjectNode();

            root1.put("nodekey1", 1);
            root1.put("nodekey2", 2);

            System.out.println(root1.toString());

            // Create the root node
            ObjectNode root = mapper.createObjectNode();
            // Create a child node
            ObjectNode node1 = mapper.createObjectNode();
            node1.put("nodekey1", 1);
            node1.put("nodekey2", 2);
            // Bind the child nodes
            root.put("child", node1);
            // Array of nodes
            ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.add(node1);
            arrayNode.add(1);
            // Bind array node
            root.put("arraynode", arrayNode);

            System.out.println(mapper.writeValueAsString(root));
            // 得到的输出信息
            // {"child":{"nodekey1":1,"nodekey2":2},"arraynode":[{"nodekey1":1,"nodekey2":2},1]}
        } catch (Exception e) {

        }
    }
}