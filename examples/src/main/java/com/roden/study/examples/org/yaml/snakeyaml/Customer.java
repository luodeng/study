package com.roden.study.examples.org.yaml.snakeyaml;

import lombok.Data;

import java.util.List;

@Data
public class Customer {
    private String firstName;
    private String lastName;
    private int age;
    private List<Contact> contactDetails;
    private Address homeAddress;
}
