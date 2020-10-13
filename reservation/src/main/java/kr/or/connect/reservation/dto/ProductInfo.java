package kr.or.connect.reservation.dto;

import java.util.List;

public class ProductInfo {
    int totalCount;
    List<Product> productList;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	@Override
	public String toString() {
		return "ProductInfo [totalCount=" + totalCount + ", productList=" + productList + "]";
	}
    
    
}
