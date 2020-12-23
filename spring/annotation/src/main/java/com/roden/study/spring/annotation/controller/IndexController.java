package com.roden.study.spring.annotation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	@RequestMapping("index")
	public String loadIndexPage() {
		log.info("index");		
		return "index";
	}
}