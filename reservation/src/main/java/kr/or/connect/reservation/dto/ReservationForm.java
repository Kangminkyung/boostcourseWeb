package kr.or.connect.reservation.dto;

import java.util.List;

public class ReservationForm {
	private int displayInfoId; // 전시상품 id
	private List<ReservationPrice> reservationPrices; // 예약 가격 정보
	private int productId; // 상품 id
	private String reservationEmail; // 예약자 이메일
	private String reservationName; // 예약자명
	private String reservationTel; // 예약자 전화번호
	private String reservationDate; // 예약일
	public int getDisplayInfoId() {
		return displayInfoId;
	}
	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}
	public List<ReservationPrice> getReservationPrices() {
		return reservationPrices;
	}
	public void setReservationPrices(List<ReservationPrice> reservationPrices) {
		this.reservationPrices = reservationPrices;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getReservationEmail() {
		return reservationEmail;
	}
	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
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
	public String getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}
	@Override
	public String toString() {
		return "ReservationForm [displayInfoId=" + displayInfoId + ", reservationPrices=" + reservationPrices
				+ ", productId=" + productId + ", reservationEmail=" + reservationEmail + ", reservationName="
				+ reservationName + ", reservationTel=" + reservationTel + ", reservationDate=" + reservationDate + "]";
	}
	
	
	
	
	
}
