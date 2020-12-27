package com.roden.study.java.designpatterns.other.filter;

import java.util.List;
 
public interface Criteria {
   public List<Person> meetCriteria(List<Person> persons);
}