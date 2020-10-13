package kr.or.connect.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.CommentDao;
import kr.or.connect.reservation.dao.DisplayInfoDao;
import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentImage;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoImage;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductImage;
import kr.or.connect.reservation.dto.ProductPrice;

@Service
public class ProductService {
	private static final int LIMIT = 4;
	private final int HIDE_EMAIL_LENGTH = 4;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private DisplayInfoDao displayInfoDao;
	
	@Autowired
	private CommentDao commentDao;
	
	public int getCountProduct() {
		return this.productDao.getCountProduct();
	}

	public int getCountProductByCategory(int categoryId) {
		return this.productDao.getCountProductByCategory(categoryId);
	}
	
	public List<Product> getProductList(int start) {
		return this.productDao.getProductList(start, LIMIT);
	}
	
	public List<Product> getProductListByCategory(int start, int categoryId) {
		return this.productDao.getProductListByCategory(start, LIMIT, categoryId);
	}
	
	/* productDetail 정보 불러오기 */
	
	public DisplayInfo getDisplayInfo(int displayInfoId) {
		return this.displayInfoDao.getDisplayInfo(displayInfoId);
	}
	
	public List<DisplayInfoImage> getDisplayInfoImages(int displayInfoId) {
		
		List<DisplayInfoImage> displayInfoImageList = displayInfoDao.getDisplayInfoImages(displayInfoId);

		// displayInfo 이미지 경로를 변경함 (displayInfoSaveFileName)
		for(DisplayInfoImage image : displayInfoImageList) {
			String newName = "/tmp/img_map/";
			newName += image.getFileName();
			image.setDisplayInfoSaveFileName(newName);
		}
		
		return displayInfoImageList;
	}
	
	public List<ProductImage> getProductImages(int productId) {
		
		List<ProductImage> productImages = productDao.getProductImages(productId);

		// product 이미지 경로를 변경함(productSaveFileName)
		for(ProductImage image : productImages) {
			String newName = "/tmp/img/";
			newName += image.getFileName();
			image.setProductSaveFileName(newName);
		}
		
		return productImages;
	}
	
	public List<Comment> getComments(int displayInfoId) {
		
		List<Comment> comments = commentDao.getComments(displayInfoId);
		
		for(Comment comment : comments) {
			String email = comment.getReservationEmail();
			int lengthId = email.indexOf('@');
			
			email = email.substring(0, lengthId > HIDE_EMAIL_LENGTH ? HIDE_EMAIL_LENGTH : lengthId);
			email += "****";
			comment.setReservationEmail(email);
			
			String date = comment.getReservationDate();
			date = date.substring(0,10).replace('-', '.') + ".";
			comment.setReservationDate(date);
		}
		return comments;
	}
	
	public double getAverageScore(int displayInfoId) {
		try {
			return commentDao.getAverageScore(displayInfoId);
		}
		catch(NullPointerException e) {
			return 0;
		}
	}

	public List<ProductPrice> getProductPrices(int productId) {
		return productDao.getPriceList(productId);
	}

	// 상품 아이디 구하기
	public int getProductIdByDisplayInfoId(int displayInfoId) {
		return displayInfoDao.getProductIdByDisplayInfoId(displayInfoId);
	}



	

}
