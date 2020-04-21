package com.roden.study.elasticsearch.demo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "lo", type = "user")
public class User implements Serializable {
    @Id
    private Long id;
    private String name;
    private Long departmentId;
    private Date createTime;
}