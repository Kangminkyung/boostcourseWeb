package kr.or.connect.reservation.dao;

public class ReservationDaoSqls {

	public static final String INSERT_RESERVATION = 
		    " INSERT INTO RESERVATION_INFO ("
		    		  + "			PRODUCT_ID," 
		    		  + "			DISPLAY_INFO_ID," 
		    		  + "			RESERVATION_NAME," 
		    		  + "			RESERVATION_TEL," 
		    		  + "			RESERVATION_EMAIL," 
		    		  + "			RESERVATION_DATE," 
		    		  + "			CANCEL_FLAG," 
		    		  + "			CREATE_DATE," 
		    		  + "			MODIFY_DATE)" 
		    		  + " VALUES (:productId," 
		    		  + "			:displayInfoId," 
		    		  + "			:reservationName," 
		    		  + "			:reservationTel," 
		    		  + "			:reservationEmail," 
		    		  + "			:reservationDate," 
		    		  + "			DEFAULT," 
		    		  + "			:createDate," 
		    		  + "			:modifyDate)"; 
	
	public static final String INSERT_PRICES = 
			"insert into reservation_info_price(reservation_info_id, product_price_id, count) values(:reservationInfoId, :productPriceId, :count)";
}
