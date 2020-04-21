package com.roden.study.elasticsearch.demo;

import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientTest {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        restTemplate = restTemplateBuilder.rootUri("http://localhost:9200").build();
    }

    @Test
    public void save() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("TEST")
                .departmentId(1L)
                .createTime(new Date())
                .build();

        ResponseEntity<String> entity = restTemplate.postForEntity("/lo/user/{id}?pretty", user, String.class, 1);
        Assert.assertTrue(JsonPath.parse(entity.getBody()).read("$._shards.successful", Integer.class) > 0);
    }

    @Test
    public void selectById() throws Exception {
        ResponseEntity<String> entity = restTemplate.getForEntity("/lo/user/{id}?pretty", String.class, 1);
        Assert.assertEquals("TEST", JsonPath.read(entity.getBody(), "$._source.name"));
    }
}