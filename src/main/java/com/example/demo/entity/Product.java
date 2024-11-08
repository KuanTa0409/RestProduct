package com.example.demo.entity;

public class Product {
	private String name;      // 商品名稱
	private Integer quantity; // 商品數量
	private Double price;     // 商品價格
	
	public Product() {
		
	}
	public Product(String name, Integer quantity, Double price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Product [name=" + name + ", quantity=" + quantity + ", price=" + price + "]";
	}
}