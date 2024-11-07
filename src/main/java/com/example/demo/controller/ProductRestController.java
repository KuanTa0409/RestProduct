package com.example.demo.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Product;

@Controller
@RequestMapping("/rest")
public class ProductRestController {

	// 商品集合
	private List<Product> products = new CopyOnWriteArrayList<>(); // 支援多執行緒
	{
		products.add(new Product("Apple", 100, 50.0));
		products.add(new Product("Mango", 120, 60.0));
		products.add(new Product("Banana", 150, 40.0));
	}

	// 首頁(查詢所有商品)
	@GetMapping("/")
	public String index(Model model) { // http://localhost:8081/product/rest/
		model.addAttribute("products", products);
		return "product"; // 將 物件products 傳至 前端product.html的屬性"products"
	}

	// 查詢單一商品
	// "/{index}/?action=delete" 就導到 product_delete.html
	@GetMapping("/{index}")
	public String get(Model model, @PathVariable("index") int index) {
		Product product = products.get(index);
		model.addAttribute("index", index);
		model.addAttribute("product", product);
		return "product_update";
	}

	// 新增商品
	@PostMapping("/") // 重導參數(只會出現一次 重新整理後，東西會消失)
	public String add(Product product, RedirectAttributes attr) { // 接 在表單新增的product
		// 驗證
		if (product.getName() == null || product.getName().trim().length() == 0 || product.getQuantity() == null || product.getPrice() == null) {
			attr.addFlashAttribute("message", "新增資料錯誤");
			return "redirect:error"; // 會重導至 /product/rest/error
		}
		// 進行新增程序...
		products.add(product);
		// 將 product 物件資料傳遞給 /addOK，再傳給 success.html 顯示, 可以防止二次 submit
		attr.addFlashAttribute("product", product); // 按 重新整理，也不會二次輸入
		attr.addFlashAttribute("message","新增成功");
		return "redirect:addOK"; // 會跳至 url="/product/rest/addOK"
	}

	// 修改商品
	@PutMapping("/{index}")
	public String update(@PathVariable("index") int index, Product product, RedirectAttributes attr) {
		// 驗證
		if (product.getName() == null || product.getName().trim().length() == 0 || product.getQuantity() == null || product.getPrice() == null) {
			attr.addFlashAttribute("message", "修改資料錯誤");
			return "redirect:error"; // 會重導至 /product/rest/error
		}
		// 進行修改程序...
		products.set(index, product);
		// 將 product 物件資料傳遞給 /updateOK，再傳給 success.html 顯示, 可以防止二次 submit
		attr.addFlashAttribute("product", product);
		attr.addFlashAttribute("message","修改成功");
		return "redirect:updateOK"; // 會跳至 url="/product/rest/updateOK"
	}

	// 刪除商品
	@DeleteMapping("/{index}")
	public String delete(@PathVariable("index") int index,Product product, RedirectAttributes attr) {
		// 驗證
		if (product.getName() == null || product.getName().trim().length() == 0 || product.getQuantity() == null || product.getPrice() == null) {
			attr.addFlashAttribute("message", "刪除資料錯誤");
			return "redirect:error"; // 會重導至 /product/rest/error
		}
		// 進行刪除程序...
		product = products.remove(index); //會回傳 欲刪除物件
		attr.addFlashAttribute(product);
		attr.addFlashAttribute("message", "刪除成功");
		return "redirect:deleteOK"; // 會跳至 url="/product/rest/deleteOK"
	}

	// 新增或修改商品-成功
	@GetMapping(value = { "/addOK", "/updateOK", "/deleteOK" })
	public String success() {
		return "success";
	}

	// 新增商品-失敗
	@GetMapping(value = "/error")
	public String error() {
		return "error";
	}
}