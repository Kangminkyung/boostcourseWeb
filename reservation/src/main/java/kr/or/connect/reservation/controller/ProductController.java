package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.ProductDetail;
import kr.or.connect.reservation.service.ProductService;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	
	@GetMapping
	public Map<String, Object> getProductList(
			@RequestParam(required = false, defaultValue = "0") int start,
			@RequestParam(required = false, defaultValue = "0") int categoryId){
		
		Map<String, Object> productinfo = new HashMap<String, Object>();
		
		// 전체 카테고리
		// categoryId = 0이면 카테고리가 전체 카테고리를 불러오는 것으로 한다)
		if(categoryId == 0) {
			productinfo.put("totalCount", this.productService.getCountProduct());
			productinfo.put("productList", this.productService.getProductList(start));
			
		}else { // 선택 카테고리
			// 카테고리 별 상품의 총 갯수
			productinfo.put("totalCount", this.productService.getCountProductByCategory(categoryId));
			productinfo.put("productList",this.productService.getProductListByCategory(start, categoryId));
		}
		
		return productinfo;
	}
	
	@GetMapping(path = "/{displayInfoId}")
	public ProductDetail productInfo(@PathVariable("displayInfoId") int displayInfoId) {
				
		// pjt3때 return은 하나의 dto로 하고 map<string, object> 사용은 되도록 하지말라는 리뷰받음
		// -> ProductDetail라는 상품의 모든 정보를 담고있는 dto 생성
		ProductDetail productDetail = new ProductDetail();
		
		// 1. displayInfo id를 가지고 displayInfo 정보를 가져와서 setDisplayInfo에 저장
		productDetail.setDisplayInfo(productService.getDisplayInfo(displayInfoId));
		
		// 2. setDisplayInfo에서 getProductId로 상품아이디를 가져온다
		int productId = productDetail.getDisplayInfo().getProductId();
		
		// 3. 전시 관련 이미지를 가져온다
		productDetail.setDisplayInfoImages(productService.getDisplayInfoImages(displayInfoId));
				
		// 4. 상품 관련 이미지를 가져온다
		productDetail.setProductImages(productService.getProductImages(productId));

		// 5. 댓글을 가져온다(전시된 상품에 작성된 리뷰를 가져오는것 <- displayInfoid 필요)
		productDetail.setComments(productService.getComments(displayInfoId));
		
		//6. 평점을 가져온다
		productDetail.setAverageScore(productService.getAverageScore(displayInfoId));
		
		// 7. 예매를 위한 가격을 가져온다(productPrice는 product테이블 참조)
		productDetail.setProductPrices(productService.getProductPrices(productId));
		
		
		return productDetail;
	}
	
	
}