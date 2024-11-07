package com.example.demo.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String index(Model model) {  // http://localhost:8081/product/rest/
		model.addAttribute("products", products);
		return "product";   // 將 物件products 傳至 前端product.html的屬性"products"
	}
	
	// 新增商品
	@PostMapping("/")                  // 重導參數(只會出現一次 重新整理後，東西會消失)
	public String add(Product product, RedirectAttributes attr) { // 接 在表單新增的product
		// 驗證
		if(product.getName() == null || product.getQuantity() == null || product.getPrice() == null) {
			attr.addFlashAttribute("message", "新增資料錯誤");
			return "redirect:error";
		}
		// 進行新增程序...
		products.add(product);
		// 將 product 物件資料傳遞給 /addOK，再傳給 success.html 顯示, 可以防止二次 submit
		attr.addFlashAttribute("product", product); //按 重新整理，也不會二次輸入
		return "redirect:addOK"; //會跳至 url="/product/rest/addOK"
	}
	// 新增商品-成功
	@GetMapping(value = "/addOK")
	public String success() {
		return "success";
	}
	
	// 新增商品-失敗
	@GetMapping(value = "/error")
	public String error() {
		return "error";
		}
	
	
	
	
	
	
	
	
	
}