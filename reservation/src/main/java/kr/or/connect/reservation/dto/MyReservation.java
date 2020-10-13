package kr.or.connect.reservation.dto;

import java.util.List;

public class MyReservation {
	private int ticketPrice;  // 티켓 총 가격
	private int ticketCount; // 총 티켓 수
	private int displayInfoId; // display_info
	private String placeName = ""; // display_info
	private String placeLot = ""; // display_info
	private String placeStreet = ""; // display_info
	private int productId; // reservation_info, product
	private String description = ""; // product
	private int reservationId; // reservation_info
	private int cancelFlag; // reservation_info
	private String reservationDate; // reservation_info
	private List<TicketInfo> ticketInfo; // product_price + reservation_info_price
	public int getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public int getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}
	public int getDisplayInfoId() {
		return displayInfoId;
	}
	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getPlaceLot() {
		return placeLot;
	}
	public void setPlaceLot(String placeLot) {
		this.placeLot = placeLot;
	}
	public String getPlaceStreet() {
		return placeStreet;
	}
	public void setPlaceStreet(String placeStreet) {
		this.placeStreet = placeStreet;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public int getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(int cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public String getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}
	public List<TicketInfo> getTicketInfo() {
		return ticketInfo;
	}
	public void setTicketInfo(List<TicketInfo> ticketInfo) {
		this.ticketInfo = ticketInfo;
	}
	
	public boolean isCanceled() {
		return cancelFlag == 1;
	}
	@Override
	public String toString() {
		return "MyReservation [ticketPrice=" + ticketPrice + ", ticketCount=" + ticketCount + ", displayInfoId="
				+ displayInfoId + ", placeName=" + placeName + ", placeLot=" + placeLot + ", placeStreet=" + placeStreet
				+ ", productId=" + productId + ", description=" + description + ", reservationId=" + reservationId
				+ ", cancelFlag=" + cancelFlag + ", reservationDate=" + reservationDate + ", ticketInfo=" + ticketInfo
				+ "]";
	}
	

	
}
