package kr.or.connect.reservation.dto;

public class ReservationPrice {
	private int reservationInfoPriceId; // 예매 가격 id
	private int reservationInfoId; // 예매 id
	private int productPriceId; // 상품 가격 id
	private int count; // 예매 상품 수

	public int getReservationInfoPriceId() {
		return reservationInfoPriceId;
	}

	public void setReservationInfoPriceId(int reservationInfoPriceId) {
		this.reservationInfoPriceId = reservationInfoPriceId;
	}

	public int getReservationInfoId() {
		return reservationInfoId;
	}

	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}

	public int getProductPriceId() {
		return productPriceId;
	}

	public void setProductPriceId(int productPriceId) {
		this.productPriceId = productPriceId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ReservationPrice [reservationInfoPriceId=" + reservationInfoPriceId + ", reservationInfoId="
			+ reservationInfoId + ", productPriceId=" + productPriceId + ", count=" + count + "]";
	}
}
