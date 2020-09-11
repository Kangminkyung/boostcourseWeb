package kr.or.connect.reservation.dto;

import java.time.LocalDateTime;
import java.util.List;

// user가 예약을 하고 댓글을 남길 경우과 관련된 Comment dto
public class Comment {
	private String description; // 상품명 (=타이틀)
	private int commentId;// 한줄평 id
	private int productId; // 상품 id
	private int displayInfoId; // 전시상품 id
	private int reservationInfoId; // 예약 id = reservation_info.id
	private double score; //평점
	private String comment; // 한줄평
	private LocalDateTime createDate; // 생성일 
	private String modifyDate; // 수정일 
	
	// CommentImage 정보 담기
	private int imageCount; // 댓글 이미지 갯수
	private List<CommentImage> commentImages; // 상품평 이미지들
	
	// reservation_info 테이블과 조인
	private String reservationName; // 예약자명
	private String reservationTel; // 예약자 전화번호
	private String reservationEmail; // 예약자 이메일
	private String reservationDate; // 예약일 
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCommentId() {
		return commentId;
	}
	
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public int getReservationInfoId() {
		return reservationInfoId;
	}

	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getImageCount() {
		return imageCount;
	}

	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}


	public List<CommentImage> getCommentImages() {
		return commentImages;
	}

	public void setCommentImages(List<CommentImage> commentImages) {
		this.commentImages = commentImages;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	public String getReservationTel() {
		return reservationTel;
	}

	public void setReservationTel(String reservationTel) {
		this.reservationTel = reservationTel;
	}

	public String getReservationEmail() {
		return reservationEmail;
	}

	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}

	public String getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}

	
	@Override
	public String toString() {
		return "Comment [description=" +description+ " comment=" + comment + ", commentId=" + commentId + ", displayInfoId= " + displayInfoId +", commentImages=" + commentImages
			+ ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", imageCount=" +imageCount+ ", productId=" + productId
			+ ", reservationDate=" + reservationDate + ", reservationEmail=" + reservationEmail + ", reservationInfoId="
			+ reservationInfoId + ", reservationName=" + reservationName + ", reservationTelephone="
			+ reservationTel + ", score=" + score + "]";
	}
}
