package kr.or.connect.reservation.dto;

public class TicketInfo {

	private String priceTypeName = ""; // product_price
	private int price; // reservation_info_price
	private int count;  // reservation_info_price
	
	public String getPriceTypeName() {
		return priceTypeName;
	}
	public void setPriceTypeName(String priceTypeName) {
		this.priceTypeName = priceTypeName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "TicketInfo [priceTypeName=" + priceTypeName + ", price=" + price + ", count=" + count + "]";
	}
	
	
}
