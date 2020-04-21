package com.roden.study.elasticsearch.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {

    List<User> getByName(String name);

    Page<User> getByName(String name, Pageable pageable);
}