package kr.or.connect.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.service.CategoryService;

@RestController
@RequestMapping(path ="/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public List<Category> getCategories() {
		return categoryService.getCategories();
	}

}
