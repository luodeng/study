package com.roden.study.elasticsearch.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void save() throws Exception {
        userDao.save(User.builder()
                .id(1L)
                .name("TEST")
                .departmentId(1L)
                .createTime(new Date())
                .build());
    }

    @Test
    public void selectById() throws Exception {
        Optional<User> bookEntityOptional = userDao.findById(1L);
        User user = bookEntityOptional.get();
        assertTrue(1L == user.getId());
    }

    @Test
    public void selectPage() throws Exception {
        Page<User> page = userDao.getByName("TEST", PageRequest.of(0, 10));
        assertTrue(page.getContent().size() > 0);
    }
}