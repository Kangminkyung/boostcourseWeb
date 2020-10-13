package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.ProductDetail;
import kr.or.connect.reservation.dto.ProductInfo;
import kr.or.connect.reservation.service.ProductService;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	
	@GetMapping
	public ProductInfo getProductList(
			@RequestParam(required = false, defaultValue = "0") int start,
			@RequestParam(required = false,  defaultValue = "0") int categoryId){
		
		ProductInfo productInfo = new ProductInfo();		
		
		// 전체 카테고리
		// categoryId = 0이면 카테고리가 전체 카테고리를 불러오는 것으로 한다)
		if(categoryId == 0) {
			productInfo.setTotalCount(productService.getCountProduct());
			productInfo.setProductList(productService.getProductList(start));
			
		}else { // 선택 카테고리
			// 카테고리 별 상품의 총 갯수
			productInfo.setTotalCount(productService.getCountProductByCategory(categoryId));
			productInfo.setProductList(productService.getProductListByCategory(start, categoryId));
		}
		
		return productInfo;
	}
	
	@GetMapping(path = "/{displayInfoId}")
	public ProductDetail productInfo(@PathVariable("displayInfoId") int displayInfoId) {
		DisplayInfo displayInfo = productService.getDisplayInfo(displayInfoId);
		
		ProductDetail productDetail = new ProductDetail();
		productDetail.setDisplayInfo(displayInfo);	
		productDetail.setDisplayInfoImages(productService.getDisplayInfoImages(displayInfoId));
		productDetail.setProductImages(productService.getProductImages(displayInfo.getProductId()));
		productDetail.setComments(productService.getComments(displayInfoId));
		productDetail.setAverageScore(productService.getAverageScore(displayInfoId));
		productDetail.setProductPrices(productService.getProductPrices(displayInfo.getProductId()));
		
		return productDetail;
	}
	
	
}