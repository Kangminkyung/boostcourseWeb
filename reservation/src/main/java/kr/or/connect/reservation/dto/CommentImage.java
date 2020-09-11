package kr.or.connect.reservation.dto;

import java.time.LocalDateTime;

// reservation_user_comment_image와 file_info, reservation_user_comment
// 위 3개 테이블을 조인하여 만든 commentImage dto

public class CommentImage {
	private int imageId; // 이미지 id
	private int reservationInfoId; // 예약 id, 
	private int reservationUserCommentId; // 예약자 상품명 id, =reservation_user_comment.id
	
	private int fileId; // 파일 id
	private String fileName; // 파일 이름
	private String saveFileName; // save 파일명
	private String contentType; // 파일 확장자
	private boolean deleteFlag; //삭제 여부
	private LocalDateTime createDate; // 생성일 
	private LocalDateTime modifyDate; // 수정일 


	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getReservationInfoId() {
		return reservationInfoId;
	}

	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}

	public int getReservationUserCommentId() {
		return reservationUserCommentId;
	}

	public void setReservationUserCommentId(int reservationUserCommentId) {
		this.reservationUserCommentId = reservationUserCommentId;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
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
		return "CommentImage [contentType=" + contentType + ", createDate=" + createDate + ", deleteFlag=" + deleteFlag
			+ ", fileId=" + fileId + ", fileName=" + fileName + ", imageId=" + imageId + ", modifyDate=" + modifyDate
			+ ", reservationInfoId=" + reservationInfoId + ", reservationUserCommentId=" + reservationUserCommentId
			+ ", saveFileName=" + saveFileName + "]";
	}
}
