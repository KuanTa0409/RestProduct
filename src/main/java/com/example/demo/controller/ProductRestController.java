package com.example.demo.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Product;

@Controller
@RequestMapping("/rest")
public class ProductRestController {
	
	// 商品集合
	private List<Product> products = new CopyOnWriteArrayList<>(); //支援多執行緒
	{
		products.add(new Product("Apple", 100, 50.0));
		products.add(new Product("Mango", 120, 60.0));
		products.add(new Product("Banana", 150, 40.0));
	}
	
	// 首頁(查詢所有商品)
	@GetMapping("/")
	public String index(Model model) { // http://localhost:8081/product/rest/
		model.addAttribute("products", products);
		return "product";   // 將 物件products 傳至 前端product.html的屬性"products"
	}
	
	// 新增商品
	@PostMapping("/")
	public String add(Product product) { // 接 表單的Product
		// 進行新增程序...
		products.add(product);
		return "redirect:addOK"; //會跳至 url="/product/rest/addOK"
	}
	// 新增商品-成功
	@GetMapping(value = "/addOK")
	public String success() {
		return "success";
	}
	
	
	
	
	
	
	
	
	
	
}