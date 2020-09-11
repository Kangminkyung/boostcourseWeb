package kr.or.connect.reservation.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.reservation.config.ApplicationConfig;
import kr.or.connect.reservation.dao.CategoryDao;
import kr.or.connect.reservation.dto.Category;

public class SelectAllTest {
	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class); 
		
		CategoryDao categoryDao =ac.getBean(CategoryDao.class);

		List<Category> list = categoryDao.selectAll();
		
		for(Category category: list) {
			System.out.println(category);
		}

	}

}
