package kr.or.connect.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.CategoryDao;
import kr.or.connect.reservation.dto.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryDao categoryDao;

	public List<Category> getCategories() {
		return this.categoryDao.selectAll();
	}
	
}
