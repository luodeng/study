package com.roden.study.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private BigDecimal price;
}
