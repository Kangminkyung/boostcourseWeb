package kr.or.connect.reservation.dto;

import java.util.List;

// ProductDetail 
// product와 displayInfo 테이블을 조인하여 전시 상품의 이미지 및 댓글, 가격 등의 정보를 받아오는 dto
public class ProductDetail {
	
	private DisplayInfo displayInfo;
	private List<ProductImage> productImages;
	private List<DisplayInfoImage> displayInfoImages;
	private List<Comment> comments;
	private double averageScore;
	private List<ProductPrice> productPrices;
	
	public DisplayInfo getDisplayInfo() {
		return displayInfo;
	}
	public void setDisplayInfo(DisplayInfo displayInfo) {
		this.displayInfo = displayInfo;
	}
	public List<ProductImage> getProductImages() {
		return productImages;
	}
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}
	public List<DisplayInfoImage> getDisplayInfoImages() {
		return displayInfoImages;
	}
	public void setDisplayInfoImages(List<DisplayInfoImage> displayInfoImages) {
		this.displayInfoImages = displayInfoImages;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public double getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}
	public List<ProductPrice> getProductPrices() {
		return productPrices;
	}
	public void setProductPrices(List<ProductPrice> productPrices) {
		this.productPrices = productPrices;
	}
	
	@Override
	public String toString() {
		return "DisplayInfoResponse [displayInfo=" + displayInfo + ", productImages=" + productImages
			+ ", displayInfoImages=" + displayInfoImages + ", comments=" + comments + ", averageScore=" + averageScore
			+ ", productPrices=" + productPrices + "]";
	}
	
}
