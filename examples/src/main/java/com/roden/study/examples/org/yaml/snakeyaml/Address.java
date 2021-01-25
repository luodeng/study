package com.roden.study.examples.org.yaml.snakeyaml;

import lombok.Data;

@Data
public class Address {
    private String line;
    private String city;
    private String state;
    private Integer zip;
}
