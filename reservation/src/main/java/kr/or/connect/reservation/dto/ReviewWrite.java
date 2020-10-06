package kr.or.connect.reservation.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReviewWrite {
	private int reservationId;
	private int productId;
	private double score;
	private String comment;
	private String fileName;
	private String saveFileName;
	private String contentType;
	private int deleteFlag;
	private final String createDate = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());
	private final String modifyDate = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());
	public int getReservationId() {
		return reservationId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getCreateDate() {
		return createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	@Override
	public String toString() {
		return "ReviewWrite [reservationId=" + reservationId + ", productId=" + productId + ", score=" + score
				+ ", comment=" + comment + ", fileName=" + fileName + ", saveFileName=" + saveFileName
				+ ", contentType=" + contentType + ", deleteFlag=" + deleteFlag + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + "]";
	}

	
	
}
