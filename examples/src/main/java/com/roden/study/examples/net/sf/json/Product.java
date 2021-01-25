package com.roden.study.examples.net.sf.json;

/**
 * 商品实体类
 * 
 * @author Administrator
 *
 */
public class Product {
	private String name;
	private Double price;
	private String category;
	private Double weight;

	public Product() {
		super();
	}

	public Product(String name, Double price, String category, Double weight) {
		super();
		this.name = name;
		this.price = price;
		this.category = category;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "net.sf.json.com.fasterxml.jackson.com.alibaba.fastjson.Product [name=" + name + ", price=" + price + ", category=" + category + ", weight=" + weight + "]";
	}
}
