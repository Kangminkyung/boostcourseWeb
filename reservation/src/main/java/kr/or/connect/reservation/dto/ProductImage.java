package kr.or.connect.reservation.dto;

import java.time.LocalDateTime;

public class ProductImage {
	private int productId; // 상품 id
	private int productImageId; // 상품 이미지 id
	private String type; // 이미지 타입 ma, th, et
	private int fileInfoId; // 파일 id
	private String fileName; // 파일 이름
	private String productSaveFileName; // 파일 저장 위치 이름
	private String contentType; // 파일 확장자
	private boolean deleteFlag; //삭제 여부
	private LocalDateTime createDate; // 생성일 
	private LocalDateTime modifyDate; // 수정일 
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductImageId() {
		return productImageId;
	}
	public void setProductImageId(int productImageId) {
		this.productImageId = productImageId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getFileInfoId() {
		return fileInfoId;
	}
	public void setFileInfoId(int fileInfoId) {
		this.fileInfoId = fileInfoId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getProductSaveFileName() {
		return productSaveFileName;
	}
	public void setProductSaveFileName(String productSaveFileName) {
		this.productSaveFileName = productSaveFileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public boolean isDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public LocalDateTime getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "ProductImage [productId=" + productId + ", productImageId=" + productImageId + ", type=" + type
			+ ", fileInfoId=" + fileInfoId + ", fileName=" + fileName + ", productSaveFileName=" + productSaveFileName
			+ ", contentType=" + contentType + ", deleteFlag=" + deleteFlag + ", createDate=" + createDate
			+ ", modifyDate=" + modifyDate + "]";
	}
}