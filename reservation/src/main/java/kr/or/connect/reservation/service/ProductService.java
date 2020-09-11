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
	private final int SIZE_PER_PAGE = 3; // comments 갯수
	private final int HIDE_EMAIL_LENGTH = 4;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private DisplayInfoDao displayInfoDao;
	
	@Autowired
	private CommentDao commentDao;
	
	@Transactional(readOnly=true)
	public int getCountProduct() {
		return this.productDao.getCountProduct();
	}

	@Transactional(readOnly=true)
	public int getCountProductByCategory(int categoryId) {
		return this.productDao.getCountProductByCategory(categoryId);
	}
	
	@Transactional(readOnly=true)
	public List<Product> getProductList(int start) {
		return this.productDao.getProductList(start, LIMIT);
	}
	
	@Transactional(readOnly=true)
	public List<Product> getProductListByCategory(int start, int categoryId) {
		return this.productDao.getProductListByCategory(start, LIMIT, categoryId);
	}
	
	/* productDetail 정보 불러오기 */
	
	@Transactional(readOnly=true)
	public DisplayInfo getDisplayInfo(int displayInfoId) {
		return this.displayInfoDao.getDisplayInfo(displayInfoId);
	}
	
	@Transactional(readOnly=true)
	public List<DisplayInfoImage> getDisplayInfoImages(int displayInfoId) {
		
		List<DisplayInfoImage> displayInfoImageList = displayInfoDao.getDisplayInfoImages(displayInfoId);

		// displayInfo 이미지 경로를 변경함 (displayInfoSaveFileName)
		for(DisplayInfoImage image : displayInfoImageList) {
			String newName = "assets/";
			newName += image.getDisplayInfoSaveFileName();
			image.setDisplayInfoSaveFileName(newName);
		}
		
		return displayInfoImageList;
	}
	
	@Transactional(readOnly=true)
	public List<ProductImage> getProductImages(int productId) {
		
		List<ProductImage> productImages = productDao.getProductImages(productId);

		// product 이미지 경로를 변경함(productSaveFileName)
		for(ProductImage image : productImages) {
			String newName = "assets/";
			newName += image.getProductSaveFileName();
			image.setProductSaveFileName(newName);
		}
		
		return productImages;
	}
	
	@Transactional(readOnly=true)
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
	
	@Transactional(readOnly=true)
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
